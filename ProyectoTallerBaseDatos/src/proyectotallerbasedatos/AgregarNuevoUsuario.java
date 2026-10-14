
//package proyectotallerbasedatos;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//
//public class AgregarNuevoUsuario extends JFrame implements ActionListener {
//    private JLabel lblAgregar, lblUsername, lblPassword, lblNombres, lblApellidos, lblCorreo, lblEstatus, lblRol;
//    private JButton btnAgregar, btnSalir;
//    private JComboBox<String> cmbRol;
//    private JTextField txtUsername, txtNombres, txtApellidos, txtCorreo, txtEstatus;
//    private JPasswordField txtPassword;
//
//    public AgregarNuevoUsuario() {
//        super("Agregar Nuevo Usuario");
//        this.setSize(550, 620); // Ajustado para dar espacio a los nuevos campos obligatorios
//        this.setLocationRelativeTo(null); // Centrado en pantalla
//        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        
//        JPanel panelPrincipal = new JPanel();
//        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
//        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
//        
//        // Inicialización de elementos
//        lblAgregar = new JLabel("** AGREGAR NUEVO USUARIO **");
//        lblAgregar.setFont(fuenteGigante);
//
//        lblUsername = new JLabel("Username:");
//        lblUsername.setFont(fuenteGigante);
//        txtUsername = new JTextField(15);
//        txtUsername.setFont(fuenteGigante);
//
//        lblPassword = new JLabel("Contraseña:");
//        lblPassword.setFont(fuenteGigante);
//        txtPassword = new JPasswordField(15);
//        txtPassword.setFont(fuenteGigante);
//        
//        lblNombres = new JLabel("Nombres:");
//        lblNombres.setFont(fuenteGigante);
//        txtNombres = new JTextField(15);
//        txtNombres.setFont(fuenteGigante);
//
//        lblApellidos = new JLabel("Apellidos:");
//        lblApellidos.setFont(fuenteGigante);
//        txtApellidos = new JTextField(15);
//        txtApellidos.setFont(fuenteGigante);
//
//        lblCorreo = new JLabel("Correo:");
//        lblCorreo.setFont(fuenteGigante);
//        txtCorreo = new JTextField(15);
//        txtCorreo.setFont(fuenteGigante);
//
//        lblEstatus = new JLabel("Estatus:");
//        lblEstatus.setFont(fuenteGigante);
//        txtEstatus = new JTextField(15);
//        txtEstatus.setText("Active"); // Valor por defecto común
//        txtEstatus.setFont(fuenteGigante);
//
//        lblRol = new JLabel("Rol:");
//        lblRol.setFont(fuenteGigante);
//        cmbRol = new JComboBox<>(new String[]{"Admin", "Empleado"});
//        cmbRol.setFont(fuenteGigante);
//
//        btnAgregar = new JButton("AGREGAR");
//        btnAgregar.setFont(fuenteGigante);
//        btnSalir = new JButton("SALIR");
//        btnSalir.setFont(fuenteGigante);
//        
//        // Dar acciones a los botones 
//        btnSalir.addActionListener(this);
//        btnAgregar.addActionListener(this);
//
//        // Panel para el formulario (7 filas, 2 columnas para acomodar todos los campos)
//        JPanel pnlFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
//        pnlFormulario.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
//        
//        pnlFormulario.add(lblUsername);
//        pnlFormulario.add(txtUsername);
//        pnlFormulario.add(lblPassword);
//        pnlFormulario.add(txtPassword);
//        pnlFormulario.add(lblNombres);
//        pnlFormulario.add(txtNombres);
//        pnlFormulario.add(lblApellidos);
//        pnlFormulario.add(txtApellidos);
//        pnlFormulario.add(lblCorreo);
//        pnlFormulario.add(txtCorreo);
//        pnlFormulario.add(lblEstatus);
//        pnlFormulario.add(txtEstatus);
//        pnlFormulario.add(lblRol);
//        pnlFormulario.add(cmbRol);
//
//        // Panel para los botones inferiores
//        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
//        Dimension tamanhoBotones = new Dimension(140, 35);
//        btnAgregar.setPreferredSize(tamanhoBotones);
//        btnSalir.setPreferredSize(tamanhoBotones);
//        
//        pnlBotones.add(btnAgregar);
//        pnlBotones.add(btnSalir);
//
//        // Agregar elementos al panel principal vertical
//        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
//        lblAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
//        panelPrincipal.add(lblAgregar);
//        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
//        panelPrincipal.add(pnlFormulario);
//        panelPrincipal.add(pnlBotones);
//
//        this.add(panelPrincipal);
//        this.setVisible(true);    
//    }
//    
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == btnSalir) {
//            new Accion();
//            this.dispose(); 
//        }
//        if (e.getSource() == btnAgregar) {
//            String username = txtUsername.getText().trim();
//            String password = new String(txtPassword.getPassword());
//            String nombres = txtNombres.getText().trim();
//            String apellidos = txtApellidos.getText().trim();
//            String correo = txtCorreo.getText().trim();
//            String estatus = txtEstatus.getText().trim();
//            
//            // Mapeo del rol seleccionado al id_rol de la base de datos (1 = Admin, 2 = Empleado)
//            int idRol = cmbRol.getSelectedItem().equals("Admin") ? 1 : 2;
//
//            if (username.isEmpty() || password.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            try {
//                // Conexión a la base de datos 'tienda' (ajusta el usuario y contraseña de tu gestor MySQL si es necesario)
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", "");
//                
//                // Consulta SQL adaptada exactamente a tu estructura con SHA2 para la contraseña y CURDATE() para la fecha
//                String query = "INSERT INTO usuarios (username, password_user, first_name, last_name, email, user_status, id_rol, fecha_creacion) VALUES (?, SHA2(?, 256), ?, ?, ?, ?, ?, CURDATE())";
//                
//                PreparedStatement ps = con.prepareStatement(query);
//                ps.setString(1, username);
//                ps.setString(2, password);
//                ps.setString(3, nombres);
//                ps.setString(4, apellidos);
//                ps.setString(5, correo);
//                ps.setString(6, estatus);
//                ps.setInt(7, idRol);
//                
//                ps.executeUpdate();
//                ps.close();
//                con.close();
//                
//                JOptionPane.showMessageDialog(this, "¡Usuario agregado con éxito a la base de datos 'tienda'!");
//                limpiar();
//                
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//    
//    public void limpiar(){
//        txtUsername.setText("");
//        txtPassword.setText("");
//        txtNombres.setText("");
//        txtApellidos.setText("");
//        txtCorreo.setText("");
//        txtEstatus.setText("Active");
//        cmbRol.setSelectedIndex(0);
//    }
//}
package proyectotallerbasedatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AgregarNuevoUsuario extends JFrame implements ActionListener {
    private JLabel lblAgregar, lblUsername, lblPassword, lblNombres, lblApellidos, lblCorreo, lblEstatus, lblRol;
    private JButton btnAgregar, btnSalir;
    private JComboBox<String> cmbRol;
    private JTextField txtUsername, txtNombres, txtApellidos, txtCorreo, txtEstatus;
    private JPasswordField txtPassword;

    public AgregarNuevoUsuario() {
        super("Agregar Nuevo Usuario");
        this.setSize(550, 620); 
        this.setLocationRelativeTo(null); // Centrado en pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
        
        // Inicialización de elementos
        lblAgregar = new JLabel("** AGREGAR NUEVO USUARIO **");
        lblAgregar.setFont(fuenteGigante);
        lblUsername = new JLabel("Username:");
        lblUsername.setFont(fuenteGigante);
        txtUsername = new JTextField(15);
        txtUsername.setFont(fuenteGigante);
        lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(fuenteGigante);
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(fuenteGigante);        
        lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(fuenteGigante);
        txtNombres = new JTextField(15);
        txtNombres.setFont(fuenteGigante);
        lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(fuenteGigante);
        txtApellidos = new JTextField(15);
        txtApellidos.setFont(fuenteGigante);
        lblCorreo = new JLabel("Correo:");
        lblCorreo.setFont(fuenteGigante);
        txtCorreo = new JTextField(15);
        txtCorreo.setFont(fuenteGigante);
        lblEstatus = new JLabel("Estatus:");
        lblEstatus.setFont(fuenteGigante);
        txtEstatus = new JTextField(15);
        txtEstatus.setText("Active"); // Valor por defecto común
        txtEstatus.setFont(fuenteGigante);
        lblRol = new JLabel("Rol:");
        lblRol.setFont(fuenteGigante);
        cmbRol = new JComboBox<>(new String[]{"Admin", "Empleado"});
        cmbRol.setFont(fuenteGigante);
        btnAgregar = new JButton("AGREGAR");
        btnAgregar.setFont(fuenteGigante);
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuenteGigante);
        
        // Dar acciones alos botones 
        btnSalir.addActionListener(this);
        btnAgregar.addActionListener(this);
        //panel donde estaran los elementos
        JPanel pnlFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        pnlFormulario.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        //Agregar elementos al panel 
        pnlFormulario.add(lblUsername);
        pnlFormulario.add(txtUsername);
        pnlFormulario.add(lblPassword);
        pnlFormulario.add(txtPassword);
        pnlFormulario.add(lblNombres);
        pnlFormulario.add(txtNombres);
        pnlFormulario.add(lblApellidos);
        pnlFormulario.add(txtApellidos);
        pnlFormulario.add(lblCorreo);
        pnlFormulario.add(txtCorreo);
        pnlFormulario.add(lblEstatus);
        pnlFormulario.add(txtEstatus);
        pnlFormulario.add(lblRol);
        pnlFormulario.add(cmbRol);

        // Panel para los botones de abajo
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Dimension tamanhoBotones = new Dimension(140, 35);
        btnAgregar.setPreferredSize(tamanhoBotones);
        btnSalir.setPreferredSize(tamanhoBotones);
        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnSalir);

        // Agregar elementos al panel principal 
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        lblAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblAgregar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(pnlFormulario);
        panelPrincipal.add(pnlBotones);

        this.add(panelPrincipal);
        this.setVisible(true);    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalir) {
            new Accion(1); 
            this.dispose(); 
        }
        if (e.getSource() == btnAgregar) {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String correo = txtCorreo.getText().trim();
            String estatus = txtEstatus.getText().trim();

            int idRol = cmbRol.getSelectedItem().equals("Admin") ? 1 : 2;

            if (username.isEmpty() || password.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //guardado mediante la clase de conexion 
            Conexion cc = new Conexion();
            String query = "INSERT INTO usuarios (username, password_user, first_name, last_name, email, user_status, id_rol, fecha_creacion) VALUES (?, SHA2(?, 256), ?, ?, ?, ?, ?, CURDATE())";
            
            try (Connection con = cc.conectar(); PreparedStatement ps = con.prepareStatement(query)) {
                if (con != null) {
                    ps.setString(1, username);
                    ps.setString(2, password);
                    ps.setString(3, nombres);
                    ps.setString(4, apellidos);
                    ps.setString(5, correo);
                    ps.setString(6, estatus);
                    ps.setInt(7, idRol);
                    
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "¡Usuario agregado con éxito a la base de datos 'tienda'!");
                    limpiar();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void limpiar(){
        txtUsername.setText("");
        txtPassword.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");
        txtEstatus.setText("Active");
        cmbRol.setSelectedIndex(0);
    }
}