package com.justcountit.expenditure;

import java.util.List;

public record ExpenditureInput(String title, Double price, List<Long> debtorsIds) {

}
