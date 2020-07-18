package myproject.eric.quantize.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntryDao {
    @Query("SELECT * FROM entry")
    List<Entry> getAll();

    @Query("SELECT * FROM entry WHERE row_id LIKE :aRowId")
    List<Entry> getAllForRow(int aRowId);

    @Insert
    void insert(Entry aEntry);

    @Insert
    void insertAll(Entry ... aEntries);

    @Delete
    void delete(Entry aEntry);
}
