package com.justcountit.expenditure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {

    <T> Set<T> findAllByGroupId(Long groupId, Class<T> type);

    <T> T getById(Long id, Class<T> type);
}
