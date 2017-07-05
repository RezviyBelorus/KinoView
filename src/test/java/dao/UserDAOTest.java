package dao;

import entity.User;
import exception.IllegalRequestException;
import org.junit.*;

import java.time.LocalDateTime;

/**
 * Created by alexfomin on 30.06.17.
 */
public class UserDAOTest {

    private static UserDAO dao;
    private static User user;
    private static boolean saveResult;

    @BeforeClass
    public static void setup() {
        dao = new UserDAO();
        user = new User();

        user.setLogin("tester");
        user.setPassword("testPass");
        user.setF_name("alex");
        user.setL_name("kurik");
        user.setEmail("123@cor.com");
        user.setStatus(1);
        user.setCreate_date(LocalDateTime.now());

        saveResult = dao.save(user);
    }


    @Test
    public void shouldSaveUser() throws Exception {

        Assert.assertTrue(saveResult);
    }

    @Test
    public void shouldReturnUserById() {
        //test will work if table users contains current ID
        User actual = dao.find(7);

        Assert.assertEquals(user.getId(), actual.getId());
    }

    @Test
    public void shouldReturnUserByEmail() {
        //test will work if table users contains current ID
        User actual = dao.find("123@cor.com");

        Assert.assertEquals("123@cor.com", actual.getEmail());
    }

    @AfterClass
    public static void shouldRemoveUser(){
        Boolean deleteResult = dao.delete(user.getEmail());

        Assert.assertTrue(deleteResult);
    }
}
