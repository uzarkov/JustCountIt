package com.justcountit.user;

import com.justcountit.request.FinancialRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUser getUserById(Long id) {
        return appUserRepository.findById(id).orElseThrow();
    }

    public AppUser getUserByEmail(String email){
        return appUserRepository.getAppUserByEmail(email).orElseThrow();
    }

    public Long getUserId(String email){
        return getUserByEmail(email).getId();
    }


    public UserRequestMetadata getUserRequestMetadata(Set<FinancialRequest> financialRequests, Long userId) {

        List<ForDebtorsMetadata> forDebtors = new ArrayList<>();
        List<ForMeMetadata> forMe = new ArrayList<>();
        for (var finReq : financialRequests) {
            if (finReq.getDebtee().getAppUser().getId().equals(userId)) {
                forDebtors.add(new ForDebtorsMetadata(finReq.getId(),finReq.getDebtor().getId(), finReq.getPrice()));
            } else if (finReq.getDebtor().getId().equals(userId)) {
                forMe.add(new ForMeMetadata(finReq.getId(), finReq.getDebtee().getAppUser().getId(), finReq.getPrice()));
            }
        }
        return new UserRequestMetadata(forDebtors,forMe);
    }

    public List<UserBalanceMetadata> processData(Set<AppUserWithRole> appUserWithRoles, Map<Long, Double> balanceMap) {
        List<UserBalanceMetadata> userBalanceMetadata = new ArrayList<>();
        for (var user : appUserWithRoles) {
            balanceMap.putIfAbsent(user.getAppUser().getId(), 0d);
        }

        for (Map.Entry<Long, Double> entry : balanceMap.entrySet()) {
            AppUser currUser = appUserWithRoles.stream().map(AppUserWithRole::getAppUser).filter
                    (a -> Objects.equals(a.getId(), entry.getKey())).findFirst().orElseThrow();
            userBalanceMetadata.add(new UserBalanceMetadata(entry.getKey(), currUser.getName(), entry.getValue()));
        }
        return userBalanceMetadata;
    }
}
