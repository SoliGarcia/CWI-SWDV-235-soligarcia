@param boolean error

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Online Bank Login</title>
  </head>
  <body>
    <main>
      <h1>Online Bank</h1>
      @if(error)
        <p role="alert">Login failed. Check your username and password.</p>
      @endif
      <form action="/login" method="post">
        <label for="username">Username</label>
        <input id="username" name="username" autocomplete="username" required>
        <label for="password">Password</label>
        <input id="password" name="password" type="password" autocomplete="current-password" required>
        <button type="submit">Log in</button>
      </form>
    </main>
  </body>
</html>
