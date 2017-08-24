package dao;

import entity.Film;
import org.junit.*;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

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

        film.setName("filmName");
        film.setReleaseYear(2017);
        film.setQuality("HDRip");
        film.setTranslation("Дублированный");
        film.setDuration("01:24:13");
        film.setRating(5);
        film.setUploadDate(LocalDateTime.now());
        film.setStatus(3);
        film.setWatchLink("watchLink");
        film.setImgLink("imgLink");
        film.setShortStory("story");

        saveResult = dao.save(film);
    }


    @Test
    public void save() throws Exception {
        Assert.assertTrue(saveResult);
    }

    @Test
    public void findID() throws Exception {
        Film actual = dao.find(film.getId());

        Assert.assertEquals(film.getId(), actual.getId());
    }

    @Test
    public void findFilmName() throws Exception {
        Film actual = dao.find(film.getName());
        Assert.assertEquals(film.getName(), actual.getName());
    }


    @AfterClass
    public static void tearDown() throws Exception {
//        shouldDeleteFilm tested film
        Boolean deleteResult = dao.delete("test3");
        Assert.assertTrue(deleteResult);
    }
}