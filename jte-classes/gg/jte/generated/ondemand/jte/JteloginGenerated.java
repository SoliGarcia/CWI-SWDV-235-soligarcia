package gg.jte.generated.ondemand.jte;
@SuppressWarnings("unchecked")
public final class JteloginGenerated {
	public static final String JTE_NAME = "jte/login.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,12,12,12,14,14,25,25,25,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, boolean error) {
		jteOutput.writeContent("\r\n<!doctype html>\r\n<html lang=\"en\">\r\n  <head>\r\n    <meta charset=\"utf-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n    <title>Online Bank Login</title>\r\n  </head>\r\n  <body>\r\n    <main>\r\n      <h1>Online Bank</h1>\r\n      ");
		if (error) {
			jteOutput.writeContent("\r\n        <p role=\"alert\">Login failed. Check your username and password.</p>\r\n      ");
		}
		jteOutput.writeContent("\r\n      <form action=\"/login\" method=\"post\">\r\n        <label for=\"username\">Username</label>\r\n        <input id=\"username\" name=\"username\" autocomplete=\"username\" required>\r\n        <label for=\"password\">Password</label>\r\n        <input id=\"password\" name=\"password\" type=\"password\" autocomplete=\"current-password\" required>\r\n        <button type=\"submit\">Log in</button>\r\n      </form>\r\n    </main>\r\n  </body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		boolean error = (boolean)params.get("error");
		render(jteOutput, jteHtmlInterceptor, error);
	}
}
