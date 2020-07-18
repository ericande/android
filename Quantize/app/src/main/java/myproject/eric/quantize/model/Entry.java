package myproject.eric.quantize.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import myproject.eric.quantize.Displayable;

@Entity(tableName = "entry",
        indices = @Index("row_id"),
        foreignKeys = @ForeignKey(entity = Row.class,
        parentColumns = "uid",
        childColumns = "row_id",
        onDelete = ForeignKey.CASCADE))
public class Entry implements Displayable {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "row_id")
    public int rowId;

    @ColumnInfo(name = "data")
    public String data;

    @Override
    public String displayName() {
        return data;
    }
}
