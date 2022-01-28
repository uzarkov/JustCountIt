package com.justcountit;

import com.justcountit.commons.Currency;
import com.justcountit.commons.Role;
import com.justcountit.group.Group;
import com.justcountit.group.GroupRepository;
import com.justcountit.group.membership.GroupMembership;
import com.justcountit.group.membership.GroupMembershipRepository;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Profile("developer")
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner
{
    private final AppUserRepository appUserRepository;
    private final GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        var user1 = addSampleUser("Andrzej");
        var user2 = addSampleUser("Julia");
        var user3 = addSampleUser("Robert");
        var user4 = addSampleUser("Krzysztof");

        var group = addSampleGroup("Grupa testowa", "Opis grupy testowej", Currency.PLN);

        addUserToGroup(user1, group, Role.ORGANIZER);
        addUserToGroup(user2, group, Role.PAYMASTER);
        addUserToGroup(user3, group, Role.MEMBER);
        addUserToGroup(user4, group, Role.MEMBER);
    }

    private AppUser addSampleUser(String name) {
        var user = new AppUser();
        user.setEmail(name.toLowerCase(Locale.ROOT) + "@test.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setName(name);
        user.setSurname(name);
        user.setNickname(name);
        user.setPesel("00123456789");
        user.setBankAccountNumber("00123412341234123412341234");
        user.setPhoneNumber("123456789");
        return appUserRepository.save(user);
    }

    private Group addSampleGroup(String name, String description, Currency currency) {
        var group = new Group();
        group.setName(name);
        group.setCurrency(currency);
        group.setDescription(description);
        return groupRepository.save(group);
    }

    private GroupMembership addUserToGroup(AppUser user, Group group, Role role) {
        var groupMembership = new GroupMembership(user, group, role);
        return groupMembershipRepository.save(groupMembership);
    }
}