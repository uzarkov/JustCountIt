package com.justcountit.expenditure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    Set<Expenditure> getAllByCreatorAndGroupName(Long creatorId, Long groupId);

}
