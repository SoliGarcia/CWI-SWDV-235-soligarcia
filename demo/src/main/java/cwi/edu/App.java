package cwi.edu;
import io.javalin.Javalin;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.routes.get("/hello/{name}", ctx -> ctx.result("Hello, " +ctx.pathParam("name")));
            
            config.routes.get("/hello/{name}/html", ctx -> ctx.html("<html> <head> <title>Javalin Intro</title> </head> <body> <h1>Hello," +ctx.pathParam("name") + "</h1> </body> </html>"));
        }).start(7070);
    }
}
