<%-- Java imports --%>
@import java.util.Map
@import gg.jte.Content


<%-- Parameters --%>
@param String title
@param edu.cwi.UserAccount account

@template.jte.accountLayout(
    title = title,
    account = @`@template.jte.userAccount(account.username(), account.balance().toString())`,
    detail = @`@template.jte.transactionsList(account.transactions())`
)