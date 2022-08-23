package ru.project.utils;

import ru.project.entity.Authority;
import ru.project.entity.User;
import java.util.List;

public class UserUtils {
    public static User getTestUser(){
        User user = new User();
        user.setPassword("pass");
        user.setEmail("email");
        Authority authority = new Authority(1L, "ADMIN");
        user.setAuthorities(List.of(authority));
        return user;
    }
}
