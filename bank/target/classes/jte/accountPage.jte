<%-- Java imports --%>
@import edu.cwi.UserAccount
@import java.util.Map

<%-- Parameters --%>
@param String title
@param UserAccount account
@param String name
@param String balance
@param Map<String, String> transactions

@template.jte.accountLayout(
    title = title,
    account = @`@template.jte.userAccount(account.name(), account.toMoneyFormat())`,
    detail = @`@template.jte.transactionsList(account.transactions())`
)