package dao;

import entity.User;
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
        user.setfName("alex");
        user.setlName("kurik");
        user.setEmail("123@cor.com");
        user.setStatus(1);
        user.setCreateDate(LocalDateTime.now());


        saveResult = dao.save(user);
    }


    @Test
    public void shouldSaveUser() throws Exception {

        Assert.assertTrue(saveResult);
    }

    @Test
    public void shouldReturnUserById() {
        //test will work if table users contains current ID
        User actual = dao.find(55);

        Assert.assertEquals(55, actual.getId());
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
