package edu.cwi;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.body;
import static j2html.TagCreator.button;
import static j2html.TagCreator.form;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.link;
import static j2html.TagCreator.main;
import static j2html.TagCreator.title;
public class TransactionsController implements CrudHandler {

    @Override
    public void getAll(Context ctx) {
		ctx.html(
        html(
            head(
                title("Bank"),
                link().withRel("stylesheet").withHref("/css/main.css")
            ),
            body(
                main(attrs("#main.content"),
                    h1("Transactions"),

                    form()
                        .withMethod("post")
                        .withAction("/transactions")
                        .with(
                            button("Create")
                        ),

                    form()
                        .withMethod("post")
                        .withAction("/transactions/1")
                        .with(
                            button("Update")
                        ),

                    form()
                        .withMethod("post")
                        .withAction("/transactions/1")
                        .with(
                            button("Delete")
                        )
                )
            )
        ).render()
    );
    }

    @Override
    public void getOne(Context ctx, String id) {
    }

    @Override
    public void create(Context ctx) {
        ctx.redirect("/transactions");
    }

    @Override
    public void update(Context ctx, String id) {
        ctx.redirect("/transactions");
    }

    @Override
    public void delete(Context ctx, String id) {
        ctx.redirect("/transactions");

    }

}