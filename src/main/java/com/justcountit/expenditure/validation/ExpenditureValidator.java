package com.justcountit.expenditure.validation;

import com.justcountit.expenditure.ExpenditureInput;
import com.justcountit.group.membership.GroupMembershipService;
import com.justcountit.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ExpenditureValidator {
    private final AppUserService appUserService;
    private final GroupMembershipService groupMembershipService;

    public void validateExpenditureInput(ExpenditureInput expenditureInput, Long groupId) {
        checkIfFieldsArePresent(expenditureInput);
        validateTitleOf(expenditureInput);
        validatePriceOf(expenditureInput);
        validateDebtorsIn(expenditureInput, groupId);
    }

    private void checkIfFieldsArePresent(ExpenditureInput expenditureInput) {
        var title = expenditureInput.title();
        if (title == null || title.isBlank()) {
            throw ExpenditureValidationException.blankField("title");
        }

        var price = expenditureInput.price();
        if (price == null) {
            throw ExpenditureValidationException.blankField("price");
        }

        var debtorsIds = expenditureInput.debtorsIds();
        if (debtorsIds == null) {
            throw ExpenditureValidationException.blankField("debtorsIds");
        }
        if (debtorsIds.isEmpty()) {
            throw ExpenditureValidationException.noDebtors();
        }
    }

    private void validateTitleOf(ExpenditureInput expenditureInput) {
        if (expenditureInput.title().length() > 60) {
            throw ExpenditureValidationException.titleOutOfBounds();
        }
    }

    private void validatePriceOf(ExpenditureInput expenditureInput) {
        var price = expenditureInput.price();

        if (doesNotHaveTwoFractionDigits(price) || price > 999_999_999) {
            throw ExpenditureValidationException.priceOutOfBounds();
        }

        if (doesNotHaveTwoFractionDigits(expenditureInput.pricePerDebtor())) {
            throw ExpenditureValidationException.pricePerDebtorOutOfBounds();
        }
    }

    private boolean doesNotHaveTwoFractionDigits(double price) {
        return BigDecimal.valueOf(price).compareTo(BigDecimal.valueOf(0.01d)) < 0;
    }

    private void validateDebtorsIn(ExpenditureInput expenditureInput, Long groupId) {
        var debtorsIds = expenditureInput.debtorsIds();

        var debtors = debtorsIds.stream()
                                .map(appUserService::getUserById)
                                .toList();

        debtors.stream()
               .filter(debtor -> !groupMembershipService.isMemberOf(debtor.getId(), groupId))
               .findAny()
               .ifPresent(debtor -> {
                   throw ExpenditureValidationException.debtorNotInGroup(debtor.getName());
               });
    }
}
