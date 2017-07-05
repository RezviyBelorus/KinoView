package controller;

import dao.UserDAO;
import entity.User;
import service.UserService;
import web.response.UserDTO;

/**
 * Created by alexfomin on 04.07.17.
 */

//todo: сделать нормальный return на все методы
public class UserController {

    private UserService userService;

    public boolean signUp(String login, String password, String f_name, String l_name, String email){
        userService.saveUser(login,password,f_name,l_name,email);
        return true;
    }

    public boolean login(String email, String password) {
        UserDTO userDTO = userService.login(email, password);
        return true;

    }

    public boolean findUserById(int id) {
        User user = userService.findUserById(id);
        return true;
    }

    public boolean findUserByLoginOrEmail(String emailOrLogin) {
        User user = userService.findUserByLoginOrEmail(emailOrLogin);
        return true;
    }

    public boolean deleteUser(String emailOrLogin){
        return userService.deleteUser(emailOrLogin);
    }

    public boolean setStatus(String emailOrLogin, int status) {
        userService.setStatus(emailOrLogin, status);
        return true;
    }

}
