package doankimdinh.i.sqlitebasic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataUser extends SQLiteOpenHelper {

    public DataUser(@Nullable Context context,
                    @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE user("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sql);
    }
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        db.insert("user", null, values);
    }

    public List<User> getAll(){
        List<User> userList = new ArrayList<>();
        String sql = "select * from user";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));

                userList.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public int removeUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("User", "ID = ?",new String[] {String.valueOf(id)});
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public User getUserByID(int id){
        User user = new User();
        String select= "SELECT * FROM User WHERE ID = "+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do{
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return user;
    }
    public int Update(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());

        return db.update("User", values, "Id"+"=?", new String[]{String.valueOf(user.getId())});
    }
}
