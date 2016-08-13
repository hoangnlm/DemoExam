package vn.t3h.demoexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import vn.t3h.demoexam.model.Email;

/**
 * Created by Hoang on 8/13/16.
 */

public class EmailDAO {
    private Context context;
    private DBContext dbContext;

    public EmailDAO(Context context){
        this.context = context;
        dbContext = new DBContext(context);
    }

    public int insert(List<Email> emails){
        SQLiteDatabase database = dbContext.getWritableDatabase();
        database.beginTransaction();
        int count = 0;
        try {
            for (Email email : emails) {
                database.insert(Email.TABLE_NAME, null, mapContentValues(email));
            }
            database.setTransactionSuccessful();
            count = emails.size();
        }catch (Exception e){

        }
        database.endTransaction();
        database.close();
        return count;
    }

    public int delete(){
        SQLiteDatabase database = dbContext.getWritableDatabase();
        int count =  database.delete(Email.TABLE_NAME, null, null);
        return count;
    }

    public List<Email> get(){
        SQLiteDatabase database = dbContext.getReadableDatabase();
        List<Email> emails = new ArrayList<>();
        Cursor cursor = database.query(Email.TABLE_NAME, null,null,null,null,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Email email = new Email();
                    email.setId(cursor.getInt(cursor.getColumnIndex(Email.COL_ID)));
                    email.setTitle(cursor.getString(cursor.getColumnIndex(Email.COL_TITLE)));
                    email.setDescription(cursor.getString(cursor.getColumnIndex(Email.COL_DESCRIPTION)));
                    email.setDate(cursor.getLong(cursor.getColumnIndex(Email.COL_DATE)));

                    emails.add(email);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        database.close();
        return emails;
    }

    private ContentValues mapContentValues(Email email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Email.COL_ID, email.getId());
        contentValues.put(Email.COL_TITLE, email.getTitle());
        contentValues.put(Email.COL_DESCRIPTION, email.getDescription());
        contentValues.put(Email.COL_DATE, email.getDate());
        return contentValues;
    }
}
