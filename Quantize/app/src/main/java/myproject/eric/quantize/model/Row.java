package myproject.eric.quantize.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import myproject.eric.quantize.Displayable;

@Entity(tableName = "row")
public class Row implements Displayable {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "pos")
    public int pos;

    public Row(String name, int pos) {
        this.name = name;
        this.pos = pos;
        uid = 0;
    }

    public String displayName() {
        return name;
    }
}
