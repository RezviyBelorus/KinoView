package dao;

import entity.Film;
import org.junit.*;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by alexfomin on 04.07.17.
 */
public class FilmDAOTest {

    private static FilmDAO dao;
    private static Film film;
    private static boolean saveResult;

    @BeforeClass
    public static void setUp() throws Exception {
        dao = new FilmDAO();
        film = new Film();

        film.setFilm_name("test3");
        film.setRelease_year(2017);
        film.setQuality_id(1);
        film.setTranslation_id(1);
        film.setLength("01:24:13");
        film.setRating(5);
        film.setUpload_date(LocalDateTime.now());
        film.setStatus(3);

        saveResult = dao.save(film);
    }

    @AfterClass
    public static void tearDown() throws Exception {
//        delete tested film
//        Boolean deleteResult = dao.delete("test3");
//        Assert.assertTrue(deleteResult);
    }

    @Test
    public void save() throws Exception {
        Assert.assertTrue(saveResult);
    }

    @Test
    public void findID() throws Exception {
        Film actual = dao.find(film.getFilm_id());

        Assert.assertEquals(film.getFilm_id(), actual.getFilm_id());
    }

    @Test
    public void findFilmName() throws Exception {
        Film actual = dao.find(film.getFilm_name());
        Assert.assertEquals(film.getFilm_name(), actual.getFilm_name());
    }

}