<%-- Java imports --%>
@import java.util.Map
@import gg.jte.Content


<%-- Parameters --%>
@param String title
@param edu.cwi.UserAccount account


<head>
    <title>Account Page</title>
</head>
<body>
    <h1>{title}</h1>
    <div>
        ${content}
    </div>
</body>

@template.jte.accountLayout(
    title = title,
    account = @`@template.jte.partials.userAccount(name, balance)`,
    detail = @`@template.jte.partials.transactionsList(transactions)`
)