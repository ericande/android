package myproject.eric.quantize;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import myproject.eric.quantize.model.AppDatabase;
import myproject.eric.quantize.model.Row;

public class RowViewModel extends AndroidViewModel {
    private LiveData<List<Row>> theRows;
    private AppDatabase theDatabase;

    public RowViewModel(Application aApplication) {
        super(aApplication);
        theDatabase = AppDatabase.getInstance(aApplication);
        theRows = theDatabase.getRowDao().getAll();
    }

    LiveData<List<Row>> getRows() {
        return theRows;
    }

    void swap(Row i, Row j) {
        int tmp = i.pos;
        i.pos = j.pos;
        j.pos = tmp;
        AppDatabase.write(() -> theDatabase.getRowDao().updateRow(i));
        AppDatabase.write(() -> theDatabase.getRowDao().updateRow(j));
    }

    void add(Row aRow) {
        AppDatabase.write(() -> theDatabase.getRowDao().insert(aRow));
    }

    void update(Row aRow) {
        AppDatabase.write(() -> theDatabase.getRowDao().updateRow(aRow));
    }

    void remove(Row aRow) {
        AppDatabase.write(() -> theDatabase.getRowDao().delete(aRow));
    }

    void removeAll() {
        AppDatabase.write(() -> theDatabase.getRowDao().deleteAll());
    }
}
