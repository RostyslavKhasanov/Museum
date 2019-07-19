package servlets;

import dto.AuthorDto;
import jdbc.Connector;
import services.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/author")
public class AuthorServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    AuthorService authorService;

    try {
      authorService = new AuthorService(Connector.getConnection());
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      return;
    }

    String idParam = req.getParameter("id");

    if (idParam != null) {
      try {
        Integer id = Integer.valueOf(idParam);
        AuthorDto authorDto = authorService.findById(id);
        req.setAttribute("author", authorDto);
        req.getRequestDispatcher("WEB-INF/static/authorInformation.jsp").forward(req, resp);
      } catch (NumberFormatException e) {
        System.out.println(
            "You have to input number that low than: "
                + Integer.MAX_VALUE
                + "or great than "
                + Integer.MIN_VALUE);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {

      try {
        List<AuthorDto> authors = authorService.findAll();
        req.setAttribute("authors", authors);
        req.getRequestDispatcher("WEB-INF/static/author.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

  }
}
