package ru.nehodov.todolist.stores;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.nehodov.todolist.models.Task;

public class SqlTaskStore implements IStore {

    private SQLiteDatabase db;

    private static final String[] projection = {
            TaskDbContract.TasksTable.COLUMN_NAME_ID,
            TaskDbContract.TasksTable.COLUMN_NAME_NAME,
            TaskDbContract.TasksTable.COLUMN_NAME_DESC,
            //추가
            TaskDbContract.TasksTable.COLUMN_NAME_ALARM_TIME,
            TaskDbContract.TasksTable.COLUMN_NAME_PERIOD,
            TaskDbContract.TasksTable.COLUMN_NAME_END_ALARM,
            //끝
            TaskDbContract.TasksTable.COLUMN_NAME_CREATED,
            TaskDbContract.TasksTable.COLUMN_NAME_DONE
    };

    public SqlTaskStore(SQLiteDatabase db) {
        this.db = db;
    }

    //여러 task 값들을 불러올때
    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> result = new ArrayList<>();
        try (Cursor cursor = db.query(TaskDbContract.TasksTable.TABLE_NAME,
                null, null, null,
                null, null, null)){
            cursor.moveToFirst();
            //다음 커서가 비어있지 않으면
            while (!cursor.isAfterLast()) {
                result.add(makeTaskFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        return result;
    }

    //하나의 task만
    @Override
    public Task getTask(int taskId) {
        Task task = null;
        try (Cursor cursor = db.query(TaskDbContract.TasksTable.TABLE_NAME,
                projection,
                TaskDbContract.TasksTable.COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(taskId)},
                null, null, null)){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                task = makeTaskFromCursor(cursor);
                cursor.moveToNext();
            }
        }
        return task;
    }

    //DB에 데이터 저장
    @Override
    public void addTask(Task newTask) {
        ContentValues values = new ContentValues();
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_NAME, newTask.getName());
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_DESC, newTask.getDesc());
        //추가
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_ALARM_TIME, newTask.getAlarmTime());
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_PERIOD, newTask.getPeriod());
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_END_ALARM, newTask.getEndAlarm());
        //끝
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_CREATED, newTask.getCreated());
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_DONE, newTask.getDoneDate());

        db.insert(TaskDbContract.TasksTable.TABLE_NAME, null, values);
    }

    //DB 데이터 업데이트
    @Override
    public void replaceTask(Task task, int taskId) {
        ContentValues values = new ContentValues();
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_NAME, task.getName());
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_DESC, task.getDesc());
        //추가
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_ALARM_TIME, task.getAlarmTime());
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_PERIOD, task.getPeriod());
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_END_ALARM, task.getEndAlarm());
        //끝
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_CREATED, task.getCreated());
        values.put(TaskDbContract.TasksTable.COLUMN_NAME_DONE, task.getDoneDate());
        db.update(TaskDbContract.TasksTable.TABLE_NAME, values,
                TaskDbContract.TasksTable.COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(taskId)});
    }

    //DB 데이터 하나 삭제
    @Override
    public void deleteTask(int taskId) {
        db.delete(TaskDbContract.TasksTable.TABLE_NAME,
                TaskDbContract.TasksTable.COLUMN_NAME_ID + " = ?",
                new String[]{ String.valueOf(taskId)
                });
    }

    //DB 데이터 전부다 삭제
    @Override
    public void deleteAll() {
        Log.d("meg","-----------------------------SqlTaskStore.deleteAll-------------------------------------");
        db.delete(TaskDbContract.TasksTable.TABLE_NAME,
                null, null);
    }

    //task 완료
    @Override
    public void doTask(int taskId) {
        Log.d("meg","-----------------------------SqlTaskStore.doTask-------------------------------------");
        Task task = getTask(taskId);
        task.doTask();
        replaceTask(task, task.getId());
    }

    //task 검색
    @Override
    public List<Task> searchTasks(String query) {
        Log.d("meg","-----------------------------SqlTaskStore.searchTasks-------------------------------------");
        List<Task> result = new ArrayList<>();
        try (Cursor cursor = db.query(TaskDbContract.TasksTable.TABLE_NAME, projection,
                TaskDbContract.TasksTable.COLUMN_NAME_NAME + " LIKE '%" + query + "%' OR "
                        + TaskDbContract.TasksTable.COLUMN_NAME_CREATED + " LIKE '%" + query + "%'",
                null,
                null, null, null)){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result.add(makeTaskFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        return result;
    }

    //task 형태
    private Task makeTaskFromCursor(Cursor cursor) {
        Log.d("meg","-----------------------------SqlTaskStore.makeTaskFormCursor-------------------------------------");
        int taskId = cursor.getInt(cursor.getColumnIndex(TaskDbContract.TasksTable.COLUMN_NAME_ID));
        String taskName = cursor.getString(
                cursor.getColumnIndex(TaskDbContract.TasksTable.COLUMN_NAME_NAME));
        String taskDesc = cursor.getString(
                cursor.getColumnIndex(TaskDbContract.TasksTable.COLUMN_NAME_DESC));
        //추가
        String taskAlarmTime = cursor.getString(
                cursor.getColumnIndex(TaskDbContract.TasksTable.COLUMN_NAME_ALARM_TIME));
        String taskPeriod = cursor.getString(
                cursor.getColumnIndex(TaskDbContract.TasksTable.COLUMN_NAME_PERIOD));
        String taskEndAlarm = cursor.getString(
                cursor.getColumnIndex(TaskDbContract.TasksTable.COLUMN_NAME_END_ALARM));
        //끝
        String created = cursor.getString(
                cursor.getColumnIndex(TaskDbContract.TasksTable.COLUMN_NAME_CREATED));
        String doneTime = cursor.getString(
                cursor.getColumnIndex(TaskDbContract.TasksTable.COLUMN_NAME_DONE));
        //추가, 끝
        Task task = new Task(taskName, taskDesc, taskAlarmTime, taskPeriod, taskEndAlarm, created, doneTime);
        task.setId(taskId);
        return task;
    }
}
//완료