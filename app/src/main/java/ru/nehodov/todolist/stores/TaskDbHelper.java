package ru.nehodov.todolist.stores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class TaskDbHelper extends SQLiteOpenHelper {


    public TaskDbHelper(@Nullable Context context, String DB_NAME, SQLiteDatabase.CursorFactory factory, int DB_VERSION) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    //DB 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TaskDbContract.TasksTable.TABLE_NAME + "("
                + TaskDbContract.TasksTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskDbContract.TasksTable.COLUMN_NAME_NAME + " TEXT, "
                + TaskDbContract.TasksTable.COLUMN_NAME_DESC + " TEXT, "
                //추가
                + TaskDbContract.TasksTable.COLUMN_NAME_ALARM_TIME + " TEXT, "
                + TaskDbContract.TasksTable.COLUMN_NAME_PERIOD + " TEXT, "
                + TaskDbContract.TasksTable.COLUMN_NAME_END_ALARM + " TEXT, "
                //끝
                + TaskDbContract.TasksTable.COLUMN_NAME_CREATED + " TEXT, "
                + TaskDbContract.TasksTable.COLUMN_NAME_DONE + " TEXT DEFAULT NULL"
                + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
//완료