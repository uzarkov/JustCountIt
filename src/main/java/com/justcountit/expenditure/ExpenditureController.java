package com.justcountit.expenditure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/expenditures")
@RequiredArgsConstructor
public class ExpenditureController {
    private final ExpenditureService expenditureService;

    @GetMapping(path = "/{groupId}")
    public ResponseEntity<Set<ExpenditureMetadataProjection>> getExpendituresMetadata(@PathVariable Long groupId,
                                                                                      Principal principal) {
        var expendituresMetadata = expenditureService.getExpendituresMetadata(groupId, principal);
        return ResponseEntity.ok(expendituresMetadata);
    }

    @PostMapping(path = "/{groupId}")
    public ResponseEntity<ExpenditureMetadataProjection> addExpenditure(@PathVariable Long groupId,
                                                                        @RequestBody ExpenditureInput input,
                                                                        Principal principal) {
        var expenditureMetadata = expenditureService.addExpenditure(groupId, principal, input);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expenditureMetadata);
    }
}
