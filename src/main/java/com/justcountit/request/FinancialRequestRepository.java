package com.justcountit.request;

import com.justcountit.expenditure.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FinancialRequestRepository extends JpaRepository<FinancialRequest,Long> {
    @Query("from FinancialRequest f where f.debtor.id = :debtorId and f.expenditure.groupName.id = :groupId")
    Set<FinancialRequest> getAllByDebtorAndExpenditure(@Param("debtorId") Long debtorId, @Param("groupId") Long groupId);


}
