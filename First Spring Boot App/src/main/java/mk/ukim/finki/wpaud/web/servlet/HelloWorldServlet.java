package mk.ukim.finki.wpaud.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="", urlPatterns = "/hello")
public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer= resp.getWriter();

        String user= req.getParameter("user");
        String age=req.getParameter("age");

        writer.format("<html><head></head><body><h4>Hello %s! you are %s years old!</h4></body></html>",user,age);
        writer.flush();

    }
}
