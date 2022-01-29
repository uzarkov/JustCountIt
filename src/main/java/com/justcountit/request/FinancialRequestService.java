package com.justcountit.request;

import com.justcountit.group.membership.GroupMembershipService;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FinancialRequestService {
    private final FinancialRequestRepository financialRequestRepository;
    private final AppUserService appUserService;
    private final GroupMembershipService groupMembershipService;

    public void addFinancialRequests(Long debteeId, Map<Long, Double> debts, Long groupId) {
        var debteeGroupMembership = groupMembershipService.getMembership(debteeId, groupId);

        Map<AppUser, Double> debtors = debts.keySet().stream()
                .map(appUserService::getUserById)
                .collect(Collectors.toMap(debtor -> debtor, debtor -> debts.get(debtor.getId())));

        var financialRequestsToAdd = debtors.entrySet().stream()
                .map(entry -> FinancialRequest.create(entry.getValue(), debteeGroupMembership, entry.getKey()))
                .toList();

        financialRequestRepository.saveAll(financialRequestsToAdd);
    }

    public void addFinancialRequest(Long debteeId, Long debtorId, Double price, Long groupId) {
        var debteeGroupMembership = groupMembershipService.getMembership(debteeId, groupId);
        var debtor = appUserService.getUserById(debtorId);
        var financialRequestToAdd = FinancialRequest.create(price, debteeGroupMembership, debtor);
        financialRequestRepository.save(financialRequestToAdd);
    }

    public Set<FinancialRequest> getAllActiveFinancialRequestsIn(Long groupId) {
        return financialRequestRepository.getAllActiveInGroup(groupId);
    }

    public boolean hasFinancialRequests(Long userId, Long groupId){

        return !getUserFinancialRequests(userId, groupId).isEmpty();
    }

    public Set<FinancialRequest> getUserFinancialRequests(Long userId, Long groupId) {
        return financialRequestRepository.getAllByDebtorAndExpenditure(userId, groupId);
    }
}
