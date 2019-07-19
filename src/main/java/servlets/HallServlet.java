package servlets;

import dto.HallDto;
import jdbc.Connector;
import services.ExhibitService;
import services.HallService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/hall")
public class HallServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HallService hallService;

    ExhibitService exhibitService;

    try {
      exhibitService = new ExhibitService(Connector.getConnection());
      hallService = new HallService(Connector.getConnection(), exhibitService);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      return;
    }

    String idParam = req.getParameter("id");

    if (idParam != null) {
      try {
        Integer id = Integer.valueOf(idParam);
        HallDto hallDto = hallService.findById(id);
        req.setAttribute("hall", hallDto);
        req.getRequestDispatcher("WEB-INF/static/hallInfo.jsp").forward(req, resp);
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
        List<HallDto> hallDtos = hallService.findAll();
        req.setAttribute("hall", hallDtos);
        req.getRequestDispatcher("WEB-INF/static/hall.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
