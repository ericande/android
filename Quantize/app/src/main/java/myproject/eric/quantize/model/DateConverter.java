package myproject.eric.quantize.model;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateConverter {
    @TypeConverter
    public static LocalDateTime fromUnix(long aEpochSeconds) {
        return LocalDateTime.ofEpochSecond(aEpochSeconds, 0, ZoneOffset.UTC);
    }

    @TypeConverter
    public static long toUnix(LocalDateTime aLocalDateTime) {
        return aLocalDateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
