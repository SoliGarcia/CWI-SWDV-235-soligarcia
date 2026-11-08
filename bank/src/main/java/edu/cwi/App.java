package edu.cwi;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.crud;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
            config.fileRenderer(new JavalinJte());

            config.routes.get("/", ctx -> ctx.redirect("/index.html"));
            config.routes.get("/account", ctx -> {
                String username = ctx.queryParam("username");
                String password = ctx.queryParam("password");

                if (username != null && password != null) {
                    AuthController.authenticate(ctx);
                    return;
                }

                AccountController.renderAccount(ctx);
            });
            config.routes.post("/account", AuthController::authenticate);
            config.routes.get("/logout", AuthController::logout);

            config.routes.apiBuilder(() -> {
                path("/transactions", () -> crud("{id}", new TransactionsController()));
            });

            config.routes.get("/login", ctx -> {
                ctx.render("login.jte");
            });

            config.routes.post(
                "/login",
                AuthController::login
            );

            config.routes.get(
                "/logout",
                AuthController::logout
            );

        });

        app.start(7070);
    }
}