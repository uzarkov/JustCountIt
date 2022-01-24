package com.justcountit.expenditure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    @Query("from Expenditure e where e.creator.id = :creatorId and e.groupName.id = :groupId")
    Set<Expenditure> getAllByCreatorAndGroupName(@Param("creatorId") Long creatorId, @Param("groupId") Long groupId);

}
