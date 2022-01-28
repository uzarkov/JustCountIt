package com.justcountit.expenditure;

import com.justcountit.expenditure.validation.ExpenditureValidator;
import com.justcountit.group.GroupService;
import com.justcountit.group.membership.GroupMembershipException;
import com.justcountit.group.membership.GroupMembershipService;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpenditureService {

    private final ExpenditureValidator expenditureValidator;
    private final ExpenditureRepository expenditureRepository;
    private final AppUserService appUserService;
    private final GroupService groupService;
    private final GroupMembershipService groupMembershipService;

    public Set<ExpenditureMetadataProjection> getExpendituresMetadata(Long groupId,
                                                                      Principal principal) {
        var user = appUserService.getUserByEmail(principal.getName());
        checkIfUserIsMemberOfTheGroup(user, groupId);
        return expenditureRepository.findAllByGroupId(groupId, ExpenditureMetadataProjection.class);
    }

    public ExpenditureMetadataProjection addExpenditure(Long groupId,
                                                        Principal principal,
                                                        ExpenditureInput expenditureInput) {
        var user = appUserService.getUserByEmail(principal.getName());
        checkIfUserIsMemberOfTheGroup(user, groupId);
        var expenditure = mapInputToExpenditure(expenditureInput, user, groupId);
        var addedExpenditure = expenditureRepository.save(expenditure);
        return expenditureRepository.getById(addedExpenditure.getId(), ExpenditureMetadataProjection.class);
    }

    private void checkIfUserIsMemberOfTheGroup(AppUser user, Long groupId) {
        if (!groupMembershipService.isMemberOf(user.getId(), groupId)) {
            throw GroupMembershipException.principalNotMember();
        }
    }

    private Expenditure mapInputToExpenditure(ExpenditureInput expenditureInput,
                                              AppUser user,
                                              Long groupId) {
        expenditureValidator.validateExpenditureInput(expenditureInput, groupId);
        var group = groupService.getGroupBy(groupId);

        return new Expenditure(expenditureInput.price(),
                               expenditureInput.title(),
                               user,
                               group);
    }
}
