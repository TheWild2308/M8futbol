package cat.iesthosicodina.futbol;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.sql.*;
@WebServlet(name = "TablaVotos", value = "/TablaVotos")
public class TablaVotos extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection con;
    private Statement set;
    private ResultSet rs;

    public void init(ServletConfig cfg) throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futbol","root","didac");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);

        //Leer el nombre del cliente de la sesión para darle las
        //gracias al final
        String nombreP = (String)s.getAttribute("nombreCliente");
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");

        out.println("<html><head><title>Fifa The Best</title></head>");
        out.println("<body><font size=10>");
        out.println("<h1>FIFA Best Player</h1>");
        out.println("<table border=1>");
        out.println("<tr><td><b>Jugador</b></td><td><b>Votos</b></td></tr>");

        try {
            PreparedStatement preparedStatement=con.prepareStatement("select player,vots from players");
            rs=preparedStatement.executeQuery();
            while (rs.next()){
                String player = rs.getString("player");
                String vots = rs.getString("vots");
                out.println("<tr>");
                out.println("<td>"+player+"</td>");
                out.println("<td>"+vots+"</td>");
                out.println("</tr>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        out.println("</table>");
        out.println("<h3>Muchas gracias " + nombreP + " por su visita</h3>");                out.println("<h3><a href='./index.html'>Volver a la pagina de votaci&oacute;n</a></h3>");
        out.println("</form></font></body></html>");
        out.close();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            doPost(req,res);
        } catch (Exception e) { }
    }

    public void destroy() {
        try {
            con.close(); //cerramos la conexión con la bbdd.
        } catch (Exception e) { }
        super.destroy();
    }
}

