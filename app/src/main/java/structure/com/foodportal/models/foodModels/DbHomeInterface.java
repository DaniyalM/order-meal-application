package structure.com.foodportal.models.foodModels;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

//@Dao
public interface DbHomeInterface {


    @Insert
    public void insert(FoodHomeModelWrapper... foodDetailModelWrappers);

    @Update
    public void update(FoodHomeModelWrapper... foodDetailModelWrappers);

    @Delete
    public void delete(FoodHomeModelWrapper foodDetailModelWrapper);



//    @Query("SELECT * FROM foodhome")
//    public List<FoodHomeModelWrapper> gethome();


}
