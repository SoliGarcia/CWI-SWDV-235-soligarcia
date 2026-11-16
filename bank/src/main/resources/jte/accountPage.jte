@import edu.cwi.Account
@import edu.cwi.Transaction
@import edu.cwi.User
@import java.util.List
@import java.util.Map

@param String title
@param User user
@param List<Account> accounts
@param Map<Integer, List<Transaction>> transactionsByAccount

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
  </head>
  <body>
    <main>
      <header>
        <h1>Welcome, ${user.getFirstName()} ${user.getLastName()}</h1>
        <a href="/logout">Log out</a>
      </header>

      <h2>Your accounts</h2>
      @for(var account : accounts)
        <section>
          <h3>${account.getAccountType()} account #${account.getAccountId()}</h3>
          <p>Balance: $${account.getBalance()}</p>
          <h4>Transactions</h4>
          <ul>
            @for(var transaction : transactionsByAccount.get(account.getAccountId()))
              <li>
                ${transaction.getCreatedAt()} - ${transaction.getTransactionType()}
                $${transaction.getAmount()}
                @if(transaction.getDescription() != null)
                  - ${transaction.getDescription()}
                @endif
              </li>
            @else
              <li>No transactions to display.</li>
            @endfor
          </ul>
        </section>
      @else
        <p>No accounts are linked to your user yet.</p>
      @endfor
    </main>
  </body>
</html>
