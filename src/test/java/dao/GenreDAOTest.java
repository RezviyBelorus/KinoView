package dao;

import entity.Genre;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by alexfomin on 04.07.17.
 */
public class GenreDAOTest {
    private static GenreDAO dao;
    private static Genre genre;
    private static boolean saveResult;

    @BeforeClass
    public static void setUp() {
        dao = new GenreDAO();
        genre = new Genre();

        genre.setGenreName("comedy");
    }

    @Test
    public void save() throws Exception {
        saveResult = dao.save(genre);

        Assert.assertTrue(saveResult);
    }

    @Test
    public void findID() throws Exception {
        Genre actual = dao.find(genre.getGenreId());

        Assert.assertEquals(genre.getGenreId(), actual.getGenreId());
    }

    @Test
    public void findGenreName() throws Exception {
        Genre actual = dao.find(genre.getGenreName());

        Assert.assertEquals(genre.getGenreName(), actual.getGenreName());
    }

    @AfterClass
    public static void deleteGenreName(){
        boolean deleteResult = dao.delete(genre.getGenreName());

        Assert.assertTrue(deleteResult);
    }
}