@import java.util.List
@import edu.cwi.Account

@param String userName
@param List<Account> accounts

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Accounts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
    <main class="container py-4">
      <header class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Welcome, ${userName}</h1>
        <a href="/logout">Log out</a>
      </header>

      <h2>Your accounts</h2>
      @if(accounts.isEmpty())
        <p>You do not have any accounts yet.</p>
      @else
        <div class="table-responsive">
          <table class="table table-striped">
            <thead>
              <tr>
                <th scope="col">Account</th>
                <th scope="col">Type</th>
                <th scope="col">Balance</th>
                <th scope="col">Details</th>
              </tr>
            </thead>
            <tbody>
              @for(Account account : accounts)
                <tr>
                  <td>${account.getAccountId()}</td>
                  <td>${account.getAccountType()}</td>
                  <td>$${account.getBalance()}</td>
                  <td><a href="/account/${account.getAccountId()}">View account</a></td>
                </tr>
              @endfor
            </tbody>
          </table>
        </div>
      @endif
    </main>
  </body>
</html>
