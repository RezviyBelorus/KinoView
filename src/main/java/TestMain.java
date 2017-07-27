import com.sun.org.apache.regexp.internal.RE;
import controller.Controller;
import controller.FilmController;
import dao.FilmDAO;
import entity.Film;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import web.ModelAndView;
import web.http.Dispatcher;
import web.http.RequestMapping;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by alexfomin on 02.07.17.
 */
public class TestMain {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(TestMain.class);
//        FilmDAO filmDao = new FilmDAO();
//        Film film = new Film();
//
//        film.setName("test3");
//        film.setReleaseYear(2017);
//        film.setQuality_id(1);
//        film.setTranslationId(1);
//        film.setLength("01:24:13");
//        film.setRating(5);
//        film.setUploadDate(LocalDateTime.now());
//        film.setStatus(3);

        logger.error("хочу печеньку");
        logger.info("test");
//        filmDao.save(film);

    }
}
