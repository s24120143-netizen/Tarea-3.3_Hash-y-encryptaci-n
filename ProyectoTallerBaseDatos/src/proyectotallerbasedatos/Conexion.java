package proyectotallerbasedatos;
import java.sql.*;
import javax.swing.*;

public class Conexion {
    private final String URL = "jdbc:mysql://localhost:3306/tienda";
    private final String USER = "root";
    private final String PASSWORD = "POLLOX20"; 
    
    public Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: ¡conector!\n" + e.getMessage());
            return null;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión en la BD: " + e.getMessage());
            return null;
        }
    }
}