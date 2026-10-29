package edu.cwi;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;



public class App
{
    public static void main( String[] args ){

        var app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
            config.app.post("/account", ctx -> {ctx.redirect("/account.html");});
            config.app.get("/logout", ctx -> {ctx.redirect("/");});
        }).start(7070);
        
    }
}
