package web;


import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppServletTest extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
        user.setLogin("alex");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/view/test2.jsp").forward(req,resp);
    }


}
