package myproject.eric.quantize.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @ColumnInfo(name = "date_created")
    public LocalDateTime dateCreated;

    @Override
    public String displayName() {
        return data + " - " + dateCreated.format(DateTimeFormatter.ofPattern("EE, MMM dd - hh:mm a"));
    }
}
