package edu.cwi;

import io.javalin.http.Context;
public class RouteRoles {

    public static boolean isAuthenticated(Context ctx) {
        return ctx.cookieStore().get("user-id") != null;
    }
}