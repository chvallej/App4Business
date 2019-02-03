package co.com.apps4business.modules.security.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import co.com.apps4business.modules.security.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE id = :userId")
    public User fetchUserById(int userId);

    @Query("SELECT * FROM user")
    public List<User> fetchAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long addUser(User user);

    @Query("SELECT COUNT(id) FROM user WHERE id = :id")
    public Integer userExist(int id);

}
