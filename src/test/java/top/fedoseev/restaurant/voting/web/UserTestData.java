package top.fedoseev.restaurant.voting.web;

import top.fedoseev.restaurant.voting.model.Role;
import top.fedoseev.restaurant.voting.model.User;

public class UserTestData {

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User USER = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);

}
