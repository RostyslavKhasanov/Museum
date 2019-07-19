package servlets;

import dtos.ExhibitDto;
import dtos.HallDto;
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

    String idHall = req.getParameter("id");

    if (idHall != null) {
      try {
        Integer id = Integer.valueOf(idHall);
        List<ExhibitDto> exhibits = exhibitService.findByHallId(id);
        req.setAttribute("exhibits", exhibits);
        req.getRequestDispatcher("WEB-INF/static/exhibitsByHall.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      try {
        List<HallDto> halls = hallService.findAll();
        req.setAttribute("hall", halls);
        req.getRequestDispatcher("WEB-INF/static/hall.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
