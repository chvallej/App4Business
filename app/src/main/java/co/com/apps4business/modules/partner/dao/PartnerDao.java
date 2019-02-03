package co.com.apps4business.modules.partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import co.com.apps4business.modules.partner.model.Partner;

import java.util.List;

@Dao
public interface PartnerDao {

    @Query("SELECT * FROM partner WHERE id = :id")
    public Partner fetchPartnerById(int id);

    @Query("SELECT * FROM partner")
    public List<Partner> fetchAllPartners();

    // add partner
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long addPartner(Partner partner);

    @Query("DELETE FROM partner")
    public void deleteAllPartner();

    @Query("SELECT COUNT(id) FROM partner WHERE id = :id")
    public Integer partnerExist(int id);
}
