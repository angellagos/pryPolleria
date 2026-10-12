
import com.dwi.prytecnoservicio.Servicio;
import com.dwi.prytecnoservicio.ServicioDAO;
import com.dwi.prytecnoservicio.ServicioDAOImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(urlPatterns = {"/InicioServlet"})
public class InicioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ServicioDAO dao = new ServicioDAOImpl();
            List<Servicio> servicios = dao.listar();
            request.setAttribute("servicios", servicios);
        } catch (Exception e) {
            request.setAttribute("errorServicios", "No fue posible recuperar los servicios.");
        }
        request.getRequestDispatcher("/index.jsp")
                .forward(request, response);

        //CREA ATRIBUTOS
        request.setAttribute("titulo", "TecnoService");
        request.setAttribute("mensaje", "Página atendida inicialmente por InicioServlet.");
        request.setAttribute("origen", "Solicitud procesada por un Servlet.");
        //DERIVA ATRIBUTOS A COMPONENTE JSP
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
