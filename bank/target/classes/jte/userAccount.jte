@import java.util.List
@import edu.cwi.Account
@import edu.cwi.UserAccount

@param String userName
@param List<Account> accounts
@param Account account
@param UserAccount demoAccount

<div>
    @if(demoAccount != null)
        <h1>Welcome, ${demoAccount.username()}</h1>
        <h2>Account details</h2>
        <dl class="row">
            <dt class="col-sm-4">Balance</dt>
            <dd class="col-sm-8">${demoAccount.formatBalance()}</dd>
        </dl>
        <a class="btn btn-primary"
           href="/account/${demoAccount.loginUsername()}/deposit">Make a deposit</a>
    @else
        <h1>Welcome, ${userName}</h1>
        @if(account == null)
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
                            @for(Account listedAccount : accounts)
                                <tr>
                                    <td>${listedAccount.getAccountId()}</td>
                                    <td>${listedAccount.getAccountType()}</td>
                                    <td>$${listedAccount.getBalance()}</td>
                                    <td><a href="/account/${listedAccount.getAccountId()}">View account</a></td>
                                </tr>
                            @endfor
                        </tbody>
                    </table>
                </div>
            @endif
        @else
            <h2>Account details</h2>
            <dl class="row">
                <dt class="col-sm-4">Account number</dt>
                <dd class="col-sm-8">${account.getAccountId()}</dd>
                <dt class="col-sm-4">Account type</dt>
                <dd class="col-sm-8">${account.getAccountType()}</dd>
                <dt class="col-sm-4">Balance</dt>
                <dd class="col-sm-8">$${account.getBalance()}</dd>
            </dl>
            <a class="btn btn-primary"
               href="/account/${account.getAccountId()}/deposit">Make a deposit</a>
        @endif
    @endif
</div>