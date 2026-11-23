@import java.util.List
@import edu.cwi.Account
@import edu.cwi.Transaction

@param String userName
@param Account account
@param List<Transaction> transactions

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Account ${account.getAccountId()}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
    <main class="container py-4">
      <header class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Welcome, ${userName}</h1>
        <a href="/logout">Log out</a>
      </header>

      <p><a href="/accounts">Back to accounts</a></p>
      <h2>Account details</h2>
      <dl class="row">
        <dt class="col-sm-3">Account number</dt>
        <dd class="col-sm-9">${account.getAccountId()}</dd>
        <dt class="col-sm-3">Account type</dt>
        <dd class="col-sm-9">${account.getAccountType()}</dd>
        <dt class="col-sm-3">Balance</dt>
        <dd class="col-sm-9">$${account.getBalance()}</dd>
      </dl>

      <h2>Transactions</h2>
      @if(transactions.isEmpty())
        <p>No transactions to display.</p>
      @else
        <div class="table-responsive">
          <table class="table table-striped">
            <thead>
              <tr>
                <th scope="col">Date</th>
                <th scope="col">Type</th>
                <th scope="col">Description</th>
                <th scope="col">Amount</th>
              </tr>
            </thead>
            <tbody>
              @for(Transaction transaction : transactions)
                <tr>
                  <td>${transaction.getCreatedAt()}</td>
                  <td>${transaction.getTransactionType()}</td>
                  <td>${transaction.getDescription()}</td>
                  <td>$${transaction.getAmount()}</td>
                </tr>
              @endfor
            </tbody>
          </table>
        </div>
      @endif
    </main>
  </body>
</html>
