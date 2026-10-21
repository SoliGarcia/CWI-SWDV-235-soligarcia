import io.javalin.Javalin;


void Main() {
    var app = Javalin.create(config -> {
        config.routes.get("/", ctx -> ctx.result("Hello World"));
        config.routes.get("/hello/{name}", ctx -> {
            // some code
            ctx.result("Hello, {name}");
        });

        config.routes.get("/hello/{name}", ctx -> {
            // some code
            ctx.html("<html> <head> <title>Javalin Intro</title> </head> <body> <h1>Hello, {name}</h1> </body> </html>");
        });
    }).start(7070);
}

