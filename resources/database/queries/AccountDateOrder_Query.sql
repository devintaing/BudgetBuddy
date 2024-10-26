SELECT
    AccountName, OpeningDate, ROUND(AccountBalance, 2) AS AccountBalance
FROM
    Accounts
ORDER BY
    OpeningDate DESC