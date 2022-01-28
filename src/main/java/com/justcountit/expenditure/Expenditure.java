package com.justcountit.expenditure;

import com.justcountit.group.Group;
import com.justcountit.user.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // For hibernate
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
    private Double price;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false)
    private LocalDateTime expenditureDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private AppUser creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    public Expenditure(Double price,
                       String title,
                       AppUser creator,
                       Group group) {
        this.price = price;
        this.title = title;
        this.creator = creator;
        this.group = group;
    }
}
