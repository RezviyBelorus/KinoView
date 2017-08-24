package service;

import java.time.LocalTime;

public class AdminService {
    TaskManager taskManager = TaskManager.getInstance(new FilmService());


    public void startUpdate() {
        taskManager.setAdminWantsToRunUpdate(true);
    }

    public LocalTime setUpdateTimeHour(String hour) {
        TaskManager.setStartUpdateHour(hour);
        String startUpdateHour = TaskManager.getStartUpdateHour();
        String startUpdateMinute = TaskManager.getStartUpdateMinute();
        return LocalTime.parse(startUpdateHour + ":" + startUpdateMinute);
    }
}
