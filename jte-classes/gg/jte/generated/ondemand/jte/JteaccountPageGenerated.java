package gg.jte.generated.ondemand.jte;
import java.util.List;
import edu.cwi.Account;
import edu.cwi.Transaction;
import edu.cwi.UserAccount;
@SuppressWarnings("unchecked")
public final class JteaccountPageGenerated {
	public static final String JTE_NAME = "jte/accountPage.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,5,5,5,5,12,12,16,16,16,16,16,16,16,16,16,16,16,5,6,7,8,9,10,10,10,10};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String title, String userName, List<Account> accounts, Account account, List<Transaction> transactions, UserAccount demoAccount) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.jte.JteaccountLayoutGenerated.render(jteOutput, jteHtmlInterceptor, title, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				gg.jte.generated.ondemand.jte.JteuserAccountGenerated.render(jteOutput, jteHtmlInterceptor, userName, accounts, account, demoAccount);
			}
		}, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				gg.jte.generated.ondemand.jte.JtetransactionsListGenerated.render(jteOutput, jteHtmlInterceptor, transactions, account, demoAccount);
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String title = (String)params.get("title");
		String userName = (String)params.get("userName");
		List<Account> accounts = (List<Account>)params.get("accounts");
		Account account = (Account)params.get("account");
		List<Transaction> transactions = (List<Transaction>)params.get("transactions");
		UserAccount demoAccount = (UserAccount)params.get("demoAccount");
		render(jteOutput, jteHtmlInterceptor, title, userName, accounts, account, transactions, demoAccount);
	}
}
