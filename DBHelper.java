package com.example.crud_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        //your database always should be unique
        DB.execSQL("create Table Userdetails(name TEXT primary key, contact TEXT, dob TEXT)");
    }//Userdetails here is our table name

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetails");
    }
    //Method to insert data into table
    public Boolean insertuserdata(String name, String contact, String dob) { //pass the strings
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); //create object contentvalues to put our table
        contentValues.put("name", name); //make sure you use matching column names as given in about method call
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);

        long result = DB.insert("Userdetails", null, contentValues);
        //use insert method, require three para, table name ,collum hack, values to insert

        if(result == -1) { //if inseretion failed
            return false;
        } else {
            return true;
        }
    }

    //Method to update data, copy above method and place here
    public Boolean updateuserdata(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //we need to delete name , we only need to update contact,dob //1
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);

        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name}); //3
        //this is for selecting a row, check whether the given name is match with exisisting name requirements

        if(cursor.getCount() > 0) {

            long result = DB.update("Userdetails", contentValues, "name=?", new String[]{name});//2
            //change insert to update, third argument, whereclause, forth is where argument to check that together

            if(result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

  // this is for delete data
    public Boolean deletedata(String name) { //once user give name that particular entry will be deleted
        SQLiteDatabase DB = this.getWritableDatabase();

        //Don't need to have any content here

        //ContentValues contentValues = new ContentValues();
        //we need to delete name , we only need to update contact,dob //1
        //contentValues.put("contact", contact);
        //contentValues.put("dob", dob);

        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        //this is for selecting a row, check whether the given name is match with exisisting name requirements

        if(cursor.getCount() > 0) {

            long result = DB.delete("Userdetails","name=?", new String[]{name});// this include only three arguments
            //change insert to update, third argument, whereclause, forth is where argument to check that together

            if(result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    // this is for view data
    public Cursor getdata() { //once user give name that particular entry show,
        //1 // make here Boolean -> Cursor
        SQLiteDatabase DB = this.getWritableDatabase();

        //2. We need to remove where clause ( where name = ?),
        // no need to have any arguments, make it null  new String[]{name}->null
        Cursor cursor = DB.rawQuery("Select * from Userdetails",null);
        //this is for selecting a row, check whether the given name is match with exisisting name requirements
        return cursor;

        //if(cursor.getCount() > 0) {

            //long result = DB.delete("Userdetails","name=?", new String[]{name});// this include only three arguments
            //change insert to update, third argument, whereclause, forth is where argument to check that together

            //if(result == -1) {
            //    return false;
           // } else {
            //    return true;
            //}
        //} else {
          //  return false;
        //}



    }


}
