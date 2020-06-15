package ru.nehodov.todolist.models;

import java.io.Serializable;
import java.util.Date;

import ru.nehodov.todolist.utils.DateTimeFormatter;

public class Task implements Serializable {

    private int id;
    private String name;
    private String desc;
    private String created;
    private String done;
    //추가
    private String alarm_time;
    private String period;
    private String end_alarm;
   //끝

    //추가, 끝
    public Task(String name, String desc, String alarm_time, String period, String end_alarm, String created, String done) {
        this.name = name;
        this.desc = desc;
        //추가
        this.alarm_time = alarm_time;
        this.period = period;
        this.end_alarm = end_alarm;
        //끝
        this.created = created;
        this.done = done;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //추가, 끝
    public Task(String name, String desc, String alarm_time, String period, String end_alarm, String created) {
        this(name, desc, alarm_time, period, end_alarm, created, "");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    //추가

    public void setAlarmTime(String alarm_time) {
        this.alarm_time = alarm_time;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setEndAlarm(String end_alarm) {
        this.end_alarm = end_alarm;
    }

    //끝

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    //추가

    public String getAlarmTime() {
        return alarm_time;
    }

    public String getPeriod() {
        return period;
    }

    public String getEndAlarm() {
        return end_alarm;
    }

    //끝

    public String getCreated() {
        return created;
    }

    public String getDoneDate() {
        return done;
    }

    public void doTask() {
        this.done = DateTimeFormatter.format(new Date());
    }


}
//완료