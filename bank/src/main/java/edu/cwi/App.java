package edu.cwi;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;

public class App
{
    public static void main( String[] args ){

        Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
            config.routes.get("/account", ctx -> {ctx.redirect("/account.html");});
            config.routes.get("/logout", ctx -> {ctx.redirect("/");});
            config.routes.apiBuilder(() -> {path("transactions", () -> {crud("{id}", new TransactionsController());});});
            config.fileRenderer(new JavalinJte());
			config.routes.apiBuilder(() -> { crud("transactions/{id}", new TransactionsController());});
            
        }).start(7070);
        
    }
}
