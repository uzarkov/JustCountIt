package com.justcountit.expenditure;

import com.justcountit.group.Group;
import com.justcountit.request.FinancialRequest;
import com.justcountit.user.AppUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expenditure {

    @Id
    @SequenceGenerator(
            name="exp_seq",
            sequenceName = "exp_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exp_seq"
    )
    private Long id;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false)
    private LocalDateTime expenditureDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private AppUser creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group groupName;

    @OneToMany(mappedBy = "expenditure")
    private Set<FinancialRequest> financialRequests;

}
