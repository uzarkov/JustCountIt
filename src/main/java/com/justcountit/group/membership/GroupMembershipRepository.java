package com.justcountit.group.membership;

import com.justcountit.commons.Role;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserWithRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface GroupMembershipRepository extends JpaRepository<GroupMembership,GroupMembershipKey> {
    // query to get all users with role from given group
    @Query("select new com.justcountit.user.AppUserWithRole(g.appUser, g.role) from GroupMembership g where g.group.id = :groupId ")
    Set<AppUserWithRole> getAllGroupMembers(@Param("groupId") Long groupId);

}
