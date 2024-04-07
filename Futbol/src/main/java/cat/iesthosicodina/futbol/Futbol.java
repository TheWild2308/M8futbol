package cat.iesthosicodina.futbol;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
@WebServlet(name = "futbol", value = "/futbol")
public class Futbol extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection con;
    private PreparedStatement set;
    private ResultSet rs;
    private String cad;

    public void init(ServletConfig cfg) throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futbol","root","didac");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Obtener la sesion
        HttpSession s = req.getSession(true);
        // Guardar el nombre del cliente en la sesión
        // para poderlo utilizar en el siguiente servlet TablaVotos
        String nombreP=(String)req.getParameter("txtNombre");
        String emailP=(String)req.getParameter("txtMail");
        s.setAttribute("nombreCliente", nombreP);
        String nombre=(String)req.getParameter("R1");
        if (nombre.equals("Otro"))
            nombre=(String)req.getParameter("txtOtros");
        boolean existe = false;
        System.out.println( "Un voto más para: "+nombre);
        int playerId=0;
        try {
            PreparedStatement selectPlayer = con.prepareStatement("select * from players where player=?");
            selectPlayer.setString(1, nombre);
            ResultSet resultSet = selectPlayer.executeQuery();
            if (!resultSet.next()) {
                System.out.println("El nombre no existe");
                return;
            }
            playerId=resultSet.getInt("id");
        } catch(Exception e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement insertVote = con.prepareStatement("insert into vots_player (id,votant,email,jugador) values(null,?,?,?)");
            insertVote.setString(1,nombreP);
            insertVote.setString(2,emailP);
            insertVote.setInt(3,playerId);

            PreparedStatement updatePlayer= con.prepareStatement("update players set vots=vots+1 where id=?");
            updatePlayer.setInt(1,playerId);
            insertVote.executeUpdate();
            updatePlayer.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Llamada al servlet TablaVotos que nos visualiza las estadísticas de jugadores
        res.sendRedirect(res.encodeRedirectURL("./TablaVotos"));
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try{
            doPost(req,res);
        } catch (Exception e) {}
    }

    public void destroy() {
        try {
            con.close();
        } catch (Exception e) {}
        super.destroy();
    }
}