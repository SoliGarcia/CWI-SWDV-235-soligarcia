package edu.cwi;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.crud;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;

public class App
{
    public static void main( String[] args ){

        Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
            
            config.fileRenderer(new JavalinJte());

            // auth
            config.routes.beforeMatched(ctx -> {
	            if (ctx.routeRoles().isEmpty()) {
		            return; }
                String role = ctx.cookieStore().get("user-role"); // get the role from the cookie store
	            AppRole appRole = AppRole.valueOf(role);

	            if (!ctx.routeRoles().contains(appRole)) {
                    ctx.cookieStore().clear();
		        throw new UnauthorizedResponse();}
            });
            
            // routes
            config.routes.get("/account", ctx -> {ctx.redirect("/account.html");});
            config.routes.get("/logout", ctx -> {ctx.redirect("/");});
			config.routes.apiBuilder(() -> { crud("transactions/{id}", new TransactionsController());});
            

        }).start(7070);
        
    }
}
