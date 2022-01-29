package com.justcountit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FinancialRequestRepository extends JpaRepository<FinancialRequest, Long> {
    @Query("from FinancialRequest fr where fr.status = 'UNACCEPTED' and fr.debtee.id.groupId = :groupId")
    Set<FinancialRequest> getAllActiveInGroup(Long groupId);
}
