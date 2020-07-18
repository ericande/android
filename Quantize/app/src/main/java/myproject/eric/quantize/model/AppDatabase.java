package myproject.eric.quantize.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quantitativeself.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Entry.class, Row.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EntryDao getEntryDao();
    public abstract RowDao getRowDao();

    private static AppDatabase INSTANCE;
    private static final int NUM_THREADS = 4;
    private static ExecutorService theWriteExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    protected static ExecutorService getWriteExecutor() {
        return theWriteExecutor;
    }

    public static void write(Runnable aDBop) {
        getWriteExecutor().execute(aDBop);
    }

    public static AppDatabase getInstance(Context aContext) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(aContext.getApplicationContext(),
                            AppDatabase.class,
                            "app_db")
                            .addCallback(myPopulateRows(aContext))
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback myPopulateRows(Context aContext) {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase aDb) {
                super.onCreate(aDb);
                String[] myExampleRowNames = aContext.getResources().getStringArray(R.array.trees);
                int myCount = myExampleRowNames.length;
                Row[] myRows = new Row[myCount];
                for (int i = 0; i < myCount; i++) {
                    myRows[i] = new Row(myExampleRowNames[i], i);
                }
                write(() -> INSTANCE.getRowDao().insertAll(myRows));
            }
        };
    }
}
