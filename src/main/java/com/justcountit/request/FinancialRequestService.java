package com.justcountit.request;

import com.justcountit.commons.Status;
import com.justcountit.expenditure.Expenditure;
import com.justcountit.expenditure.ExpenditureRepository;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FinancialRequestService {
    private final FinancialRequestRepository financialRequestRepository;
    private final AppUserRepository appUserRepository;
    private final ExpenditureRepository expenditureRepository;



    public boolean hasFinancialRequests(Long userId, Long groupId){

        return !getUserFinancialRequests(userId, groupId).isEmpty();
    }

    public Set<FinancialRequest> getUserFinancialRequests(Long userId, Long groupId) {
        System.out.println("Here");
        return financialRequestRepository.getAllByDebtorAndExpenditure(userId, groupId);
    }

    public FinancialRequest addRequest(Long expenditureId, Long debtorId){
        LocalDateTime generatedDate = LocalDateTime.now();
        AppUser appUser = appUserRepository.findById(debtorId).orElseThrow();
        Expenditure expenditure = expenditureRepository.findById(expenditureId).orElseThrow();

        return financialRequestRepository.save(new FinancialRequest(1L,generatedDate,10.2, Status.UNACCEPTED,expenditure,appUser));
    }
}
