package servlets;

import dto.ExcursionDto;
import jdbc.Connector;
import services.ExcursionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/excursion")
public class ExcursionServlet extends HttpServlet {
  ExcursionService excursionService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      excursionService = new ExcursionService(Connector.getConnection());
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      return;
    }

    req.getRequestDispatcher("WEB-INF/static/excursion.jsp").forward(req, resp);

    String idParam = req.getParameter("id");

    if (idParam != null) {
      try {
        Integer id = Integer.valueOf(idParam);
        ExcursionDto excursionDto = excursionService.findById(id);
        req.setAttribute("exhibit", excursionDto);
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
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String start = req.getParameter("startDate");
    String end = req.getParameter("endDate");

    if (start != null || end != null){
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);
        List<ExcursionDto> excursions = excursionService.findByDate(startDate, endDate);
        req.setAttribute("excursion", excursions);
        req.getRequestDispatcher("WEB-INF/static/excursionInfo.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
