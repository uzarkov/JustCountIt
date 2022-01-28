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
        validateTitleOf(expenditureInput);
        validatePriceOf(expenditureInput);
        validateDebtorsIn(expenditureInput, groupId);
    }

    private void validateTitleOf(ExpenditureInput expenditureInput) {
        var title = expenditureInput.title();

        if (title == null || title.isBlank()) {
            throw ExpenditureValidationException.blankField("title");
        }

        if (title.length() > 60) {
            throw ExpenditureValidationException.titleOutOfBounds();
        }
    }

    private void validatePriceOf(ExpenditureInput expenditureInput) {
        var price = expenditureInput.price();

        if (price == null) {
            throw ExpenditureValidationException.blankField("price");
        }

        var hasAtleastTwoFractionDigits = BigDecimal.valueOf(price).compareTo(BigDecimal.valueOf(0.01)) >= 0;
        if (!hasAtleastTwoFractionDigits || price > 999_999_999) {
            throw ExpenditureValidationException.priceOutOfBounds();
        }
    }

    private void validateDebtorsIn(ExpenditureInput expenditureInput, Long groupId) {
        var debtorsIds = expenditureInput.debtorsIds();

        if (debtorsIds == null) {
            throw ExpenditureValidationException.blankField("debtorsIds");
        }

        if (debtorsIds.isEmpty()) {
            throw ExpenditureValidationException.noDebtors();
        }

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
