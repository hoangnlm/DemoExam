package vn.t3h.demoexam.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import vn.t3h.demoexam.model.Email;

/**
 * Created by Hoang on 8/13/16.
 */

public class DBContext extends SQLiteOpenHelper {
    public final static String DB_NAME = "Email.db";
    public final static int DB_VERSION = 1;

    public DBContext(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Email.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
