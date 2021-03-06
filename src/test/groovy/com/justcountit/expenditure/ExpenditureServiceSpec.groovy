package com.justcountit.expenditure

import com.justcountit.expenditure.validation.ExpenditureValidator
import com.justcountit.group.Group
import com.justcountit.group.GroupService
import com.justcountit.group.membership.GroupMembershipException
import com.justcountit.group.membership.GroupMembershipService
import com.justcountit.request.FinancialRequestOptimizer
import com.justcountit.request.FinancialRequestService
import com.justcountit.user.AppUser
import com.justcountit.user.AppUserService
import spock.lang.Specification

import java.security.Principal

class ExpenditureServiceSpec extends Specification {
    def expenditureValidator = Mock(ExpenditureValidator)
    def expenditureRepository = Mock(ExpenditureRepository)
    def appUserService = Mock(AppUserService)
    def groupService = Mock(GroupService)
    def groupMembershipService = Mock(GroupMembershipService)
    def financialRequestService = Mock(FinancialRequestService)
    def financialRequestOptimizer = Mock(FinancialRequestOptimizer)
    def expenditureService = new ExpenditureService(expenditureValidator,
                                                    expenditureRepository,
                                                    appUserService,
                                                    groupService,
                                                    groupMembershipService,
                                                    financialRequestService,
                                                    financialRequestOptimizer)

    def sampleUserId = 1L
    def sampleUserEmail = "sampleUser@mail.com"
    def sampleGroupId = 1L

    def principal = Mock(Principal) {
        getName() >> sampleUserEmail
    }

    def sampleUser = Mock(AppUser) {
        getId() >> sampleUserId
        getEmail() >> sampleUserEmail
    }

    def sampleInput = new ExpenditureInput("title", 9.99d, [1L, 2L, 3L])

    def "getExpendituresMetadata()\
         WHEN caller is not a member of the requested group\
         SHOULD throw an exception"() {
        given:
        appUserService.getUserByEmail(sampleUserEmail) >> sampleUser
        groupMembershipService.isMemberOf(sampleUserId, sampleGroupId) >> false

        when:
        expenditureService.getExpendituresMetadata(sampleGroupId, principal)

        then:
        def ex = thrown(GroupMembershipException)
        ex.getMessage() == GroupMembershipException.principalNotMember().getMessage()
    }

    def "getExpendituresMetadata()\
         WHEN caller is a member of the requested group\
         SHOULD return metadata of expenditures from the group"() {
        given:
        appUserService.getUserByEmail(sampleUserEmail) >> sampleUser
        groupMembershipService.isMemberOf(sampleUserId, sampleGroupId) >> true
        expenditureRepository.findAllByGroupId(sampleGroupId, ExpenditureMetadataProjection) >> []

        when:
        def result = expenditureService.getExpendituresMetadata(sampleGroupId, principal)

        then:
        notThrown(GroupMembershipException)
        result.isEmpty()
    }

    def "addExpenditure()\
         WHEN caller is not a member of the requested group\
         SHOULD throw an exception"() {
        given:
        appUserService.getUserByEmail(sampleUserEmail) >> sampleUser
        groupMembershipService.isMemberOf(sampleUserId, sampleGroupId) >> false

        when:
        expenditureService.addExpenditure(sampleGroupId, principal, sampleInput)

        then:
        def ex = thrown(GroupMembershipException)
        ex.getMessage() == GroupMembershipException.principalNotMember().getMessage()
    }

    def "addExpenditure()\
         WHEN caller is a member of the requested group and input is valid\
         SHOULD add new expenditure and financial requests"() {
        given:
        appUserService.getUserByEmail(sampleUserEmail) >> sampleUser
        groupMembershipService.isMemberOf(sampleUserId, sampleGroupId) >> true
        groupService.getGroupBy(sampleGroupId) >> Mock(Group)
        1 * expenditureRepository.save(_ as Expenditure) >> { Expenditure expenditure -> return expenditure }

        and:
        def expectedDebts = [
                1L: sampleInput.pricePerDebtor(),
                2L: sampleInput.pricePerDebtor(),
                3L: sampleInput.pricePerDebtor(),
        ]

        when:
        expenditureService.addExpenditure(sampleGroupId, principal, sampleInput)

        then:
        notThrown(GroupMembershipException)
        1 * financialRequestService.addFinancialRequests(sampleUserId, expectedDebts, sampleGroupId)
        1 * financialRequestOptimizer.optimizeFinancialRequestsIn(sampleGroupId)
        1 * expenditureRepository.getById(_, ExpenditureMetadataProjection)
    }
}
