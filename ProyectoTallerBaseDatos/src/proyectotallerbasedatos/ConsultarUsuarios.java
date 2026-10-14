//package proyectotallerbasedatos;
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//
//public class ConsultarUsuarios extends JFrame implements ActionListener {
//    private JLabel lblTitulo;
//    private JTable tblUsuarios;
//    private JScrollPane scrollPane;
//    private JButton btnSalir;
//
//    public ConsultarUsuarios() {
//        super("Consultar Usuarios");
//        this.setSize(850, 500); // Ancho mayor para acomodar todas las columnas de la tabla de usuarios
//        this.setLocationRelativeTo(null); // Centrado en pantalla
//        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        
//        JPanel panelPrincipal = new JPanel();
//        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
//        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
//        
//        // Inicialización de elementos
//        lblTitulo = new JLabel("** CONSULTAR USUARIOS **");
//        lblTitulo.setFont(fuenteGigante);
//        
//        // Definición de columnas según la tabla 'usuarios' y 'roles'
//        String[] columnas = {"ID", "Username", "Nombre", "Apellidos", "Correo", "Estatus", "Rol", "Fecha Creación", "Edad", "Ciudad"};
//        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
//        
//        // Cargar datos desde la base de datos 'tienda'
//        cargarDatosUsuarios(modelo);
//        
//        tblUsuarios = new JTable(modelo);
//        tblUsuarios.setFont(new Font("Arial", Font.PLAIN, 13));
//        tblUsuarios.getTableHeader().setFont(fuenteGigante);
//        tblUsuarios.setRowHeight(25);
//        
//        scrollPane = new JScrollPane(tblUsuarios);
//        scrollPane.setPreferredSize(new Dimension(780, 320));
//
//        btnSalir = new JButton("SALIR");
//        btnSalir.setFont(fuenteGigante);
//        btnSalir.addActionListener(this);
//        
//        // Panel para los botones inferiores
//        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
//        Dimension tamanhoBotones = new Dimension(140, 35);
//        btnSalir.setPreferredSize(tamanhoBotones);
//        pnlBotones.add(btnSalir);
//
//        // Agregar elementos al panel principal vertical
//        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
//        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
//        panelPrincipal.add(lblTitulo);
//        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
//        
//        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
//        panelPrincipal.add(scrollPane);
//        
//        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
//        panelPrincipal.add(pnlBotones);
//
//        this.add(panelPrincipal);
//        this.setVisible(true);    
//    }
//    
//    // Método para consultar y llenar la tabla con los usuarios de MySQL
//    private void cargarDatosUsuarios(DefaultTableModel modelo) {
//        try {
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", "");
//            // Consulta combinando usuarios y roles para mostrar el nombre del rol en lugar de su ID numérico
//            String query = "SELECT u.id_usuario, u.username, u.first_name, u.last_name, u.email, u.user_status, r.nombre_rol, u.fecha_creacion, u.edad, u.ciudad " +
//                           "FROM usuarios u INNER JOIN roles r ON u.id_rol = r.id_rol";
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            
//            while (rs.next()) {
//                Object[] fila = {
//                    rs.getInt("id_usuario"),
//                    rs.getString("username"),
//                    rs.getString("first_name"),
//                    rs.getString("last_name"),
//                    rs.getString("email"),
//                    rs.getString("user_status"),
//                    rs.getString("nombre_rol"),
//                    rs.getDate("fecha_creacion"),
//                    rs.getInt("edad"),
//                    rs.getString("ciudad")
//                };
//                modelo.addRow(fila);
//            }
//            rs.close();
//            stmt.close();
//            con.close();
//        } catch (SQLException e) {
//            // Datos de respaldo por si no hay conexión a la BD en ese momento
//            modelo.addRow(new Object[]{1, "EjemploUser", "NombreEj", "ApellidoEj", "correo@tienda.com", "Active", "Administrador", "2026-03-01", 30, "Guanajuato"});
//        }
//    }
//    
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == btnSalir) {
//            new Accion();
//            this.dispose(); 
//        }
//    }
//}
package proyectotallerbasedatos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ConsultarUsuarios extends JFrame implements ActionListener {
    private JLabel lblTitulo;
    private JTable tblUsuarios;
    private JScrollPane scrollPane;
    private JButton btnSalir;

    public ConsultarUsuarios() {
        super("Consultar Usuarios");
        this.setSize(850, 500); // Ancho mayor para acomodar todas las columnas de la tabla de usuarios
        this.setLocationRelativeTo(null); // Centrado en pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
        
        // Inicialización de elementos
        lblTitulo = new JLabel("** CONSULTAR USUARIOS **");
        lblTitulo.setFont(fuenteGigante);
        //cargar tabla
        String[] columnas = {"ID", "Username", "Nombre", "Apellidos", "Correo", "Estatus", "Rol", "Fecha Creación", "Edad", "Ciudad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        cargarDatosUsuarios(modelo);
        
        tblUsuarios = new JTable(modelo);
        tblUsuarios.setFont(new Font("Arial", Font.PLAIN, 13));
        tblUsuarios.getTableHeader().setFont(fuenteGigante);
        tblUsuarios.setRowHeight(25);
        
        scrollPane = new JScrollPane(tblUsuarios);
        scrollPane.setPreferredSize(new Dimension(780, 320));

        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuenteGigante);
        btnSalir.addActionListener(this);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Dimension tamanhoBotones = new Dimension(140, 35);
        btnSalir.setPreferredSize(tamanhoBotones);
        pnlBotones.add(btnSalir);


        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));      
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(scrollPane);     
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(pnlBotones);

        this.add(panelPrincipal);
        this.setVisible(true);    
    }
    

    private void cargarDatosUsuarios(DefaultTableModel modelo) {
        Conexion cc = new Conexion();
        String query = "SELECT u.id_usuario, u.username, u.first_name, u.last_name, u.email, u.user_status, r.nombre_rol, u.fecha_creacion, u.edad, u.ciudad " +
                       "FROM usuarios u INNER JOIN roles r ON u.id_rol = r.id_rol";
        
        try (Connection con = cc.conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (con != null) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id_usuario"),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("user_status"),
                        rs.getString("nombre_rol"),
                        rs.getDate("fecha_creacion"),
                        rs.getInt("edad"),
                        rs.getString("ciudad")
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los usuarios: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalir) {
            new Accion(1); 
            this.dispose(); 
        }
    }
}