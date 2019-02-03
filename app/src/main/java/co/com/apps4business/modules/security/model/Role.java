package co.com.apps4business.modules.security.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import co.com.apps4business.converter.Converters;
import com.google.gson.annotations.Expose;

@Entity(tableName = "role")
@TypeConverters({Converters.class})
public class Role {

    @Expose()
    @PrimaryKey
    private int id;

    @Expose()
    @ColumnInfo(name = "name")
    private String name;

    @Expose()
    @ColumnInfo(name = "description")
    private String description;

    public Role(){

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
