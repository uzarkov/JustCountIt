package com.justcountit.user;

import com.justcountit.expenditure.ExpenditureRepository;
import com.justcountit.expenditure.ExpenditureService;
import com.justcountit.group.GroupService;
import com.justcountit.request.FinancialRequest;
import com.justcountit.request.FinancialRequestOptimizer;
import com.justcountit.request.FinancialRequestRepository;
import com.justcountit.request.FinancialRequestService;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/balance/")
@RequiredArgsConstructor
@Getter

public class AppUserController {
    private final GroupService groupService;
    private final ExpenditureService expenditureService;
    private final FinancialRequestService financialRequestService;
    private final AppUserService appUserService;
    private final FinancialRequestRepository financialRequestRepository;
    private final ExpenditureRepository expenditureRepository;
    private final FinancialRequestOptimizer financialRequestOptimizer;


    @GetMapping("/{groupId}")
    public List<UserBalanceMetadata> getGroupBalance(@PathVariable Long groupId) {
        List<UserBalanceMetadata> userBalanceMetadata = new ArrayList<>();
        Set<AppUserWithRole> appUserWithRoles = groupService.getGroupMember(groupId);
        Set<FinancialRequest> financialRequests = financialRequestService.getAllActiveFinancialRequestsIn(groupId);

        Map<Long, Double> balanceMap = financialRequestOptimizer.calculateNetCashFlowIn(financialRequests);
        for (var user : appUserWithRoles) {
            balanceMap.putIfAbsent(user.getAppUser().getId(), 0d);
        }

        for (Map.Entry<Long, Double> entry : balanceMap.entrySet()) {
            AppUser currUser = appUserWithRoles.stream().map(AppUserWithRole::getAppUser).filter(a -> Objects.equals(a.getId(), entry.getKey())).findFirst().orElseThrow();
            userBalanceMetadata.add(new UserBalanceMetadata(entry.getKey(), currUser.getName(), entry.getValue()));
        }

        return userBalanceMetadata;
    }

    @GetMapping("/{groupId}/currentUser")
    public UserRequestMetadata getPersonalRequests(@PathVariable Long groupId, Principal principal) {
        var user = appUserService.getUserByEmail(principal.getName());
        Set<FinancialRequest> financialRequests = financialRequestService.getAllActiveFinancialRequestsIn(groupId);
        return appUserService.getUserRequestMetadata(financialRequests,user.getId());
    }
}
