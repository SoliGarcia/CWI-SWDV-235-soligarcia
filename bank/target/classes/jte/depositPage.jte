@param String title
@param String accountReference
@param String accountType
@param String balance
@param String postUrl

@template.jte.accountLayout(
    title = title,
    account = @`
        <div>
            <h1>Make a deposit</h1>
            <dl class="row">
                <dt class="col-sm-4">Account</dt>
                <dd class="col-sm-8">${accountReference}</dd>
                <dt class="col-sm-4">Type</dt>
                <dd class="col-sm-8">${accountType}</dd>
                <dt class="col-sm-4">Current balance</dt>
                <dd class="col-sm-8">${balance}</dd>
            </dl>
            <form action="${postUrl}" method="post">
                <div class="mb-3">
                    <label class="form-label" for="deposit-amount">Amount</label>
                    <input class="form-control" id="deposit-amount" name="amount"
                           type="number" min="0.01" step="0.01" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="deposit-description">Description</label>
                    <input class="form-control" id="deposit-description" name="description"
                           type="text" maxlength="255">
                </div>
                <button class="btn btn-primary" type="submit">Submit deposit</button>
                <a class="btn btn-secondary" href="/account/${accountReference}">Cancel</a>
            </form>
        </div>
    `,
    detail = @``
)
