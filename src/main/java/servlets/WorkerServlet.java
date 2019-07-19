package servlets;

import dto.ExhibitDto;
import dto.WorkerDto;
import jdbc.Connector;
import services.ExcursionService;
import services.HallService;
import services.WorkerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/worker")
public class WorkerServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    WorkerService workerService;

    HallService hallService;
    ExcursionService excursionService;

    try {
      hallService = new HallService(Connector.getConnection());
      excursionService = new ExcursionService(Connector.getConnection());
      workerService = new WorkerService(Connector.getConnection(), excursionService, hallService);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      return;
    }

    String nameParam = req.getParameter("name");
    String idParam = req.getParameter("id");
    String posParam = req.getParameter("pos");

    if (nameParam != null) {
      try {
        List<ExhibitDto> exhibitDtoList = workerService.findExhibitByWorkerName(nameParam);
        req.setAttribute("exhibits", exhibitDtoList);
        req.getRequestDispatcher("WEB-INF/static/worker.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (idParam != null) {
      try {
        Integer id = Integer.valueOf(idParam);
        WorkerDto workerDto = workerService.findById(id);
        req.setAttribute("worker", workerDto);
        req.getRequestDispatcher("WEB-INF/static/workerInfo.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (posParam != null) {
      try {
        Integer pos = Integer.valueOf(posParam);
        List<WorkerDto> workerDtoList = workerService.findByWorkerPosition(pos);
        req.setAttribute("workers", workerDtoList);
        req.getRequestDispatcher("WEB-INF/static/gid.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      try {
        List<WorkerDto> workerDtoList = workerService.findAll();
        req.setAttribute("workers", workerDtoList);
        req.getRequestDispatcher("WEB-INF/static/worker.jsp").forward(req, resp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
