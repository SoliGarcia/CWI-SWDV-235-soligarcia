@import java.util.List
@import edu.cwi.Account
@import edu.cwi.Transaction
@import edu.cwi.UserAccount

@param String title
@param String userName
@param List<Account> accounts
@param Account account
@param List<Transaction> transactions
@param UserAccount demoAccount

@template.jte.accountLayout(
    title = title,
    account = @`@template.jte.userAccount(userName, accounts, account, demoAccount)`,
    detail = @`@template.jte.transactionsList(transactions, account, demoAccount)`
)