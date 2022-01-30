package com.justcountit.balance

import com.justcountit.group.Group
import com.justcountit.group.membership.GroupMembership
import com.justcountit.group.membership.GroupMembershipKey
import com.justcountit.group.membership.GroupMembershipService
import com.justcountit.request.FinancialRequest
import com.justcountit.request.FinancialRequestService
import com.justcountit.user.AppUser
import com.justcountit.user.AppUserRepository
import com.justcountit.user.AppUserService
import com.justcountit.user.ForDebtorsMetadata
import com.justcountit.user.ForMeMetadata
import com.justcountit.user.UserRequestMetadata
import spock.lang.Specification

class UserBalance extends Specification {
    def appUserRepository = Mock(AppUserRepository)
    def financialRequestService = Mock(FinancialRequestService)
    def appUserService = new AppUserService(appUserRepository)
    def sampleGroupId = 1L
    def sampleName = "Test"
    def sampleUserId = 1L

    def group = Mock(Group){
        getName() >> sampleName
        getId() >> sampleGroupId
    }

    def groupMembershipKey = Mock(GroupMembershipKey){
        getAppUserId() >> sampleUserId
        getGroupId() >> sampleGroupId
    }
    def appUser = Mock(AppUser){
        getId() >> sampleUserId
    }

    def appUser2 = Mock(AppUser){
        getId() >> 3L
    }

    def debtor = Mock(AppUser){
        getId() >> 2L
    }

    def debtor2 = Mock(AppUser){
        getId() >> 1L
    }

    def groupMembership = Mock(GroupMembership){
        getId() >> groupMembershipKey
        getAppUser() >> debtor
    }

    def groupMembership2 = Mock(GroupMembership){
        getId() >> groupMembershipKey
        getAppUser() >> debtor2
    }

    def financialRequest = Mock(FinancialRequest){
        getId() >> 1L
        getPrice() >> 11.5
        getDebtor() >> appUser
        getDebtee() >> groupMembership
    }

    def secondFinancialRequest = Mock(FinancialRequest) {
        getId() >> 1L
        getPrice() >> 22.5
        getDebtor() >> appUser2
        getDebtee() >> groupMembership2
    }

    def forMeList = new ArrayList<ForMeMetadata>(){{add(new ForMeMetadata(1L,2L,11.5)) }}
    def sampleResultForMe = new UserRequestMetadata([],forMeList)

    def "getUsersTransaction()\
         WHEN user wants to see who owes him and to whom he owes but no one owes to him and he owes to one person \
         SHOULD return list of only his active ownings"() {
        given:

        financialRequestService.getAllActiveFinancialRequestsIn(sampleGroupId) >> financialRequest

        when:
        UserRequestMetadata userRequestMetadata= appUserService.getUserRequestMetadata(financialRequest as Set<FinancialRequest>, sampleUserId)

        then:
        !userRequestMetadata.forMe().isEmpty()
        userRequestMetadata.forDebtors().isEmpty()
        userRequestMetadata == sampleResultForMe

    }

    def forDebtorList = new ArrayList<ForDebtorsMetadata>(){{add(new ForDebtorsMetadata(1L,3L,22.5)) }}
    def sampleResultForDebtor= new UserRequestMetadata(forDebtorList,[])

    def "getUsersTransaction()\
         WHEN user wants to see who owes him and to whom he owes but one person owes to him and he owes to no one \
         SHOULD return list of only person who owe to him"() {
        given:
        financialRequestService.getAllActiveFinancialRequestsIn(sampleGroupId) >> secondFinancialRequest

        when:
        UserRequestMetadata userRequestMetadata= appUserService.getUserRequestMetadata(secondFinancialRequest as Set<FinancialRequest>, sampleUserId)

        then:
        userRequestMetadata.forMe().isEmpty()
        !userRequestMetadata.forDebtors().isEmpty()
        userRequestMetadata == sampleResultForDebtor

    }
    def emptySet  = new HashSet<FinancialRequest>()
    def sampleResultEmptySet = new UserRequestMetadata([],[])


    def "getUsersTransaction()\
         WHEN user wants to see who owes him and to whom he owes but there are no transactions\
         SHOULD return empty list"() {
        given:
        financialRequestService.getAllActiveFinancialRequestsIn(sampleGroupId) >> emptySet

        when:
        UserRequestMetadata userRequestMetadata= appUserService.getUserRequestMetadata(emptySet, sampleUserId)

        then:
        userRequestMetadata.forMe().isEmpty()
        userRequestMetadata.forDebtors().isEmpty()
        userRequestMetadata == sampleResultEmptySet

    }

    def allTransactions= new UserRequestMetadata(forDebtorList,forMeList)
    def "getUsersTransaction()\
         WHEN user wants to see who owes him and to whom he owes\
         SHOULD return all transactions"() {
        given:
        financialRequestService.getAllActiveFinancialRequestsIn(sampleGroupId) >> financialRequest
        financialRequestService.getAllActiveFinancialRequestsIn(sampleGroupId) >> secondFinancialRequest
        def set = new HashSet()
        set.add(financialRequest)
        set.add(secondFinancialRequest)
        when:
        UserRequestMetadata userRequestMetadata= appUserService.getUserRequestMetadata(set, sampleUserId)

        then:
        !userRequestMetadata.forMe().isEmpty()
        !userRequestMetadata.forDebtors().isEmpty()
        userRequestMetadata == allTransactions

    }
}
