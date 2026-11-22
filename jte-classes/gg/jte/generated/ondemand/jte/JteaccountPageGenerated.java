package gg.jte.generated.ondemand.jte;
import java.util.Map;
import gg.jte.Content;
@SuppressWarnings("unchecked")
public final class JteaccountPageGenerated {
	public static final String JTE_NAME = "jte/accountPage.jte";
	public static final int[] JTE_LINE_INFO = {1,1,2,6,6,6,6,9,9,13,13,13,13,13,13,13,13,13,13,13,6,7,7,7,7};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String title, edu.cwi.UserAccount account) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.jte.JteaccountLayoutGenerated.render(jteOutput, jteHtmlInterceptor, title, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				gg.jte.generated.ondemand.jte.JteuserAccountGenerated.render(jteOutput, jteHtmlInterceptor, account.username(), account.balance().toString());
			}
		}, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				gg.jte.generated.ondemand.jte.JtetransactionsListGenerated.render(jteOutput, jteHtmlInterceptor, account.transactions());
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String title = (String)params.get("title");
		edu.cwi.UserAccount account = (edu.cwi.UserAccount)params.get("account");
		render(jteOutput, jteHtmlInterceptor, title, account);
	}
}
