/*
package web.http;

import controller.Controller;
import controller.UserController;
import entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import service.UserService;
import web.ModelAndView;
import web.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)

public class DispatcherTest {
//    static Dispatcher dispatcher;

    @Mock
    UserController userController;

    @Mock
    List<Controller> controllers;

    @Mock
    UserService userService;

    @InjectMocks
    Dispatcher dispatcher;

    @Test
    public void ShouldgetInstance() throws Exception {
        dispatcher = Dispatcher.getInstance();

        Assert.assertNotNull(dispatcher);
    }

    @Test
    public void dispatch() throws Exception {
        User user = new User();
        String[] emailOrLogin = {"Vasya"};
       ModelAndView modelAndView = new ModelAndView(View.USER);

        Map<String, String[]> parametersMap = new HashMap<>();
        parametersMap.put("emailOrLogin", emailOrLogin);


//        Mockito.when(userService.find("Vasya")).thenReturn(user);
        Mockito.when(userController.find("Vasya")).thenReturn(modelAndView);



        ModelAndView actual = dispatcher.dispatch("users/findEmailOrLogin", "GET", parametersMap);

        Assert.assertEquals(View.USER, actual.getView().getName());
    }

    @Test
    public void getTargetForInvoke() {

    }

}
*/
