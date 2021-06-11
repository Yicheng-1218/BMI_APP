package com.project.bmi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SqlDataBaseHelper extends SQLiteOpenHelper {

    public SqlDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DEBUG","SQL onCREATE");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS mybmi (time INTEGER Primary key,BMI REAL not null,status text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void clearData(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mybmi");
        onCreate(sqLiteDatabase);
    }
}
