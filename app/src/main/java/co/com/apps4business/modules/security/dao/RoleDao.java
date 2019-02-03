package co.com.apps4business.modules.security.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import co.com.apps4business.modules.security.model.Role;

@Dao
public interface RoleDao {

    @Query("SELECT * FROM role WHERE id = :id")
    public Role fetchRoleById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addRole(Role role);
}
