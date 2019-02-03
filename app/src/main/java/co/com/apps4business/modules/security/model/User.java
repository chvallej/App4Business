package co.com.apps4business.modules.security.model;


import android.arch.persistence.room.*;

import co.com.apps4business.converter.Converters;
import com.google.gson.annotations.Expose;
import java.util.Date;
import co.com.apps4business.modules.hr.model.Employee;
import co.com.apps4business.modules.partner.model.Partner;

@Entity(tableName = "user",
        indices = {@Index("partner_id"), @Index("employee_id")})
@TypeConverters({Converters.class})
public class User {

    @Expose()
    @PrimaryKey
    private int id;

    @Expose()
    @ColumnInfo(name = "login")
    private String login;

    @Expose()
    @ColumnInfo(name = "password")
    private String password;

    @Expose()
    @ColumnInfo(name = "name")
    private String name;

    @Expose()
    @Ignore
    private Role role;

    @Expose(serialize = false)
    @ColumnInfo(name = "role_id")
    private int roleId;

    @Expose()
    @ColumnInfo(name = "active")
    private Boolean active;

    @Expose()
    @ColumnInfo(name = "email")
    private String email;

    @Expose()
    @ColumnInfo(name = "create_date")
    private Date createDate;

    @Expose()
    @ColumnInfo(name = "write_date")
    private Date writeDate;

    @Expose()
    @ColumnInfo(name = "image")
    private byte[] image;

    @Expose(serialize = false)
    @Ignore
    private User createdBy;

    @Expose(serialize = false)
    @ColumnInfo(name = "create_by")
    private int userId;

    @Expose()
    @Ignore
    private Partner partner;

    @Expose(serialize = false)
    @ColumnInfo(name = "partner_id")
    private int partnerId;

    @Expose()
    @Ignore
    private Employee employee;

    @Expose(serialize = false)
    @ColumnInfo(name = "employee_id")
    private int employee_id;

    @Expose()
    @ColumnInfo(name = "is_customer")
    private Boolean customer;

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Boolean isNullPartner(){
        return this.partner == null;
    }

    public Boolean isNulImage() {
        return  this.image == null;
    }

    public Boolean getCustomer() {
        return customer;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Boolean isNullEmployee(){
        return this.employee == null;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
