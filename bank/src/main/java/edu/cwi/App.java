package edu.cwi;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;



public class App
{
    public static void main( String[] args ){

        Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
            config.routes.get("/account", ctx -> {ctx.redirect("/account.html");});
            config.routes.get("/logout", ctx -> {ctx.redirect("/");});
        }).start(7070);   
    }
}
