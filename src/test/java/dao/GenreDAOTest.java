package dao;

import entity.Genre;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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

        genre.setGenre_name("comedy");
    }

    @Test
    public void save() throws Exception {
        saveResult = dao.save(genre);

        Assert.assertTrue(saveResult);
    }

    @Test
    public void findID() throws Exception {
        Genre actual = dao.find(genre.getGenre_id());

        Assert.assertEquals(genre.getGenre_id(), actual.getGenre_id());
    }

    @Test
    public void findGenreName() throws Exception {
        Genre actual = dao.find(genre.getGenre_name());

        Assert.assertEquals(genre.getGenre_name(), actual.getGenre_name());
    }

    @AfterClass
    public static void deleteGenreName(){
        boolean deleteResult = dao.delete(genre.getGenre_name());

        Assert.assertTrue(deleteResult);
    }
}