package servlets;

import dtos.ExhibitDto;
import jdbc.Connector;
import services.ExhibitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/exhibit")
public class ExhibitServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    ExhibitService exhibitService;

    try {
      exhibitService = new ExhibitService(Connector.getConnection());
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      return;
    }

    String idParam = req.getParameter("id");

    if (idParam != null) {
      try {
        Integer id = Integer.valueOf(idParam);
        ExhibitDto exhibitDto = exhibitService.findById(id);
        req.setAttribute("exhibit", exhibitDto);
        req.getRequestDispatcher("WEB-INF/static/exhibitInformation.jsp").forward(req, resp);
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
        List<ExhibitDto> exhibitDtoList = exhibitService.findAll();
        req.setAttribute("exhibits", exhibitDtoList);
        req.getRequestDispatcher("WEB-INF/static/exhibit.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
