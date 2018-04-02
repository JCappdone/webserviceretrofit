package com.example.webservicesqite2.database;

/**
 * Created by shriji on 31/3/18.
 */

public interface DBConstants {

    String TABLE_NAME = "userTable";
    String COLUMN_ID = "userId";
    String COLUMN_NAME = "userName";
    String COLUMN_PHONE = "userPhone";
    String COLUMN_IMAGE = "userImage";
    String COLUMN_STATUS = "deleteStatus";

    String[] COLUMN_ALL = {COLUMN_ID, COLUMN_NAME, COLUMN_PHONE, COLUMN_IMAGE,COLUMN_STATUS};

    String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PHONE + " TEXT," +
                    COLUMN_IMAGE + " TEXT," +
                    COLUMN_STATUS + " TEXT" + ");";

    String SQL_DELETE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


}
