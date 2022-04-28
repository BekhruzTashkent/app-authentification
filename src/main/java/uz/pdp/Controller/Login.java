package uz.pdp.Controller;

import uz.pdp.model.User;
import uz.pdp.service.DbService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
String username = req.getParameter("username");
String password = req.getParameter("password");
        DbService dbService = new DbService();
        User user = dbService.login(username,password);
        PrintWriter printWriter = resp.getWriter();
        if(user == null){
            printWriter.write("<h1>Error</h1>");
        }
        else {
            printWriter.write("<h1>Welcome to system dear "+user.getFirstName()+" "+user.getLastName()+"</h1>");
            printWriter.write("<h1>Your phone number:  "+user.getPhoneNumber()+"\tYour username: "
                    +user.getUsername()+"</h1>");

            Cookie cookie = new Cookie("authApp",user.getUsername());
            cookie.setMaxAge(60);
            resp.addCookie(cookie);
            resp.sendRedirect("/cabinet");
      }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }
}
