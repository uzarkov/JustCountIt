package com.justcountit.group

import com.justcountit.group.membership.GroupMembershipService
import com.justcountit.group.validation.DeletingUserFromGroupException
import com.justcountit.request.FinancialRequestService
import com.justcountit.user.AppUserService
import spock.lang.Specification

import java.security.Principal

class DeleteUserFromGroup extends Specification {
    def appUserService = Mock(AppUserService)
    def groupRepository = Mock(GroupRepository)
    def groupMembershipService = Mock(GroupMembershipService)
    def financialRequestService = Mock(FinancialRequestService)
    def groupService = new GroupService(groupMembershipService,appUserService,financialRequestService,groupRepository)

    def sampleCallerId = 1L
    def sampleUserId = 2L
    def sampleUserEmail = "sampleUser@mail.com"
    def sampleGroupId = 1L
    def successfullDeletion = "Deleted successfully"

    def principal = Mock(Principal) {
        getName() >> sampleUserEmail
    }


    def "deleteUserFromGroup()\
         WHEN caller is not organizer and wants to delete any person\
         SHOULD throw an exception"() {
        given:
        principal.getName() >> sampleUserEmail
        appUserService.getUserId(sampleUserEmail) >> sampleCallerId
        groupMembershipService.isOrganizer(sampleCallerId,sampleGroupId) >> false

        when:
        groupService.checkIfOrganizer(sampleCallerId,sampleGroupId, sampleUserId)

        then:
        def ex = thrown(DeletingUserFromGroupException)
        ex.getMessage() == DeletingUserFromGroupException.notAuthorized().getMessage()
    }


    def "deleteUserFromGroup()\
         WHEN caller is organizer but the person which is going to be deleted its himself\
         SHOULD throw an exception"() {
        given:
        principal.getName() >> sampleUserEmail
        appUserService.getUserId(sampleUserEmail) >> sampleCallerId
        groupMembershipService.isOrganizer(sampleCallerId,sampleGroupId) >> true

        when:
        groupService.checkIfOrganizer(sampleCallerId,sampleGroupId, sampleCallerId)

        then:
        def ex = thrown(DeletingUserFromGroupException)
        ex.getMessage() == DeletingUserFromGroupException.notAuthorized().getMessage()
    }


    def "deleteUserFromGroup()\
         WHEN caller is not organizer and wants to delete himself\
         SHOULD throw an exception"() {
        given:
        principal.getName() >> sampleUserEmail
        appUserService.getUserId(sampleUserEmail) >> sampleCallerId
        groupMembershipService.isOrganizer(sampleCallerId,sampleGroupId) >> false

        when:
        groupService.checkIfOrganizer(sampleCallerId,sampleGroupId, sampleUserId)

        then:
        def ex = thrown(DeletingUserFromGroupException)
        ex.getMessage() == DeletingUserFromGroupException.notAuthorized().getMessage()
    }



    def "deleteUserFromGroup()\
         WHEN caller is organizer but the person which is going to be deleted has pending transactions\
         SHOULD throw an exception"() {
        given:
        financialRequestService.hasFinancialRequests(sampleUserId,sampleGroupId) >> true
        when:
        groupService.deleteUserFromGroup(sampleUserId,sampleGroupId)
        then:
        def ex = thrown(DeletingUserFromGroupException)
        ex.getMessage() == DeletingUserFromGroupException.pendingTransaction().getMessage()
    }

    def "deleteUserFromGroup()\
         WHEN caller is organizer and the person which is going to be deleted has no pending transactions\
         SHOULD return successfully deleted message"() {
        given:
        principal.getName() >> sampleUserEmail
        appUserService.getUserId(sampleUserEmail) >> sampleCallerId
        groupMembershipService.isOrganizer(sampleCallerId,sampleGroupId) >> true
        financialRequestService.hasFinancialRequests(sampleUserId,sampleGroupId) >> false
        groupMembershipService.deleteUserFromGroupMembership(sampleUserId, sampleGroupId) >> successfullDeletion

        when:
        groupService.checkIfOrganizer(sampleCallerId,sampleGroupId, sampleUserId)
        var result = groupService.deleteUserFromGroup(sampleUserId,sampleGroupId)
        then:
        result == "Deleted successfully"

    }


}
