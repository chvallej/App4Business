package co.com.apps4business.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import co.com.apps4business.config.AppConstants;
import co.com.apps4business.modules.hr.dao.EmployeeDao;
import co.com.apps4business.modules.hr.model.Employee;
import co.com.apps4business.modules.partner.dao.PartnerDao;
import co.com.apps4business.modules.partner.model.Partner;
import co.com.apps4business.modules.security.dao.RoleDao;
import co.com.apps4business.modules.security.dao.UserDao;
import co.com.apps4business.modules.security.model.Role;
import co.com.apps4business.modules.security.model.User;

@Database(entities = {User.class, Partner.class, Employee.class, Role.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract RoleDao getRoleDao();
    public abstract PartnerDao getPartnerDao();
    public abstract EmployeeDao getEmployeeDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDb(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, AppConstants.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
