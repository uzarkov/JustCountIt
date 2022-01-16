package com.justcountit.user;

import com.justcountit.expenditure.Expenditure;
import com.justcountit.group.membership.GroupMembership;
import com.justcountit.request.FinancialRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser {

    @Id
    @SequenceGenerator(
            name="user_seq",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq"
    )
    private Long id;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 60)
    private String surname;

    @Column(nullable = false, length = 60)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String pesel;

    @Column(nullable = false, length = 50)
    private String bankAccountNumber;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @OneToMany(mappedBy = "creator")
    private Set<Expenditure> expenditures;

    @OneToMany(mappedBy = "debtor")
    private Set<FinancialRequest> financialRequests;

    @OneToMany(mappedBy = "appUser")
    private Set<GroupMembership> groups;

    public User toUser() {
        return new User(email, password, Collections.emptyList());
    }
}
