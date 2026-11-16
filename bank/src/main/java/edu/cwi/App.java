package edu.cwi;

import static io.javalin.apibuilder.ApiBuilder.crud;
import static io.javalin.apibuilder.ApiBuilder.path;

import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
            config.fileRenderer(new JavalinJte());

            config.routes.get("/", ctx -> ctx.redirect("/login"));
            config.routes.get("/account", ctx -> {
                if (!RouteRoles.isAuthenticated(ctx)) {
                    ctx.redirect("/login");
                    return;
                }
                AccountController.renderAccount(ctx);
            });
            config.routes.get("/login", ctx -> ctx.render(
                "jte/login.jte",
                Map.of("error", ctx.queryParam("error") != null)
            ));
            config.routes.post("/login", AuthController::authenticate);
            config.routes.get("/logout", AuthController::logout);
            config.routes.apiBuilder(() -> path(
                "/transactions",
                () -> crud("{id}", new TransactionsController())
            ));
        });

        app.start(7070);
    }
}
