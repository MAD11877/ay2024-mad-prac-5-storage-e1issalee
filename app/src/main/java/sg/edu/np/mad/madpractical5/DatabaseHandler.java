package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "usersDB.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static String COLUMN_DESCRIPTION = "description";
    public static String COLUMN_FOLLOWED = "followed";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_USERS_TABLE = "CREATE TABLE " +
                TABLE_USERS +
                "("+COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_FOLLOWED + " TEXT "
                + ")";

        db.execSQL(CREATE_USERS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getName());
        values.put(COLUMN_DESCRIPTION,user.getDescription());
        values.put(COLUMN_FOLLOWED,user.getFollowed());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USERS,null,values);
        //db.close();
    }
    public ArrayList<User> getUsers()
    {
        ArrayList<User> user_list = new ArrayList<>();
        int id;
        String name;
        String description;
        boolean followed;

        String query = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            id = Integer.parseInt(cursor.getString(0));
            name = cursor.getString(1);
            description = cursor.getString(2);
            int followed_num = cursor.getInt(3);
            if(followed_num == 1)
            {
                followed = true;
            }
            else
            {
                followed = false;
            }
            User user = new User(name,description,id,followed);
            user_list.add(user);
        }
        while(cursor.moveToNext())
        {
            id = Integer.parseInt(cursor.getString(0));
            name = cursor.getString(1);
            description = cursor.getString(2);
            int followed_num = cursor.getInt(3);
            if(followed_num == 1)
            {
                followed = true;
            }
            else
            {
                followed = false;
            }
            User user = new User(name,description,id,followed);
            user_list.add(user);
        }
        cursor.close();
//        db.close();
        return user_list;
    }

    public void updateUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOLLOWED,user.getFollowed());
        db.update(TABLE_USERS,values,"_id= ?",new String[] {String.valueOf(user.getId())});
    }
    public void deleteDB()
    {
        String query = "SELECT * FROM" + TABLE_USERS ;

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USERS,null,null);
        db.close();
    }


    public User getUsers(String username) {
        SQLiteDatabase db = getReadableDatabase();
        User user = new User("", "", 1, true);
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            user.setName(cursor.getString(1));
            user.setDescription(cursor.getString(2));
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setFollowed(Boolean.parseBoolean(cursor.getString(3)));
            cursor.close();
        }
//      db.close();
        return user;
    }
}
