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
    }
}