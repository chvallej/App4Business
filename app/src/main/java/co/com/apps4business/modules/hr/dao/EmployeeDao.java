package co.com.apps4business.modules.hr.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import co.com.apps4business.modules.hr.model.Employee;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee WHERE id = :id")
    public Employee fetchEmployeeById(int id);

    @Query("SELECT * FROM employee")
    public List<Employee> fetchAllEmployees();

    // add employee
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long addEmployee(Employee employee);

    @Query("DELETE FROM employee")
    public void deleteAllEmployee();

    @Query("SELECT COUNT(id) FROM employee WHERE id = :id")
    public Integer employeeExist(int id);
}
