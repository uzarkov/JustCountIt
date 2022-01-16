package com.justcountit.expenditure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpenditureService {
    private final ExpenditureRepository expenditureRepository;

    public boolean hasExpenditures(Long userId, Long groupId){

        return getUserExpendituresInsideGroup(userId, groupId).isEmpty();
    }

    public Set<Expenditure> getUserExpendituresInsideGroup(Long userId, Long groupId) {

        return expenditureRepository.getAllByCreatorAndGroupName(userId, groupId);
    }
}
