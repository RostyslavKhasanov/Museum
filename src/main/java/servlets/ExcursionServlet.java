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
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String startTime = req.getParameter("startTime");
    String endTime = req.getParameter("endTime");

    if (startTime != null && endTime != null) {
      try {
        List<ExcursionDto> excursions =
            excursionService.findByDate(
                LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
        int excursionsStatistic =
            excursionService.findCountByPeriod(
                LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
        req.setAttribute("excursions", excursions);
        req.setAttribute("excursionsStatistic", excursionsStatistic);
        req.getRequestDispatcher("WEB-INF/static/excursionInfo.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
