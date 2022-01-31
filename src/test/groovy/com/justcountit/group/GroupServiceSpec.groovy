package com.justcountit.group

import com.justcountit.user.AppUser
import com.justcountit.commons.Currency
import com.justcountit.group.membership.GroupMembershipService
import com.justcountit.user.AppUserService
import com.justcountit.request.FinancialRequestService
import com.justcountit.group.membership.GroupMembershipException;
import spock.lang.Specification

class GroupServiceSpec extends Specification {
    def groupMembershipService = Mock(GroupMembershipService)
    def appUserService = Mock(AppUserService)
    def financialRequestService = Mock(FinancialRequestService)
    def groupRepository = Mock(GroupRepository)
    def groupService = new GroupService(groupMembershipService,
                                        appUserService,
                                        financialRequestService,
                                        groupRepository)

    def sampleUserId = 1L
    def sampleUserEmail = "sampleuser@test.com"
    def sampleGroupName = "sample group"
    def sampleCurrency = Currency.PLN

    def sampleUser = Mock(AppUser) {
        getId() >> sampleUserId
        getEmail() >> sampleUserEmail
    }

    def "addGroup(Group group, String organizerEmail)\
         WHEN provided group data is valid and user exists\
         SHOULD create new group and return its base data"(String groupName, Currency currency) {
        given:
        def sampleGroup = new Group(groupName, currency)
        appUserService.getUserByEmail(sampleUserEmail) >> sampleUser
        1 * groupRepository.save(_ as Group) >> sampleGroup

        when:
        def groupBaseData = groupService.addGroup(sampleGroup, sampleUserEmail)

        then:
        notThrown(WrongGroupDataException)
        groupBaseData.id() == sampleGroup.id
        groupBaseData.name() == sampleGroup.name
        groupBaseData.currency() == sampleGroup.currency
        groupBaseData.description() == sampleGroup.description

        where:
        groupName << ["g",
                      "Dom tiktokerow",
                      "this_name_is_not_too_long_because_it_has_length_of_60_its_ok"]
        currency << [Currency.PLN,
                     Currency.EURO,
                     Currency.USD]

    }

    def "addGroup(Group group, String organizerEmail)\
         WHEN provided group data is not valid\
         SHOULD throw"(String groupName, Currency currency) {
        given:
        def sampleGroup = new Group(groupName, currency)

        when:
        groupService.addGroup(sampleGroup, sampleUserEmail)

        then:
        def exception = thrown(WrongGroupDataException)
        exception.getMessage() == WrongGroupDataException.wrongData().getMessage()

        where:
        groupName << ["g",
                      "this_name_is_not_too_long_because_it_has_length_of_60_its_ok",
                      "",
                      "this_name_is_too_long_because_it_has_length_of_61_this_is_bad"]
        currency << [null,
                     null,
                     Currency.PLN,
                     Currency.PLN]
    }

    def "removeGroup(Long groupId, String email)\
         WHEN caller has role organizer\
         SHOULD remove group"() {
        given:
        def sampleGroup = new Group(sampleGroupName, sampleCurrency)
        appUserService.getUserByEmail(sampleUserEmail) >> sampleUser

        groupMembershipService.isOrganizer(_ as Long, sampleGroup.id) >> true

        when:
        groupService.removeGroup(sampleGroup.id, sampleUserEmail)

        then:
        notThrown(GroupMembershipException)
        1 * groupRepository.removeById(sampleGroup.id)
    }

    def "removeGroup(Long groupId, String email)\
        WHEN caller has no role organizer\
        SHOULD throw"() {
        given:
        def sampleGroup = new Group(sampleGroupName, sampleCurrency)
        appUserService.getUserByEmail(sampleUserEmail) >> sampleUser

        groupMembershipService.isOrganizer(_ as Long, sampleGroup.id) >> false

        when:
        groupService.removeGroup(sampleGroup.id, sampleUserEmail)

        then:
        def exception = thrown(GroupMembershipException)
        exception.getMessage() == GroupMembershipException.principalNotOrganizer().getMessage()
    }
}
