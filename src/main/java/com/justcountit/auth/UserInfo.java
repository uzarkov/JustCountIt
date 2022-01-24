package com.justcountit.auth;

import com.justcountit.user.AppUser;

public record UserInfo(Long id, String email, String name, String surname, String nickname) {
    public static UserInfo from(AppUser appUser) {
        return new UserInfo(
                appUser.getId(),
                appUser.getEmail(),
                appUser.getName(),
                appUser.getSurname(),
                appUser.getNickname()
        );
    }
}
