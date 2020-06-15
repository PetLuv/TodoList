package ru.nehodov.todolist.stores;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ru.nehodov.todolist.models.Task;
import ru.nehodov.todolist.utils.DateTimeFormatter;

public class MemTaskStore implements IStore {

    private static IStore INSTANCE;

    private final Map<Integer, Task> tasks = new LinkedHashMap<>();

    private static int id = 0;

    //테스트용 리스트
    private MemTaskStore() {
        /*for (int i = 0; i < 12; i ++) {
            //추가, 끝
            Task task = new Task("New Task " + i, "Description task " + i,
                    "Alarm Time" + i, "Period" + i, "End Alarm" + i, DateTimeFormatter.format(new Date()));
            Log.d("tag", task.getDesc());
            Log.d("tag2","--------------------------------------");
            task.setId(id++);
            tasks.put(task.getId(), task);
            i++;
            //추가, 끝
            Task closedTask = new Task("New Task " + i, "Description task " + i,
                    "Alarm Time" + i, "Period" + i, "End Alarm" + i, DateTimeFormatter.format(new Date()));
            closedTask.setId(id++);
            closedTask.doTask();
            tasks.put(closedTask.getId() ,closedTask);
        }*/
    }

    public static IStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MemTaskStore();
        }
        return INSTANCE;
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> result = new ArrayList<>(tasks.values());
        return result;
    }

    @Override
    public Task getTask(int taskId) {
        return tasks.get(taskId);
    }

    @Override
    public void addTask(Task newTask) {
        newTask.setId(id++);
        this.tasks.put(newTask.getId(), newTask);
    }

    @Override
    public void replaceTask(Task task, int taskId) {
        Task taskToReplace = tasks.get(taskId);
        taskToReplace = task;
    }

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void deleteAll() {
        tasks.clear();
    }

    @Override
    public void doTask(int taskId) {
        getTask(taskId).doTask();
    }

    @Override
    public List<Task> searchTasks(String query) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getName().contains(query) || task.getCreated().contains(query)) {
                result.add(task);
            }
        }
        return result;
    }
}
//완료