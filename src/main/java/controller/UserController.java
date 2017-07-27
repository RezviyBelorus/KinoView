package controller;

import entity.User;
import service.UserService;
import web.ModelAndView;
import web.View;
import web.http.HttpMethod;
import web.http.RequestMapping;
import web.response.UserDTO;

/**
 * Created by alexfomin on 04.07.17.
 */

//todo: create java docs
public class UserController implements Controller {

    private UserService userService;

    @RequestMapping(method = HttpMethod.POST, url = "users/signUp")
    public ModelAndView signUp(String login, String password, String fName, String lName, String email) {
        ModelAndView view = new ModelAndView(View.MAIN);
        UserDTO userDTO = userService.save(login, password, fName, lName, email);
        view.addParam("user", userDTO);
        return view;
    }

    //todo: View.User или View.Main или что-то еще
    @RequestMapping(method = HttpMethod.POST, url = "/users/login")
    public ModelAndView login(String emailOrLogin, String password) {
        ModelAndView view = new ModelAndView(View.USER);
        UserDTO userDTO = userService.login(emailOrLogin, password);
        view.addParam("user", userDTO);
        return view;

    }

    @RequestMapping(method = HttpMethod.GET, url = "users/findId")
    public ModelAndView find(int id) {
        ModelAndView view = new ModelAndView(View.USER);
        User user = userService.find(id);
        view.addParam("user", user);
        return view;
    }

    @RequestMapping(method = HttpMethod.GET, url = "users/findEmailOrLogin")
    public ModelAndView find(String emailOrLogin) {
        ModelAndView view = new ModelAndView(View.USER);
        User user = userService.find(emailOrLogin);
        view.addParam("user", user);
        return view;
    }

    @RequestMapping(method = HttpMethod.DELETE, url = "users/delete")
    public ModelAndView delete(String emailOrLogin) {
        ModelAndView view = new ModelAndView(View.USER);
        UserDTO userDTO = userService.delete(emailOrLogin);
        view.addParam("user", userDTO);
        return view;
    }

    @RequestMapping(method = HttpMethod.POST, url = "users/setStatus")
    public ModelAndView setStatus(String emailOrLogin, String status) {
        ModelAndView view = new ModelAndView(View.USER);
        UserDTO userDTO = userService.setStatus(emailOrLogin, status);
        view.addParam("user", userDTO);
        return view;
    }
}
