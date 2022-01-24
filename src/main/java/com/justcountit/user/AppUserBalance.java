package com.justcountit.user;


import com.justcountit.expenditure.Expenditure;
import com.justcountit.request.FinancialRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// c;ass created to store users expenditures and financial request inside a group and compute total balance
@Getter
@Setter
public class AppUserBalance {
    private AppUser appUser;
    private Set<Expenditure> expenditureList;
    private Set<FinancialRequest> financialRequestList;
    private double totalUserBalance;

    public AppUserBalance(AppUser appUser, Set<Expenditure> expenditureList, Set<FinancialRequest> financialRequestList) {
        this.appUser = appUser;
        this.expenditureList = expenditureList;
        this.financialRequestList = financialRequestList;
        getUserTotalBalance();
    }

    public void getUserTotalBalance() {
        double totalExpenditures = getSumOfExpenditures(expenditureList);
        double totalFinancialRequests = getSumOfFinancialRequests(financialRequestList);
        totalUserBalance = totalExpenditures - totalFinancialRequests;
    }

    private double getSumOfExpenditures(Set<Expenditure> expenditureList) {
        return expenditureList.stream().mapToDouble(Expenditure::getPrice).sum();
    }
    private double getSumOfFinancialRequests(Set<FinancialRequest> financialRequestList) {
        return financialRequestList.stream().mapToDouble(FinancialRequest::getPrice).sum();
    }


}
