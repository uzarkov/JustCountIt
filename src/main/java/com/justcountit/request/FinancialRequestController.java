package com.justcountit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/financial-requests")
@RequiredArgsConstructor
public class FinancialRequestController {
    private final FinancialRequestService financialRequestService;

    @PatchMapping("/{requestId}/accept")
    public ResponseEntity<?> acceptRequest(@PathVariable Long requestId, Principal principal) {
        financialRequestService.acceptFinancialRequest(requestId, principal);
        return ResponseEntity.ok().build();
    }
}
