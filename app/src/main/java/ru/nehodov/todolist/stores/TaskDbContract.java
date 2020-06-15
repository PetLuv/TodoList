package ru.nehodov.todolist.stores;

public final class TaskDbContract {

    private TaskDbContract() {

    }

    //DB 인덱스 필드 이름
    public static class TasksTable {


        public static final String TABLE_NAME = "tasksTable";

        public static final String COLUMN_NAME_ID = "_ID";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESC = "desc";

        //추가
        public static final String COLUMN_NAME_ALARM_TIME = "alarm_time";
        public static final String COLUMN_NAME_PERIOD = "period";
        public static final String COLUMN_NAME_END_ALARM = "end_alarm";
       //끝

        public static final String COLUMN_NAME_CREATED = "created";
        public static final String COLUMN_NAME_DONE = "done";

    }

}
//완료