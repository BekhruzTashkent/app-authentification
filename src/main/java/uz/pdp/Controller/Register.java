package uz.pdp.Controller;

import uz.pdp.model.Result;
import uz.pdp.model.User;
import uz.pdp.service.DbService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phoneNumber");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String prePassword = req.getParameter("prePassword");
        PrintWriter printWriter = resp.getWriter();
        if (password.equals(prePassword)) {
            DbService dbService = new DbService();
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setUsername(username);
            user.setPassword(password);
            Result result = dbService.registerUser(user);


            if (result.isSuccess()) {

                printWriter.write("<h1 color = 'green'>" + result.getMessage() + "</h1>");
            } else {
                printWriter.write("<h1 color = 'red'>" + result.getMessage() + "</h1>");
            }

        } else {
            printWriter.write("<h1> Incorrect pre-password </h1>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   resp.sendRedirect("register.html");
    }
}
