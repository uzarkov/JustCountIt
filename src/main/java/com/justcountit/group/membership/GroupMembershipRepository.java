package com.justcountit.group.membership;

import com.justcountit.group.GroupBaseData;
import com.justcountit.user.AppUserWithRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupMembershipRepository extends JpaRepository<GroupMembership,GroupMembershipKey> {
    // query to get all users with role from given group
    @Query("select new com.justcountit.user.AppUserWithRole(g.appUser, g.role) from GroupMembership g where g.group.id = :groupId ")
    Set<AppUserWithRole> getAllGroupMembers(@Param("groupId") Long groupId);

    @Query("select case when count(gm) > 0 then true else false end from GroupMembership gm " +
            "where gm.id.appUserId = :userId and gm.id.groupId = :groupId")
    boolean isMemberOf(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Query("select new com.justcountit.group.GroupBaseData(g.group.id,g.group.name,g.group.description,g.group.currency)" +
            " from GroupMembership g" +
            " where g.appUser.id = :userId ")
    List<GroupBaseData> getAllUserGroups(@Param("userId") Long userId);
}
