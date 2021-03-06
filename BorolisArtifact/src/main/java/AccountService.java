public class AccountService {
    DBHelper myDB;
    Account lastUsedAccount;

    public AccountService(DBHelper _db)
    {
        myDB = _db;
    }

    public Account getLastUsedAccount() {
        return lastUsedAccount;
    }

    public void setLastUsedAccount(Account lastUsedAccount) {
        this.lastUsedAccount = lastUsedAccount;
    }

    public void createNewAccount(Account account)
    {
        lastUsedAccount = account;
        myDB.execUpdate(myDB.makeSQLInsertNewAcc(account));
    }


    public Account findAccountByLogin(String login)
    {
        lastUsedAccount = myDB.getAccount(myDB.makeSQLqueryGetAccByLogin(login));
        return lastUsedAccount;
    }

    public Account findAccountBySession(String session_id)
    {
        lastUsedAccount = myDB.getAccBySession(session_id);
        return lastUsedAccount;
    }

}
