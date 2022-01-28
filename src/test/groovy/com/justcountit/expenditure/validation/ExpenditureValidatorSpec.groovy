package com.justcountit.expenditure.validation

import com.justcountit.expenditure.ExpenditureInput
import com.justcountit.group.membership.GroupMembershipService
import com.justcountit.user.AppUser
import com.justcountit.user.AppUserService
import spock.lang.Specification

class ExpenditureValidatorSpec extends Specification {
    def appUserService = Mock(AppUserService)
    def groupMembershipService = Mock(GroupMembershipService)
    def expenditureValidator = new ExpenditureValidator(appUserService, groupMembershipService)

    def sampleTitle = "title"
    def samplePrice = 9.99d
    def sampleDebtors = [1, 2, 3]
    def sampleGroupId = 1L

    def "validateExpenditureInput()\
         WHEN title field is null\
         SHOULD throw an exception"() {
        given:
        def expenditureInput = new ExpenditureInput(null, samplePrice, sampleDebtors)

        when:
        expenditureValidator.validateExpenditureInput(expenditureInput, sampleGroupId)

        then:
        def exception = thrown(ExpenditureValidationException)
        exception.getMessage() == ExpenditureValidationException.blankField("title").getMessage()
    }

    def "validateExpenditureInput()\
         WHEN title field is an empty string\
         SHOULD throw an exception"() {
        given:
        def expenditureInput = new ExpenditureInput("", samplePrice, sampleDebtors)

        when:
        expenditureValidator.validateExpenditureInput(expenditureInput, sampleGroupId)

        then:
        def exception = thrown(ExpenditureValidationException)
        exception.getMessage() == ExpenditureValidationException.blankField("title").getMessage()
    }

    def "validateExpenditureInput()\
         WHEN title is too large\
         SHOULD throw an exception"() {
        given:
        def tooLargeTitle = "abc" * 100
        def expenditureInput = new ExpenditureInput(tooLargeTitle, samplePrice, sampleDebtors)

        when:
        expenditureValidator.validateExpenditureInput(expenditureInput, sampleGroupId)

        then:
        def exception = thrown(ExpenditureValidationException)
        exception.getMessage() == ExpenditureValidationException.titleOutOfBounds().getMessage()
    }

    def "validateExpenditureInput()\
         WHEN price field is null\
         SHOULD throw an exception"() {
        given:
        def expenditureInput = new ExpenditureInput(sampleTitle, null, sampleDebtors)

        when:
        expenditureValidator.validateExpenditureInput(expenditureInput, sampleGroupId)

        then:
        def exception = thrown(ExpenditureValidationException)
        exception.getMessage() == ExpenditureValidationException.blankField("price").getMessage()
    }

    def "validateExpenditureInput()\
         WHEN price is too small\
         SHOULD throw an exception"() {
        given:
        def tooSmallPrice = 0.0099999999999999d
        def expenditureInput = new ExpenditureInput(sampleTitle, tooSmallPrice, sampleDebtors)

        when:
        expenditureValidator.validateExpenditureInput(expenditureInput, sampleGroupId)

        then:
        def exception = thrown(ExpenditureValidationException)
        exception.getMessage() == ExpenditureValidationException.priceOutOfBounds().getMessage()
    }

    def "validateExpenditureInput()\
         WHEN price is too large\
         SHOULD throw an exception"() {
        given:
        def tooLargePrice = 999_999_999.0001d
        def expenditureInput = new ExpenditureInput(sampleTitle, tooLargePrice, sampleDebtors)

        when:
        expenditureValidator.validateExpenditureInput(expenditureInput, sampleGroupId)

        then:
        def exception = thrown(ExpenditureValidationException)
        exception.getMessage() == ExpenditureValidationException.priceOutOfBounds().getMessage()
    }

    def "validateExpenditureInput()\
         WHEN debtorsId field is null\
         SHOULD throw an exception"() {
        given:
        def expenditureInput = new ExpenditureInput(sampleTitle, samplePrice, null)

        when:
        expenditureValidator.validateExpenditureInput(expenditureInput, sampleGroupId)

        then:
        def exception = thrown(ExpenditureValidationException)
        exception.getMessage() == ExpenditureValidationException.blankField("debtorsIds").getMessage()
    }

    def "validateExpenditureInput()\
         WHEN debtorsId field is empty\
         SHOULD throw an exception"() {
        given:
        def expenditureInput = new ExpenditureInput(sampleTitle, samplePrice, [])

        when:
        expenditureValidator.validateExpenditureInput(expenditureInput, sampleGroupId)

        then:
        def exception = thrown(ExpenditureValidationException)
        exception.getMessage() == ExpenditureValidationException.noDebtors().getMessage()
    }

    def "validateExpenditureInput()\
         WHEN one of the debtors is not a member of the group\
         SHOULD throw an exception"() {
        given:
        def debtorId = 1L
        def debtorsIds = [debtorId]
        def expenditureInput = new ExpenditureInput(sampleTitle, samplePrice, debtorsIds)

        and:
        def sampleUserName = "sampleUserName"
        appUserService.getUserById(debtorId) >> Mock(AppUser) {
            getId() >> debtorId
            getName() >> sampleUserName
        }
        groupMembershipService.isMemberOf(debtorId, sampleGroupId) >> false

        when:
        expenditureValidator.validateExpenditureInput(expenditureInput, sampleGroupId)

        then:
        def exception = thrown(ExpenditureValidationException)
        exception.getMessage() == ExpenditureValidationException.debtorNotInGroup(sampleUserName).getMessage()
    }
}
