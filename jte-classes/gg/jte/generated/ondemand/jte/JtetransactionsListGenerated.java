package gg.jte.generated.ondemand.jte;
import java.util.List;
import java.util.Map;
import edu.cwi.Account;
import edu.cwi.Transaction;
import edu.cwi.UserAccount;
@SuppressWarnings("unchecked")
public final class JtetransactionsListGenerated {
	public static final String JTE_NAME = "jte/transactionsList.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,4,6,6,6,6,10,10,10,12,12,14,14,16,16,17,17,17,18,18,20,20,21,21,22,22,24,24,26,26,38,38,40,40,40,41,41,41,42,42,42,43,43,43,45,45,49,49,50,50,51,51,51,51,6,7,8,8,8,8};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, List<Transaction> transactions, Account account, UserAccount demoAccount) {
		jteOutput.writeContent("\r\n");
		if (demoAccount != null) {
			jteOutput.writeContent("\r\n    <h2>Transactions</h2>\r\n    ");
			if (demoAccount.transactions().isEmpty()) {
				jteOutput.writeContent("\r\n        <p>No transactions to display.</p>\r\n    ");
			} else {
				jteOutput.writeContent("\r\n        <ul class=\"list-group\">\r\n            ");
				for (Map.Entry<String, String> transaction : demoAccount.transactions().entrySet()) {
					jteOutput.writeContent("\r\n                <li class=\"list-group-item\">");
					jteOutput.setContext("li", null);
					jteOutput.writeUserContent(transaction.getValue());
					jteOutput.writeContent("</li>\r\n            ");
				}
				jteOutput.writeContent("\r\n        </ul>\r\n    ");
			}
			jteOutput.writeContent("\r\n");
		} else {
			jteOutput.writeContent("\r\n    ");
			if (account != null) {
				jteOutput.writeContent("\r\n        <h2>Transactions</h2>\r\n        ");
				if (transactions.isEmpty()) {
					jteOutput.writeContent("\r\n            <p>No transactions to display.</p>\r\n        ");
				} else {
					jteOutput.writeContent("\r\n            <div class=\"table-responsive\">\r\n                <table class=\"table table-striped\">\r\n                    <thead>\r\n                        <tr>\r\n                            <th scope=\"col\">Date</th>\r\n                            <th scope=\"col\">Type</th>\r\n                            <th scope=\"col\">Description</th>\r\n                            <th scope=\"col\">Amount</th>\r\n                        </tr>\r\n                    </thead>\r\n                    <tbody>\r\n                        ");
					for (Transaction transaction : transactions) {
						jteOutput.writeContent("\r\n                            <tr>\r\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(transaction.getCreatedAt().toString());
						jteOutput.writeContent("</td>\r\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(transaction.getTransactionType());
						jteOutput.writeContent("</td>\r\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(transaction.getDescription());
						jteOutput.writeContent("</td>\r\n                                <td>$");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(transaction.getAmount());
						jteOutput.writeContent("</td>\r\n                            </tr>\r\n                        ");
					}
					jteOutput.writeContent("\r\n                    </tbody>\r\n                </table>\r\n            </div>\r\n        ");
				}
				jteOutput.writeContent("\r\n    ");
			}
			jteOutput.writeContent("\r\n");
		}
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		List<Transaction> transactions = (List<Transaction>)params.get("transactions");
		Account account = (Account)params.get("account");
		UserAccount demoAccount = (UserAccount)params.get("demoAccount");
		render(jteOutput, jteHtmlInterceptor, transactions, account, demoAccount);
	}
}
