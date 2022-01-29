package com.justcountit.request;

import com.justcountit.commons.Status;
import com.justcountit.group.membership.GroupMembership;
import com.justcountit.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "debtee_id", referencedColumnName = "appuser_id", nullable = false),
            @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false)
    })
    private GroupMembership debtee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "debtor_id")
    private AppUser debtor;

    private FinancialRequest(double price,
                             GroupMembership debtee,
                             AppUser debtor,
                             Status status) {
        this.price = price;
        this.debtee = debtee;
        this.debtor = debtor;
        this.status = status;
    }

    public static FinancialRequest create(double price,
                                          GroupMembership debtee,
                                          AppUser debtor) {
        return new FinancialRequest(price, debtee, debtor, Status.UNACCEPTED);
    }
}
