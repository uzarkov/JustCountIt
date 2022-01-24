package com.justcountit.user;

import com.justcountit.commons.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


// class used to store user with its role
@Data
@AllArgsConstructor
public class AppUserWithRole {
    private AppUser appUser;
    private Role role;

}
