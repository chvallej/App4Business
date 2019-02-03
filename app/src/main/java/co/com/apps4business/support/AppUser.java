package co.com.apps4business.support;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;

public class AppUser {

    public static final String TAG = AppUser.class.getSimpleName();
    public static final int USER_ACCOUNT_VERSION = 1;
    private Account account;
    private Integer userId, partnerId = 0, employeeId = 0, roleId;
    private String userName, name, avatar, password, email, database = "App4Business";
    private Boolean isActive = false;


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getAndroidName() {
        return userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public void fillFromAccount(AccountManager accMgr, Account account) {
        setUserId(Integer.parseInt(accMgr.getUserData(account, "userid")));
        setPartnerId(Integer.parseInt(accMgr.getUserData(account, "partnerid")));
        setName(accMgr.getUserData(account, "name"));
        setEmail(accMgr.getUserData(account,"email"));
        setUserName(accMgr.getUserData(account, "username"));
        setIsActive(Boolean.parseBoolean(accMgr.getUserData(account, "isactive")));
        setAvatar(accMgr.getUserData(account, "avatar"));
        setPassword(accMgr.getUserData(account, "password"));
        setDatabase(accMgr.getUserData(account, "database"));
        setRoleId(Integer.parseInt(accMgr.getUserData(account, "roleId")));
        setEmployeeId(Integer.parseInt(accMgr.getUserData(account, "employeeId")));
    }

    public Bundle getAsBundle() {
        Bundle data = new Bundle();
        data.putInt("userid", getUserId());
        data.putInt("partnerid", getPartnerId());
        data.putString("username", getUserName());
        data.putString("name", getName());
        data.putString("email", getEmail());
        data.putString("avatar", getAvatar());
        data.putString("password", getPassword());
        data.putBoolean("is_active", isActive());
        data.putString("database", getDatabase());
        data.putInt("roleId", getRoleId());
        data.putInt("employeeId", getEmployeeId());

        // Converting each value to string. Account supports only string values
        for (String key : data.keySet()) {
            data.putString(key, data.get(key) + "");
        }
        return data;
    }

    public String getDBName() {
        String db_name = "AppSQLite";
        db_name += "_" + getUserName();
        db_name += "_" + getDatabase();
        return db_name + ".db";
    }

    public static AppUser current(Context context) {
        return AppAccountManager.getActiveUser(context);
    }
}
