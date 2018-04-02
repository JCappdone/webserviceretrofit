package com.example.webservicesqite2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.webservicesqite2.Models.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shriji on 31/3/18.
 */

public class DBOperations {

    public Context mContext;
    public SQLiteOpenHelper mSQLiteOpenHelper;
    public SQLiteDatabase mSQLiteDatabase;

    public DBOperations(Context context) {
        mContext = context;
        mSQLiteOpenHelper = new DBHelper(mContext);
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    public void openDB() {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    public UserModel insertUser(UserModel userModel) {
        ContentValues values = new ContentValues();
        values.put(DBConstants.COLUMN_ID,userModel.getId());
        values.put(DBConstants.COLUMN_NAME, userModel.getName());
        values.put(DBConstants.COLUMN_PHONE, userModel.getPhone());
        values.put(DBConstants.COLUMN_IMAGE, userModel.getImage());
        values.put(DBConstants.COLUMN_STATUS,userModel.getStatus());
        mSQLiteDatabase.insert(DBConstants.TABLE_NAME,null,values);
        return userModel;
    }


    public List<UserModel> getAllUser() {
        List<UserModel> mList = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query(DBConstants.TABLE_NAME, DBConstants.COLUMN_ALL,
                DBConstants.COLUMN_STATUS +"=? ", new String[] {"Enable"} , null, null, null);
        while (cursor.moveToNext()) {
            UserModel userModel = new UserModel();
            userModel.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_ID)));
            userModel.setName(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_NAME)));
            userModel.setPhone(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_PHONE)));
            userModel.setImage(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_IMAGE)));
            userModel.setStatus(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_STATUS)));
            mList.add(userModel);
        }
        return mList;
    }

    public void deleteItemById(int id,UserModel userModel){
        ContentValues values = new ContentValues();
        values.put(DBConstants.COLUMN_STATUS,userModel.getStatus());
        mSQLiteDatabase.update(DBConstants.TABLE_NAME,
                values,
                DBConstants.COLUMN_ID + " =?  AND "+DBConstants.COLUMN_STATUS + " =?",new String[] {String.valueOf(id),"Disable"});
    }

    public void updateItemById(int id,UserModel userModel){
        ContentValues values = new ContentValues();
        values.put(DBConstants.COLUMN_NAME, userModel.getName());
        values.put(DBConstants.COLUMN_PHONE, userModel.getPhone());
        values.put(DBConstants.COLUMN_IMAGE, userModel.getImage());
        values.put(DBConstants.COLUMN_STATUS,userModel.getStatus());
        mSQLiteDatabase.update(DBConstants.TABLE_NAME,
                values,
                DBConstants.COLUMN_ID + " =?",new String[] {String.valueOf(id)});
    }
}
