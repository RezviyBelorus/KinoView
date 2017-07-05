import dao.FilmDAO;
import entity.Film;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Created by alexfomin on 02.07.17.
 */
public class TestMain {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(TestMain.class);
        FilmDAO filmDao = new FilmDAO();
        Film film = new Film();

        film.setFilm_name("test3");
        film.setRelease_year(2017);
        film.setQuality_id(1);
        film.setTranslation_id(1);
        film.setLength("01:24:13");
        film.setRating(5);
        film.setUpload_date(LocalDateTime.now());
        film.setStatus(3);

        logger.error("хочу печеньку");
        logger.info("test");
        filmDao.save(film);

        System.out.println(new java.sql.Date(System.currentTimeMillis()));

    }
}
