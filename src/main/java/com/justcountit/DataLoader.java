package com.justcountit;

import com.justcountit.commons.Currency;
import com.justcountit.commons.Role;
import com.justcountit.commons.Status;
import com.justcountit.expenditure.Expenditure;
import com.justcountit.expenditure.ExpenditureRepository;
import com.justcountit.group.Group;
import com.justcountit.group.GroupRepository;
import com.justcountit.group.membership.GroupMembership;
import com.justcountit.group.membership.GroupMembershipKey;
import com.justcountit.group.membership.GroupMembershipRepository;
import com.justcountit.request.FinancialRequest;
import com.justcountit.request.FinancialRequestRepository;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner
{
    private final AppUserRepository appUserRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final GroupRepository groupRepository;
    private final ExpenditureRepository expenditureRepository;
    private final FinancialRequestRepository financialRequestRepository;


    private final PasswordEncoder passwordEncoder;
    // added more users
    @Override
    public void run(ApplicationArguments args) throws Exception {
        var user = new AppUser();
        user.setEmail("whatever@what.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setName("Something");
        user.setSurname("Xd");
        user.setNickname("XD");
        user.setPesel("XD");
        user.setBankAccountNumber("XD");
        user.setPhoneNumber("XD");
        appUserRepository.save(user);

        var user2 = new AppUser();
        user2.setEmail("whatr@what.com");
        user2.setPassword(passwordEncoder.encode("pass"));
        user2.setName("Second");
        user2.setSurname("User");
        user2.setNickname("Ur");
        user2.setPesel("1234567898");
        user2.setBankAccountNumber("124125352525");
        user2.setPhoneNumber("532532523");
        appUserRepository.save(user2);

        var user3 = new AppUser();
        user3.setEmail("dorian@dorian.com");
        user3.setPassword(passwordEncoder.encode("dorian"));
        user3.setName("Dorian");
        user3.setSurname("B");
        user3.setNickname("Dr");
        user3.setPesel("1232141");
        user3.setBankAccountNumber("5123521");
        user3.setPhoneNumber("12512512");
        appUserRepository.save(user3);

        var group = new Group();
        //group.setId(1L);
        group.setName("Aa");
        group.setCurrency(Currency.PLN);
        group.setDescription("aaaa");
        groupRepository.save(group);
        var expenditure1 = new Expenditure();
        expenditure1.setId(1L);
        expenditure1.setPrice(1000);
        expenditure1.setExpenditureDate(LocalDateTime.now());
        expenditure1.setCreator(user2);
        expenditure1.setTitle("My expenditure");
        expenditure1.setGroupName(group);
        expenditureRepository.save(expenditure1);

        var financialRequest1 = new FinancialRequest();
       // financialRequest1.setId(1L);
        financialRequest1.setPrice(1000);
        financialRequest1.setDebtor(user);
        financialRequest1.setExpenditure(expenditure1);
        financialRequest1.setStatus(Status.UNACCEPTED);
        financialRequestRepository.save(financialRequest1);
        Set<FinancialRequest> fin1 = new HashSet<>();
        fin1.add(financialRequest1);
        expenditure1.setFinancialRequests(fin1);
        expenditureRepository.save(expenditure1);
        var expenditure2 = new Expenditure();
       // expenditure2.setId(2L);
        expenditure2.setPrice(7000);
        expenditure2.setExpenditureDate(LocalDateTime.now());
        expenditure2.setCreator(user3);
        expenditure2.setGroupName(group);
        expenditure2.setTitle("My second expenditure");
        expenditureRepository.save(expenditure2);


        var financialRequest2 = new FinancialRequest();
       // financialRequest2.setId(2L);
        financialRequest2.setPrice(2000);
        financialRequest2.setDebtor(user);
        financialRequest2.setExpenditure(expenditure2);
        financialRequest2.setStatus(Status.UNACCEPTED);
        financialRequestRepository.save(financialRequest2);

        var financialRequest3 = new FinancialRequest();
       // financialRequest3.setId(3L);
        financialRequest3.setPrice(5000);
        financialRequest3.setDebtor(user2);
        financialRequest3.setExpenditure(expenditure2);
        financialRequest3.setStatus(Status.UNACCEPTED);
        financialRequestRepository.save(financialRequest3);

        Set<FinancialRequest> fin2 =  new HashSet<>();
        fin2.add(financialRequest2);
        fin2.add(financialRequest3);
        expenditure2.setFinancialRequests(fin2);
        expenditureRepository.save(expenditure2);
        var gm = new GroupMembership();
        gm.setId(new GroupMembershipKey(1L,1L));
        gm.setGroup(group);
        gm.setAppUser(user);
        gm.setRole(Role.ORGANIZER);
        groupMembershipRepository.save(gm);
        var gm1 = new GroupMembership();
        gm1.setId(new GroupMembershipKey(2L,1L));
        gm1.setGroup(group);
        gm1.setAppUser(user2);
        gm1.setRole(Role.MEMBER);
        groupMembershipRepository.save(gm1);

        var gm2 = new GroupMembership();
        gm2.setId(new GroupMembershipKey(3L,3L));
        gm2.setGroup(group);
        gm2.setAppUser(user3);
        gm2.setRole(Role.MEMBER);

        groupMembershipRepository.save(gm2);


    }
}