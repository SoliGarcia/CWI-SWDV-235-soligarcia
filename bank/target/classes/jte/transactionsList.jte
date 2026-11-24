@import java.util.List
@import java.util.Map
@import edu.cwi.Account
@import edu.cwi.Transaction
@import edu.cwi.UserAccount

@param List<Transaction> transactions
@param Account account
@param UserAccount demoAccount

@if(demoAccount != null)
    <h2>Transactions</h2>
    @if(demoAccount.transactions().isEmpty())
        <p>No transactions to display.</p>
    @else
        <ul class="list-group">
            @for(Map.Entry<String, String> transaction : demoAccount.transactions().entrySet())
                <li class="list-group-item">${transaction.getValue()}</li>
            @endfor
        </ul>
    @endif
@else
    @if(account != null)
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
                                <td>${transaction.getCreatedAt().toString()}</td>
                                <td>${transaction.getTransactionType()}</td>
                                <td>${transaction.getDescription()}</td>
                                <td>$${transaction.getAmount()}</td>
                            </tr>
                        @endfor
                    </tbody>
                </table>
            </div>
        @endif
    @endif
@endif