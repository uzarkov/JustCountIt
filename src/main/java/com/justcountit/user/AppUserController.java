package com.justcountit.user;

import com.justcountit.expenditure.Expenditure;
import com.justcountit.expenditure.ExpenditureRepository;
import com.justcountit.expenditure.ExpenditureService;
import com.justcountit.group.GroupService;
import com.justcountit.request.FinancialRequest;
import com.justcountit.request.FinancialRequestRepository;
import com.justcountit.request.FinancialRequestService;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/balance/")
@RequiredArgsConstructor
@Getter

public class AppUserController {
    private final GroupService groupService;
    private final ExpenditureService expenditureService;
    private final FinancialRequestService financialRequestService;
    private List<AppUserBalance> appUserBalanceList;
    private final FinancialRequestRepository financialRequestRepository;
    private final ExpenditureRepository expenditureRepository;


    // Dorian's second use case, returns total balance of every person in group and all ownings assigned to current user
    // using new created calss appUserBalacne list which has method to compute total ownings and spendings
    @GetMapping("/{groupId}/{userId}")
    public void getGroupBalance(@PathVariable Long groupId,@PathVariable Long userId){
        appUserBalanceList = new ArrayList<>();
        Set<AppUserWithRole> appUserWithRoles = groupService.getGroupMember(groupId);
        for (var appUser: appUserWithRoles){
//            Set<Expenditure> expenditures =expenditureService.getUserExpendituresInsideGroup(appUser.getAppUser().getId(), groupId);
            Set<FinancialRequest> financialRequests = financialRequestService.getUserFinancialRequests(appUser.getAppUser().getId(), groupId);
//            appUserBalanceList.add(new AppUserBalance(appUser.getAppUser(),expenditures,financialRequests));

        }
        getMyOwnings(userId);


    }


    private void getMyOwnings(Long userId){
        AppUserBalance appUserBalance =
                appUserBalanceList.stream().filter(k -> Objects.equals(k.getAppUser().getId(), userId)).toList().get(0);


        }


    // for now it is just a sample of code we can use later to optimise creating financial requests, feel free to copy it
    @GetMapping("/{groupId}/{userId}/balance")
    public void getAllYourTransactions(@PathVariable Long groupId,@PathVariable Long userId) {
        if (appUserBalanceList == null)
            getGroupBalance(groupId,userId);
        appUserBalanceList.sort(Comparator.comparing(AppUserBalance::getTotalUserBalance));
        minCashFlow();

        System.out.println("End");
    }


    private double minOf2(double x, double y)
    {
        return Math.min(x, y);
    }

    private void minCashFlow()
    {
        int size = appUserBalanceList.size();

        double[] amount =new double[size];

        for (int i = 0 ; i < appUserBalanceList.size();i++){
            amount[i] = appUserBalanceList.get(i).getTotalUserBalance();
        }
        minCashFlowRec(amount);
    }
    private void minCashFlowRec(double[] amount)
    {

        int mxCredit = getMax(amount), mxDebit = getMin(amount);

        if (amount[mxCredit] == 0 && amount[mxDebit] == 0){
            return;
            }

        double min = minOf2(-amount[mxDebit], amount[mxCredit]);
        amount[mxCredit] -= min;
        amount[mxDebit] += min;

//        for (var financialRequests : appUserBalanceList.get(mxDebit).getFinancialRequestList()){
//            if (financialRequests.getExpenditure().getCreator().equals(appUserBalanceList.get(mxCredit).getAppUser())){
//                financialRequests.setPrice(min);
//                financialRequestRepository.save(financialRequests);


            //}

        //}
        System.out.println("Person " + mxDebit + " pays " + min
                + " to " + "Person " + mxCredit);
        System.out.println("____");

        minCashFlowRec(amount);
    }
    private int getMin(double[] arr)
    {
        int minInd = 0;
        for (int i = 1; i < appUserBalanceList.size();i++)
            if (arr[i] < arr[minInd])
                minInd = i;
        return minInd;
    }

    private int getMax(double[] arr)
    {
        int maxInd = 0;
        for (int i = 1; i < appUserBalanceList.size(); i++)
            if (arr[i] > arr[maxInd])
                maxInd = i;
        return maxInd;
    }

}
