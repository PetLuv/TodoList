package ru.nehodov.todolist.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;

import ru.nehodov.todolist.R;
import ru.nehodov.todolist.models.Task;
import ru.nehodov.todolist.utils.DateTimeFormatter;

public class TaskEditorFragment extends Fragment {

    public static final int NEW_TASK_INDEX = -1;
    public static final String TRANSFERRED_TASK_KEY = "transferred_task_key";

    private TaskEditorListener listener;

    private int taskId;
    private Task task;
    private EditText taskTittleEdit;
    private EditText taskDescriptionEdit;
    //추가
    private EditText taskAlarmTimeEdit;
    private EditText taskPeriodEdit;
    private EditText taskEndAlarmEdit;
    //끝

    public TaskEditorFragment() {
    }

    public static Fragment getInstance(Bundle args) {
        Fragment fragment = new TaskEditorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_editor_fragment, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.task_editor_menu);
        toolbar.setTitle("Task editor");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this::onNavigationButtonClick);
        toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);


        taskId = getArguments().getInt(TaskListFragment.TASK_ID_KEY);

        taskTittleEdit = view.findViewById(R.id.task_title_edit);
        taskDescriptionEdit = view.findViewById(R.id.task_description_edit);
        //추가
        taskAlarmTimeEdit = view.findViewById(R.id.task_alarm_time_edit);
        taskPeriodEdit = view.findViewById(R.id.task_period_edit);
        taskEndAlarmEdit = view.findViewById(R.id.task_end_alarm_edit);
        //끝

        if (taskId > NEW_TASK_INDEX) {
            task = (Task) getArguments().getSerializable(TRANSFERRED_TASK_KEY);

            taskTittleEdit.setText(task.getName());
            taskDescriptionEdit.setText(task.getDesc());
            //추가
            taskAlarmTimeEdit.setText(task.getAlarmTime());
            taskPeriodEdit.setText(task.getPeriod());
            taskEndAlarmEdit.setText(task.getEndAlarm());
            //끝
            Log.d("ex", "여기다!");
        }
        return view;
    }

    //테스크 생성
    private void createTask() {

        String taskName = taskTittleEdit.getText().toString();
        taskName = taskName.equals("") ? "New Task" : taskName;
        String taskDescription = taskDescriptionEdit.getText().toString();
        //추가
        String taskAlarmTime = taskAlarmTimeEdit.getText().toString();
        String taskPeriod = taskPeriodEdit.getText().toString();
        String taskEndAlarm = taskEndAlarmEdit.getText().toString();
        //끝
        String created = DateTimeFormatter.format(new Date());

        //추가, 끝
        task = new Task(taskName, taskDescription, taskAlarmTime, taskPeriod, taskEndAlarm, created, "");
        listener.addNewTask(task);
    }

    private void editTask() {

        task.setName(taskTittleEdit.getText().toString());
        task.setDesc(taskDescriptionEdit.getText().toString());
        //추가
        task.setAlarmTime(taskAlarmTimeEdit.getText().toString());
        task.setPeriod(taskPeriodEdit.getText().toString());
        task.setEndAlarm(taskEndAlarmEdit.getText().toString());
        //끝
        listener.editTask(task, taskId);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.save_edit_menu) {
            if (taskId > NEW_TASK_INDEX) {
                editTask();
            } else {
                createTask();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNavigationButtonClick(View view) {
        getActivity().onBackPressed();
    }

    public interface TaskEditorListener {
        void addNewTask(Task task);

        void editTask(Task task, int index);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            this.listener = (TaskEditorListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    String.format(
                            "Class %s must implement %s interface",
                            context.toString(), TaskEditorListener.class.getSimpleName()
                    )
            );
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();
        this.listener = null;
    }

}
