package edu.cwi;

import io.javalin.http.Context;

public class AuthController {
    public static void authenticate(Context ctx) {
        UsersController usersController = new UsersController();
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        User user = usersController.authenticateUser(ctx, username, password);

        if (user == null) {
            ctx.cookieStore().clear();
            ctx.redirect("/login?error=1");
            return;
        }

        ctx.cookieStore().set("user-id", Integer.toString(user.getId()));
        ctx.cookieStore().set("user-role", user.getRole());
        ctx.redirect("/account");
    }

    public static void logout(Context ctx) {
        ctx.cookieStore().clear();
        ctx.redirect("/login");
    }
}