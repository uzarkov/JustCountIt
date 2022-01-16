package com.justcountit.group;

import com.justcountit.commons.Currency;

import com.justcountit.group.membership.GroupMembership;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.Set;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
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

    @OneToMany(mappedBy="group")
    private Set<GroupMembership> groupMembers;
}

