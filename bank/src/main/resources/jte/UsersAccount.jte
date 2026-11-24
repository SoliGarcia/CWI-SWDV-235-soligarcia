@import java.util.List
@import edu.cwi.Account
@import edu.cwi.Transaction
@import edu.cwi.UserAccount

@param String title
@param UserAccount account

@template.jte.accountPage(
    title = title,
    userName = account.username(),
    accounts = List.<Account>of(),
    account = null,
    transactions = List.<Transaction>of(),
    demoAccount = account
)