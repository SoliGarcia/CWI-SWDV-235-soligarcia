<%-- Java imports --%>
@import java.util.Map
@import gg.jte.Content


<%-- Parameters --%>
@param String title
@param edu.cwi.UserAccount account

@template.jte.accountLayout(
    title = title,
    account = @`@template.jte.userAccount(account.name(), account.BalanceFormat())`,
    detail = @`@template.jte.transactionsList(account.transactions)`
)