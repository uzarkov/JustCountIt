package com.justcountit.request;

import com.justcountit.commons.Status;
import com.justcountit.expenditure.Expenditure;
import com.justcountit.user.AppUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class FinancialRequest {

    @Id
    @SequenceGenerator(
            name="request_seq",
            sequenceName = "request_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "request_seq"
    )
    private Long id;

    @Column(nullable = false)
    private LocalDateTime generatedDate = LocalDateTime.now();

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expenditure_id")
    private Expenditure expenditure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "debtor_id")
    private AppUser debtor;
}
