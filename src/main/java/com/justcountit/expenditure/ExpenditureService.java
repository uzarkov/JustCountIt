package com.justcountit.expenditure;
import com.justcountit.commons.Status;
import com.justcountit.group.Group;
import com.justcountit.group.GroupRepository;
import com.justcountit.request.FinancialRequest;
import com.justcountit.request.FinancialRequestRepository;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpenditureService {
    private final ExpenditureRepository expenditureRepository;
    private final AppUserRepository appUserRepository;
    private final GroupRepository groupRepository;
    private final FinancialRequestRepository financialRequestRepository;

    public boolean hasExpenditures(Long userId, Long groupId){

        return getUserExpendituresInsideGroup(userId, groupId).isEmpty();
    }

    public Set<Expenditure> getUserExpendituresInsideGroup(Long userId, Long groupId) {

        return expenditureRepository.getAllByCreatorAndGroupName(userId, groupId);
    }
    // TODO correct implementation of thist method
    public Expenditure addExpenditure(Long userId, Long groupId){
        Group group = groupRepository.findById(groupId).orElseThrow();
        AppUser appUser = appUserRepository.findById(userId).orElseThrow();
        Random rand = new Random(); //instance of random class
        int upperbound = 25;
        //generate random values from 0-24
        Long int_random = (long) rand.nextInt(upperbound);
        return expenditureRepository.save(new Expenditure(int_random,12.2,"Test Expenditure",LocalDateTime.now(), appUser,group,Collections.emptySet()));

    }
    public Expenditure setFinancialRequest(Long expenditureId, Long financialRequestId){
        Expenditure expenditure = expenditureRepository.getById(expenditureId);
        FinancialRequest financialRequest = financialRequestRepository.getById(financialRequestId);
        Set<FinancialRequest> frSet = new HashSet<>();
        frSet.add(financialRequest);
        expenditure.setFinancialRequests(frSet);
        return  expenditureRepository.save(expenditure);
    }
}
