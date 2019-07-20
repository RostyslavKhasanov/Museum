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

    String start = req.getParameter("dateStart");
    String end = req.getParameter("dateEnd");

    if (start != null && end != null) {
      try {
        List<ExcursionDto> excursions = excursionService.findByDate(start, end);
        req.setAttribute("excursion", excursions);
        req.getRequestDispatcher("WEB-INF/static/excursionInfo.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      req.getRequestDispatcher("WEB-INF/static/excursion.jsp").forward(req, resp);
    }

    //    String idParam = req.getParameter("id");
    //
    //    if (idParam != null) {
    //      try {
    //        Integer id = Integer.valueOf(idParam);
    //        ExcursionDto excursionDto = excursionService.findById(id);
    //        req.setAttribute("exhibit", excursionDto);
    //        req.getRequestDispatcher("WEB-INF/static/exhibitInformation.jsp").forward(req, resp);
    //      } catch (NumberFormatException e) {
    //        System.out.println(
    //            "You have to input number that low than: "
    //                + Integer.MAX_VALUE
    //                + "or great than "
    //                + Integer.MIN_VALUE);
    //      } catch (SQLException e) {
    //        e.printStackTrace();
    //      }
  }
}
