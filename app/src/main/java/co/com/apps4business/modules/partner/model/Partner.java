package co.com.apps4business.modules.partner.model;

import android.arch.persistence.room.*;

import co.com.apps4business.converter.Converters;
import co.com.apps4business.modules.security.model.User;
import com.google.gson.annotations.Expose;

import java.util.Date;

@Entity(tableName = "partner", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "created_by"),
        indices = {@Index("created_by")})
@TypeConverters({Converters.class})
public class Partner {

    @Expose()
    @PrimaryKey
    private int id;

    @Expose()
    @ColumnInfo(name = "name")
    private String name;

    @Expose()
    @ColumnInfo(name = "image")
    private byte[] image;

    @Expose()
    @ColumnInfo(name = "city")
    private String city;

    @Expose()
    @ColumnInfo(name = "street")
    private String street;

    @Expose()
    @ColumnInfo(name = "is_cuatomer")
    private Boolean customer = true;

    @Expose()
    @ColumnInfo(name = "email")
    private String email;

    @Expose()
    @ColumnInfo(name = "website")
    private String website;

    @Expose()
    @ColumnInfo(name = "country")
    private String country;

    @Expose()
    @ColumnInfo(name = "fax")
    private String fax;

    @Expose()
    @ColumnInfo(name = "phone")
    private String phone;

    @Expose()
    @ColumnInfo(name = "mobile")
    private String mobile;

    @Expose()
    @ColumnInfo(name = "is_company")
    private Boolean isCompany = false;

    @Expose()
    @Ignore
    private User createdBy;

    @Expose(serialize = false)
    @ColumnInfo(name = "created_by")
    private int userId;

    @Expose()
    @ColumnInfo(name = "active")
    private Boolean active;

    @Expose()
    @ColumnInfo(name = "create_date")
    private Date createDate;

    @Expose()
    @ColumnInfo(name = "write_date")
    private Date writeDate;

    @Expose()
    @ColumnInfo(name = "document_type")
    private String documentType;

    @Expose()
    @ColumnInfo(name = "document_id")
    private String documentId;

    public Partner(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Boolean getCustomer() {
        return customer;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getCompany() {
        return isCompany;
    }

    public void setCompany(Boolean company) {
        isCompany = company;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
