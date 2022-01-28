package com.justcountit.request;

import com.justcountit.commons.Status;
import com.justcountit.expenditure.Expenditure;
import com.justcountit.expenditure.ExpenditureRepository;
import com.justcountit.group.membership.GroupMembershipRepository;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserBalance;
import com.justcountit.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class FinancialRequestService {
    private final FinancialRequestRepository financialRequestRepository;
    private final AppUserRepository appUserRepository;
    private final ExpenditureRepository expenditureRepository;
    private final GroupMembershipRepository groupMembershipRepository;



    public boolean hasFinancialRequests(Long userId, Long groupId){

        return !getUserFinancialRequests(userId, groupId).isEmpty();
    }

    public Set<FinancialRequest> getUserFinancialRequests(Long userId, Long groupId) {
        System.out.println("Here");
        return null;
    }

    // TODO adding financial request
    public FinancialRequest addRequest(Long expenditureId, Long debtorId){
        LocalDateTime generatedDate = LocalDateTime.now();
        AppUser appUser = appUserRepository.findById(debtorId).orElseThrow();
        Expenditure expenditure = expenditureRepository.findById(expenditureId).orElseThrow();
        Random rand = new Random(); //instance of random class
        int upperbound = 25;
        //generate random values from 0-24
        Long int_random = (long)rand.nextInt(upperbound);

        return null;
    }


}
