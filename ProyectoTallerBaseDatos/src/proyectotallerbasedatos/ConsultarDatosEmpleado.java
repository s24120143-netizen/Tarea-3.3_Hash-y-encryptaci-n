
package proyectotallerbasedatos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ConsultarDatosEmpleado extends JFrame implements ActionListener {
    private JLabel lblTitulo;
    private JTable tblDatos;
    private JScrollPane scrollPane;
    private JButton btnSalir;

    public ConsultarDatosEmpleado() {
        super("Consulta de Datos Generales");
        this.setSize(750, 500); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
        
        // Inicialización de elementos
        lblTitulo = new JLabel("** DIRECTORIO DE USUARIOS **");
        lblTitulo.setFont(fuenteGigante);
        String[] columnas = {"Username", "Nombre", "Apellidos", "Correo", "Estatus", "Rol", "Edad", "Ciudad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        cargarDatosBasicos(modelo);
        tblDatos = new JTable(modelo);
        tblDatos.setFont(new Font("Arial", Font.PLAIN, 13));
        tblDatos.getTableHeader().setFont(fuenteGigante);
        tblDatos.setRowHeight(25);       
        scrollPane = new JScrollPane(tblDatos);
        scrollPane.setPreferredSize(new Dimension(680, 320));
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
    
    private void cargarDatosBasicos(DefaultTableModel modelo) {
        Conexion cc = new Conexion();
        String query = "SELECT u.username, u.first_name, u.last_name, u.email, u.user_status, r.nombre_rol, u.edad, u.ciudad " +
                       "FROM usuarios u INNER JOIN roles r ON u.id_rol = r.id_rol";
        
        try (Connection con = cc.conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (con != null) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("user_status"),
                        rs.getString("nombre_rol"),
                        rs.getInt("edad"),
                        rs.getString("ciudad")
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalir) {
            new AccionEmpleado(); 
            this.dispose(); 
        }
    }
}