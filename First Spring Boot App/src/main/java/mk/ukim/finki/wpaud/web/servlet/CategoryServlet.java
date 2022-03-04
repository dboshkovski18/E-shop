package mk.ukim.finki.wpaud.web.servlet;

import mk.ukim.finki.wpaud.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="category-servlet",urlPatterns = "/servlet/category")
public class CategoryServlet extends HttpServlet {

    private final CategoryService categoryService;

    public CategoryServlet(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out=resp.getWriter();

        String ipAddress= req.getRemoteAddr();
        String clientAgent= req.getHeader("User-Agent");

        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>User info:</h1>");
         out.format("<p><h4>IP: %s </h4> <br/><h4>User-Admin: %s</h4></p>",ipAddress,clientAgent);
        out.println("<h3>Category List</h3>");
        out.println("<ul>");
        categoryService.listCategories().forEach(r->out.format("<li>%s (%s)</li>",r.getName(),r.getDescription()));
        out.println("</ul>");
        out.println("<h3>Add Category</h3>");
        out.println("<form method='POST' action='/servlet/category'>" +
                "<label for='name' >Name:</label>"+
                "<input type='text' name='name'/>"  +
                "<label for='desc' >Description:</label>"+
                "<input type='text' name='desc'/>"  +
                "<input type='submit' value='Submit'/>"  +
                "</form>");




        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       

        String categoryName=req.getParameter("name");
        String categoryDescription = req.getParameter("desc");
        categoryService.create(categoryName,categoryDescription);
        resp.sendRedirect("/servlet/category");

    }

}
