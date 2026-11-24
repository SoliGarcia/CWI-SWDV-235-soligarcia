package gg.jte.generated.ondemand.jte;
import java.util.List;
import edu.cwi.Account;
import edu.cwi.UserAccount;
@SuppressWarnings("unchecked")
public final class JteuserAccountGenerated {
	public static final String JTE_NAME = "jte/userAccount.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,4,10,10,10,11,11,11,15,15,15,17,17,18,18,18,19,19,21,21,23,23,35,35,37,37,37,38,38,38,39,39,39,40,40,40,40,42,42,46,46,47,47,52,52,52,54,54,54,56,56,56,58,58,59,59,60,60,60,4,5,6,7,7,7,7};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String userName, List<Account> accounts, Account account, UserAccount demoAccount) {
		jteOutput.writeContent("\r\n<div>\r\n    ");
		if (demoAccount != null) {
			jteOutput.writeContent("\r\n        <h1>Welcome, ");
			jteOutput.setContext("h1", null);
			jteOutput.writeUserContent(demoAccount.username());
			jteOutput.writeContent("</h1>\r\n        <h2>Account details</h2>\r\n        <dl class=\"row\">\r\n            <dt class=\"col-sm-4\">Balance</dt>\r\n            <dd class=\"col-sm-8\">");
			jteOutput.setContext("dd", null);
			jteOutput.writeUserContent(demoAccount.formatBalance());
			jteOutput.writeContent("</dd>\r\n        </dl>\r\n    ");
		} else {
			jteOutput.writeContent("\r\n        <h1>Welcome, ");
			jteOutput.setContext("h1", null);
			jteOutput.writeUserContent(userName);
			jteOutput.writeContent("</h1>\r\n        ");
			if (account == null) {
				jteOutput.writeContent("\r\n            <h2>Your accounts</h2>\r\n            ");
				if (accounts.isEmpty()) {
					jteOutput.writeContent("\r\n                <p>You do not have any accounts yet.</p>\r\n            ");
				} else {
					jteOutput.writeContent("\r\n                <div class=\"table-responsive\">\r\n                    <table class=\"table table-striped\">\r\n                        <thead>\r\n                            <tr>\r\n                                <th scope=\"col\">Account</th>\r\n                                <th scope=\"col\">Type</th>\r\n                                <th scope=\"col\">Balance</th>\r\n                                <th scope=\"col\">Details</th>\r\n                            </tr>\r\n                        </thead>\r\n                        <tbody>\r\n                            ");
					for (Account listedAccount : accounts) {
						jteOutput.writeContent("\r\n                                <tr>\r\n                                    <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(listedAccount.getAccountId());
						jteOutput.writeContent("</td>\r\n                                    <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(listedAccount.getAccountType());
						jteOutput.writeContent("</td>\r\n                                    <td>$");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(listedAccount.getBalance());
						jteOutput.writeContent("</td>\r\n                                    <td><a href=\"/account/");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(listedAccount.getAccountId());
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\">View account</a></td>\r\n                                </tr>\r\n                            ");
					}
					jteOutput.writeContent("\r\n                        </tbody>\r\n                    </table>\r\n                </div>\r\n            ");
				}
				jteOutput.writeContent("\r\n        ");
			} else {
				jteOutput.writeContent("\r\n            <p><a href=\"/accounts\">Back to accounts</a></p>\r\n            <h2>Account details</h2>\r\n            <dl class=\"row\">\r\n                <dt class=\"col-sm-4\">Account number</dt>\r\n                <dd class=\"col-sm-8\">");
				jteOutput.setContext("dd", null);
				jteOutput.writeUserContent(account.getAccountId());
				jteOutput.writeContent("</dd>\r\n                <dt class=\"col-sm-4\">Account type</dt>\r\n                <dd class=\"col-sm-8\">");
				jteOutput.setContext("dd", null);
				jteOutput.writeUserContent(account.getAccountType());
				jteOutput.writeContent("</dd>\r\n                <dt class=\"col-sm-4\">Balance</dt>\r\n                <dd class=\"col-sm-8\">$");
				jteOutput.setContext("dd", null);
				jteOutput.writeUserContent(account.getBalance());
				jteOutput.writeContent("</dd>\r\n            </dl>\r\n        ");
			}
			jteOutput.writeContent("\r\n    ");
		}
		jteOutput.writeContent("\r\n</div>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String userName = (String)params.get("userName");
		List<Account> accounts = (List<Account>)params.get("accounts");
		Account account = (Account)params.get("account");
		UserAccount demoAccount = (UserAccount)params.get("demoAccount");
		render(jteOutput, jteHtmlInterceptor, userName, accounts, account, demoAccount);
	}
}
