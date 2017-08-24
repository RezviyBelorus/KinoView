package service;

import entity.Film;
import exception.IllegalRequestException;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import util.LocalProperties;
import util.SiteConnector;
import util.Validator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskManager implements Runnable {
    private static final String THREAD_SEPARATOR = "taskmanager.thread.separator";
    private static String startUpdateHour = "taskmanager.startupdatehour";
    private static String startUpdateMinute = "taskmanager.startupdateminute";

    public static TaskManager instance;
    private static LocalProperties properties = new LocalProperties();

    private static final Logger logger = Logger.getLogger(TaskManager.class);
    private FilmService filmService;

    private boolean isAdminWantsToRunUpdate = false;
    private boolean isJobRun = true;

    private TaskManager(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public void run() {
        while (isJobRun) {
            LocalTime currentTime = LocalTime.now();

            LocalTime updateTime = LocalTime.parse(properties.get(startUpdateHour) + ":" + properties.get(startUpdateMinute));
            boolean isTimeToUpdate = isTimeToUpdate(currentTime, updateTime);
            if (isAdminWantsToRunUpdate || isTimeToUpdate) {

                List<Film> resultFilmsList = getFilms();

                List<Film> updatedFilms = filmService.updateBatchFilms(resultFilmsList);

                List<Film> savedFilms = filmService.saveBatch(resultFilmsList);

                isAdminWantsToRunUpdate = false;
                isJobRun = false;
            }
        }
    }

    public static TaskManager getInstance(FilmService filmService) {
        if (instance == null) {

            return new TaskManager(filmService);
        }
        return instance;
    }

    private boolean isTimeToUpdate(LocalTime currentTime, LocalTime updateTime) {
        return updateTime.getHour() == currentTime.getHour() && updateTime.getMinute() == currentTime.getMinute();
    }

    private List<Film> getFilms() {
        List<Future<List<Film>>> resultFilmsList = new ArrayList<>();
        int threadsQuantity = getThreadsQuantity(findLastPage());
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadsQuantity);

        int begin;
        int end = 0;
        for (int i = 0; i < threadsQuantity; i++) {
            begin = end + 1;
            end = end + Validator.validateInt(properties.get(THREAD_SEPARATOR));
            Future<List<Film>> submit = executor.submit(new Task(begin, end + 1));
            resultFilmsList.add(submit);
        }

        List<Film> films = new ArrayList<>();

        for (Future<List<Film>> listFuture : resultFilmsList) {
            try {
                films.addAll(listFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Task manager error");
                throw new IllegalRequestException("");
            }
        }
        return films;
    }

    private int findLastPage() {
        SiteConnector siteConnector;

        int begin;
        int end = 1;

        while (true) {
            siteConnector = new SiteConnector(end);
            Document page = siteConnector.getPage();
            if (page == null) {
                begin = end / 2;
                for (int i = begin; i < end; i++) {
                    siteConnector = new SiteConnector(i);
                    Document currentPage = siteConnector.getPage();
                    if (currentPage == null) {
                        return i - 1;
                    }
                }
            }
            end *= 2;
        }
    }

    private int getThreadsQuantity(int lastPage) {
        int threadsNumber = lastPage / Validator.validateInt(properties.get(THREAD_SEPARATOR));
        return threadsNumber == 0 ? 1 : threadsNumber;
    }

    public boolean isAdminWantsToRunUpdate() {
        return isAdminWantsToRunUpdate;
    }

    public void setAdminWantsToRunUpdate(boolean adminWantsToRunUpdate) {
        isAdminWantsToRunUpdate = adminWantsToRunUpdate;
    }

    public String getTHREAD_SEPARATOR() {
        return THREAD_SEPARATOR;
    }

    public static String getStartUpdateHour() {
        return startUpdateHour;
    }

    public static void setStartUpdateHour(String startUpdateHour) {
        TaskManager.startUpdateHour = startUpdateHour;
    }

    public static String getStartUpdateMinute() {
        return startUpdateMinute;
    }

    public static void setStartUpdateMinute(String startUpdateMinute) {
        TaskManager.startUpdateMinute = startUpdateMinute;
    }

    public boolean isJobRun() {
        return isJobRun;
    }

    public void setJobRun(boolean jobRun) {
        isJobRun = jobRun;
    }
}


