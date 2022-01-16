package com.justcountit.request;

import com.justcountit.expenditure.Expenditure;
import com.justcountit.expenditure.ExpenditureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FinancialRequestService {
    private final FinancialRequestRepository financialRequestRepository;

    public boolean hasFinancialRequests(Long userId, Long groupId){

        return !getUserFinancialRequests(userId, groupId).isEmpty();
    }

    public Set<FinancialRequest> getUserFinancialRequests(Long userId, Long groupId) {
        System.out.println("Here");
        return financialRequestRepository.getAllByDebtorAndExpenditure(userId, groupId);
    }
}
