package edu.cwi;

import io.javalin.http.Context;
import io.javalin.Javalin;


public class RouteRoles {

    public static boolean isAuthenticated(Context ctx) {
        return ctx.cookie("username") != null;
        
    }

    public static void registerRoutes(Javalin app) {
        config.routes.get("/account", ctx -> {
            if (!isAuthenticated(ctx)) {
                ctx.redirect("/login");
                return;
            }

            ctx.render("account.html");
        });
    }
}