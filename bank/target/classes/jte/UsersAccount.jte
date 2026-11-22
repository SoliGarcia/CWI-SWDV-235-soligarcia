<%-- Java imports --%>
@import java.util.Map
@import edu.cwi.UserAccount <!----- imported model type -->

<%-- Parameters --%>
@param String title
@param UserAccount account <!----- typed parameter -->

@template.jte.accountLayout(
    title = title,
    account = @`@template.jte.userAccount(account.name(), account.formatBalance())`,
    detail = @`@template.jte.transactionsList(account.transactions())`
)