package myproject.eric.quantize.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RowDao {
    @Query("SELECT * FROM row ORDER BY pos")
    LiveData<List<Row>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Row aRow);

    @Update
    void updateRow(Row aRow);

    @Insert
    void insertAll(Row ... aRows);

    @Delete
    void delete(Row aRow);

    @Query("DELETE FROM row")
    void deleteAll();
}
