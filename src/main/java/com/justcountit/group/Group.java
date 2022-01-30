package com.justcountit.group;

import com.justcountit.commons.Currency;

import com.justcountit.expenditure.Expenditure;
import com.justcountit.group.membership.GroupMembership;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tour_group")
public class Group {

    @Id
    @SequenceGenerator(
            name="group_seq",
            sequenceName = "group_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_seq"
    )
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(mappedBy="group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupMembership> groupMembers;

    @OneToMany(mappedBy= "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Expenditure> expenditures;

    public Group(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
        this.description = name;
        this.groupMembers = new HashSet<>();
        this.expenditures = new HashSet<>();
    }

    public void addMembership(GroupMembership membership) {
        groupMembers.add(membership);
    }
}

