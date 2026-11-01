@import java.util.Map 

<%-- Parameters --%>
@param Map<String, String> transactions

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Transactions</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/styles.css">
  </head>
  <body>
    <main id="main" class="content"></main>
    <h1>Transactions</h1>
    <form method="post" action="transactions"></form><button type="submit">Create</button></form>
    <form method="post" action="transactions/1"><button type="submit">Update</button></form>
    <form method="post" action="transactions/1"><button type="submit">Delete</button></form>
    <ul>
		<%-- Loop through the transactions map --%>
		@for(var entry : transactions.entrySet())
			<li>${entry.getKey()}: ${entry.getValue()}</li>
		@endfor
    </ul>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
