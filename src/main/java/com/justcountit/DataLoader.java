package com.justcountit;

import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner
{
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

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
    }
}