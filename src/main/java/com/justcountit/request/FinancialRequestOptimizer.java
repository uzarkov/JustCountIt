package com.justcountit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FinancialRequestOptimizer {
    private final FinancialRequestRepository financialRequestRepository;
    private final FinancialRequestService financialRequestService;

    public void optimizeFinancialRequestsIn(Long groupId) {
        var oldFinancialRequests = financialRequestService.getAllActiveFinancialRequestsIn(groupId);
        Map<Long, Double> netCashFlow = calculateNetCashFlowIn(oldFinancialRequests);
        financialRequestRepository.deleteAll(oldFinancialRequests);
        optimizeNetCashFlow(netCashFlow, groupId);
    }

    private Map<Long, Double> calculateNetCashFlowIn(Set<FinancialRequest> financialRequests) {
        Map<Long, Double> result = new HashMap<>();

        for (var financialRequest : financialRequests) {
            var debteeId = financialRequest.getDebtee().getId().getAppUserId();
            var debtorId = financialRequest.getDebtor().getId();
            var price = financialRequest.getPrice();
            result.compute(debteeId, (id, cashFlow) -> (cashFlow) == null ? price : cashFlow + price);
            result.compute(debtorId, (id, cashFlow) -> (cashFlow) == null ? -price : cashFlow - price);
        }

        return result;
    }

    private void optimizeNetCashFlow(Map<Long, Double> netCashFlow, Long groupId) {
        while (!netCashFlow.isEmpty()) {
            var maxReceiver = getMaxReceiverFrom(netCashFlow);
            var maxGiver = getMaxGiverFrom(netCashFlow);

            var maxReceiverCashFlow = maxReceiver.getValue();
            assert maxReceiverCashFlow > 0;

            var maxGiverCashFlow = maxGiver.getValue();
            assert maxGiverCashFlow < 0;

            var debteeId = maxReceiver.getKey();
            var debtorId = maxGiver.getKey();
            var cashFlowDifference = maxReceiverCashFlow + maxGiverCashFlow;
            var nextRequestPrice = Math.abs(maxGiverCashFlow);

            if (cashFlowDifference > 0) {
                var newCashFlow = maxReceiverCashFlow + maxGiverCashFlow;
                netCashFlow.remove(debtorId);
                netCashFlow.put(debteeId, newCashFlow);
            } else if (cashFlowDifference < 0) {
                var newCashFlow = maxGiverCashFlow + maxReceiverCashFlow;
                netCashFlow.remove(debteeId);
                netCashFlow.put(debtorId, newCashFlow);
                nextRequestPrice = maxReceiverCashFlow;
            } else {
                netCashFlow.remove(debtorId);
                netCashFlow.remove(debteeId);
            }

            financialRequestService.addFinancialRequest(debteeId, debtorId, nextRequestPrice, groupId);
        }
    }

    private Map.Entry<Long, Double> getMaxReceiverFrom(Map<Long, Double> netCashFlow) {
        return netCashFlow.entrySet().stream().max(comparingByNetCashFlow())
                .orElseThrow();
    }

    private Map.Entry<Long, Double> getMaxGiverFrom(Map<Long, Double> netCashFlow) {
        return netCashFlow.entrySet().stream().min(comparingByNetCashFlow())
                .orElseThrow();
    }

    private Comparator<Map.Entry<Long, Double>> comparingByNetCashFlow() {
        return Comparator.comparingDouble(Map.Entry::getValue);
    }
}
