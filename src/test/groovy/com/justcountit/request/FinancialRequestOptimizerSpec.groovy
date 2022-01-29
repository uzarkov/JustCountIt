package com.justcountit.request

import com.justcountit.group.membership.GroupMembership
import com.justcountit.group.membership.GroupMembershipKey
import com.justcountit.user.AppUser
import spock.lang.Specification

class FinancialRequestOptimizerSpec extends Specification {
    def financialRequestRepository = Mock(FinancialRequestRepository)
    def financialRequestService = Mock(FinancialRequestService)
    def financialRequestOptimizer = new FinancialRequestOptimizer(financialRequestRepository,
                                                                  financialRequestService)

    def sampleGroupId = 1L


    def "optimizeFinancialRequestsIn()\
         SHOULD optimize the cash flow"() {
        given:
        def userA = Mock(AppUser) { getId() >> 1L }
        def groupMembershipA = Mock(GroupMembership) { getId() >> GroupMembershipKey.from(userA.getId(), sampleGroupId) }
        def userB = Mock(AppUser) { getId() >> 2L }
        def groupMembershipB = Mock(GroupMembership) { getId() >> GroupMembershipKey.from(userB.getId(), sampleGroupId) }
        def userC = Mock(AppUser) { getId() >> 3L }
        def groupMembershipC = Mock(GroupMembership) { getId() >> GroupMembershipKey.from(userC.getId(), sampleGroupId) }
        def userD = Mock(AppUser) { getId() >> 4L }
        def groupMembershipD = Mock(GroupMembership) { getId() >> GroupMembershipKey.from(userD.getId(), sampleGroupId) }

        and:
        def oldFinancialRequests = [
                FinancialRequest.create(5d, groupMembershipA, userC),
                FinancialRequest.create(10d, groupMembershipB, userA),
                FinancialRequest.create(15d, groupMembershipC, userB),
                FinancialRequest.create(5d, groupMembershipD, userB),
                FinancialRequest.create(20d, groupMembershipC, userD),
        ] as LinkedHashSet
        financialRequestService.getAllActiveFinancialRequestsIn(sampleGroupId) >> oldFinancialRequests

        when:
        financialRequestOptimizer.optimizeFinancialRequestsIn(sampleGroupId)

        then:
        1 * financialRequestRepository.deleteAll(oldFinancialRequests)
        1 * financialRequestService.addFinancialRequest(userC.getId(), userD.getId(), 15d, sampleGroupId)
        1 * financialRequestService.addFinancialRequest(userC.getId(), userB.getId(), 10d, sampleGroupId)
        1 * financialRequestService.addFinancialRequest(userC.getId(), userA.getId(), 5d, sampleGroupId)
    }
}
