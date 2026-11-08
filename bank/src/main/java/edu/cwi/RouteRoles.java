package edu.cwi;

import io.javalin.http.Context;

public class RouteRoles {

    public static boolean isAuthenticated(Context ctx) {
        return ctx.cookie("username") != null;
        
    }

    config.routes.get("/account", ctx -> {

    if(!RouteRoles.isAuthenticated(ctx)) {
        ctx.redirect("/login");
        return;
    }

    ctx.render("account.html");
});
}