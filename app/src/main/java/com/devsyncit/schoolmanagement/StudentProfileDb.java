package com.devsyncit.schoolmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class StudentProfileDb extends SQLiteOpenHelper {

    private static String DB_NAME = "StudentProfile.db";
    private static String TABLE_NAME = "StudentProfileTable";
    private static int DB_VERSION = 1;
    private static String student_name = "student_name";
    private static String student_id = "student_id";
    private static String student_class = "student_class";
    private static String student_email = "student_email";
    private static String student_phone_number = "student_phone_number";
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + student_name + " VARCHAR(100), " + student_id + " VARCHAR(25), " + student_class + " VARCHAR(20), " + student_email + " VARCHAR(90), " + student_phone_number + " VARCHAR(50))";
    private static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private Context context;

    public StudentProfileDb(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "OnCreate called", Toast.LENGTH_LONG).show();
        } catch (Exception e) {

            Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        try{
            db.execSQL(DROP_TABLE);
            Toast.makeText(context, "OnUpgrade is called", Toast.LENGTH_LONG).show();
            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
        }


    }


    public boolean insertData(String name, String id, String std_class, String email, String mobile_number){

        SQLiteDatabase studentProfileDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(student_name, name);
        contentValues.put(student_id, id);
        contentValues.put(student_class, std_class);
        contentValues.put(student_email, email);
        contentValues.put(student_phone_number, mobile_number);
        long rowId = studentProfileDb.insert(TABLE_NAME, null, contentValues);

        boolean isSuccessfull = true;

        if (rowId==-1){
            isSuccessfull = false;
        }

        return isSuccessfull;
    }

    public Cursor selectData(String id){

        SQLiteDatabase profiledb = this.getReadableDatabase();
        Cursor cursor = profiledb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+student_id+" = "+id, null);


        return cursor;
    }
}
