package com.justcountit.expenditure;

import java.util.List;

public record ExpenditureInput(String title, Double price, List<Long> debtorsIds) {
    public int numberOfDebtors() {
        return debtorsIds == null ? 0 : debtorsIds.size();
    }
}
