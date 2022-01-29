package com.justcountit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FinancialRequestRepository extends JpaRepository<FinancialRequest, Long> {
    @Query("from FinancialRequest fr where fr.status = 'UNACCEPTED' and fr.debtee.id.groupId = :groupId")
    Set<FinancialRequest> getAllActiveInGroup(Long groupId);
    @Query("from FinancialRequest f where (f.debtor.id = :debtorId  or f.debtee.appUser.id = :debtorId)and f.debtee.id.groupId = :groupId and f.status = 'UNACCEPTED'")
    Set<FinancialRequest> getAllByDebtorAndExpenditure(@Param("debtorId") Long debtorId, @Param("groupId") Long groupId);
}
