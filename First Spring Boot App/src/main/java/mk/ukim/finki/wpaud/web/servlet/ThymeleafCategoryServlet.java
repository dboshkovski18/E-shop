package mk.ukim.finki.wpaud.web.servlet;

import mk.ukim.finki.wpaud.model.Category;
import mk.ukim.finki.wpaud.service.CategoryService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="thymeleaf-category-servlet",urlPatterns = "/servlet/thymeleaf/category")
public class ThymeleafCategoryServlet extends HttpServlet {

    private final CategoryService categoryService;
    private final SpringTemplateEngine springTemplateEngine;

    public ThymeleafCategoryServlet(CategoryService categoryService,SpringTemplateEngine springTemplateEngine){
        this.springTemplateEngine=springTemplateEngine;
        this.categoryService=categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       WebContext context = new WebContext(req,resp,req.getServletContext());
       context.setVariable("ipAddress",req.getRemoteAddr());
       context.setVariable("clientAgent",req.getHeader("User-Agent"));
       context.setVariable("categories",categoryService.listCategories());
       this.springTemplateEngine.process("categories.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryName=req.getParameter("name");
        String categoryDesc=req.getParameter("desc");
        categoryService.create(categoryName,categoryDesc);
        resp.sendRedirect("/servlet/thymeleaf/category");
    }
}
