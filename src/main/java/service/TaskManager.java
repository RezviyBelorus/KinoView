package service;


import entity.Film;
import exception.IllegalRequestException;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import util.SiteConnector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskManager implements Runnable {
    private final int THREAD_SEPARATOR = 200;
    Logger logger = Logger.getLogger(TaskManager.class);
    FilmService filmService;

    @Override
    public void run() {
        List<Film> resultFilmsList = getFilms();
        List<Film> filmsInDatabase = filmService.findLoadedFilms();
        List<Film> films = filmsToLoad(resultFilmsList, filmsInDatabase);

        filmService.saveBatch(films);
    }

    private List<Film> filmsToLoad(List<Film> resultFilmsList, List<Film> filmsInDatabase) {

        Iterator<Film> iterator = resultFilmsList.iterator();

        while (iterator.hasNext()) {
            Film potentialToLoadFilm = iterator.next();
            for (Film databaseFilm : filmsInDatabase) {
                if (potentialToLoadFilm.getName() == databaseFilm.getName()
                        && potentialToLoadFilm.getKinogoPage() >= databaseFilm.getKinogoPage()) {
                    iterator.remove();
                }
            }
        }
        return resultFilmsList;
    }

    private List<Film> getFilms() {
        List<Future<List<Film>>> resultFilmsList = new ArrayList<>();
        int threadsQuantity = getThreadsQuantity(findLastPage());
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadsQuantity);

        int begin;
        int end = 0;
        for (int i = 0; i < executor.getTaskCount(); i++) {
            begin = end + 1;
            end = end + THREAD_SEPARATOR;
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
        int threadsNumber = lastPage % THREAD_SEPARATOR;

        return threadsNumber == lastPage ? 1 : lastPage / THREAD_SEPARATOR;
    }
}
