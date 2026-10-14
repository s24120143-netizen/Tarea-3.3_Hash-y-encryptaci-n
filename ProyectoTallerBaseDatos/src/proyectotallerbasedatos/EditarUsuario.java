
package proyectotallerbasedatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditarUsuario extends JFrame implements ActionListener, ItemListener {
    private JLabel lblTitulo, lblSeleccionar, lblUsername, lblNombres, lblApellidos, lblCorreo, lblEstatus, lblRol;
    private JButton btnActualizar, btnSalir;
    private JComboBox<String> cmbUsuarios;
    private JTextField txtUsername, txtNombres, txtApellidos, txtCorreo, txtEstatus;
    private JComboBox<String> cmbRol;

    public EditarUsuario() {
        super("Editar Usuario");
        this.setSize(550, 580); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
        
        // Inicialización de elementos
        lblTitulo = new JLabel("** EDITAR USUARIO **");
        lblTitulo.setFont(fuenteGigante);      
        lblSeleccionar = new JLabel("Seleccionar Usuario:");
        lblSeleccionar.setFont(fuenteGigante);
        cmbUsuarios = new JComboBox<>();
        cmbUsuarios.setFont(fuenteGigante);
        cmbUsuarios.addItemListener(this); 

        try {
            cargarUsuarios();
        } catch (Exception e) {
            System.out.println("Aviso al cargar usuarios en constructor: " + e.getMessage());
        }      
        lblUsername = new JLabel("Username:");
        lblUsername.setFont(fuenteGigante);
        txtUsername = new JTextField(15);
        txtUsername.setFont(fuenteGigante);
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
        txtEstatus.setFont(fuenteGigante);
        lblRol = new JLabel("Rol:");
        lblRol.setFont(fuenteGigante);
        cmbRol = new JComboBox<>(new String[]{"Admin", "Empleado"});
        cmbRol.setFont(fuenteGigante);
        btnActualizar = new JButton("ACTUALIZAR");
        btnActualizar.setFont(fuenteGigante);
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuenteGigante);       
        btnSalir.addActionListener(this);
        btnActualizar.addActionListener(this);
        
        JPanel pnlFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        pnlFormulario.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        pnlFormulario.add(lblSeleccionar); pnlFormulario.add(cmbUsuarios);
        pnlFormulario.add(lblUsername); pnlFormulario.add(txtUsername);
        pnlFormulario.add(lblNombres); pnlFormulario.add(txtNombres);
        pnlFormulario.add(lblApellidos); pnlFormulario.add(txtApellidos);
        pnlFormulario.add(lblCorreo); pnlFormulario.add(txtCorreo);
        pnlFormulario.add(lblEstatus); pnlFormulario.add(txtEstatus);
        pnlFormulario.add(lblRol); pnlFormulario.add(cmbRol);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Dimension tamanhoBotones = new Dimension(140, 35);
        btnActualizar.setPreferredSize(tamanhoBotones);
        btnSalir.setPreferredSize(tamanhoBotones);
        
        pnlBotones.add(btnActualizar);
        pnlBotones.add(btnSalir);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(pnlFormulario);
        panelPrincipal.add(pnlBotones);

        this.add(panelPrincipal);
        this.setVisible(true);
    }
    
    private void cargarUsuarios() {
        Conexion cc = new Conexion();
        String sql = "SELECT username FROM usuarios";
        
        try (Connection con = cc.conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (con != null) {
                while (rs.next()) {
                    cmbUsuarios.addItem(rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los usuarios: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String seleccionado = (String) cmbUsuarios.getSelectedItem();
            if (seleccionado != null) {
                Conexion cc = new Conexion();
                String sql = "SELECT username, first_name, last_name, email, user_status, id_rol FROM usuarios WHERE username = ?";
                
                try (Connection con = cc.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                    if (con != null) {
                        ps.setString(1, seleccionado);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                txtUsername.setText(rs.getString("username"));
                                txtNombres.setText(rs.getString("first_name"));
                                txtApellidos.setText(rs.getString("last_name"));
                                txtCorreo.setText(rs.getString("email"));
                                txtEstatus.setText(rs.getString("user_status"));
                                
                                int idRol = rs.getInt("id_rol");
                                if (idRol == 1) {
                                    cmbRol.setSelectedItem("Admin");
                                } else {
                                    cmbRol.setSelectedItem("Empleado");
                                }
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al consultar el usuario: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalir) {
            new Accion(1);
            this.dispose(); 
        }
        if (e.getSource() == btnActualizar) {
            String usuarioOriginal = (String) cmbUsuarios.getSelectedItem();
            String nuevoUsername = txtUsername.getText().trim();
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String correo = txtCorreo.getText().trim();
            String estatus = txtEstatus.getText().trim();
            int idRol = cmbRol.getSelectedItem().equals("Admin") ? 1 : 2;
            
            if (usuarioOriginal != null && !nuevoUsername.isEmpty()) {
                Conexion cc = new Conexion();
                String sql = "UPDATE usuarios SET username = ?, first_name = ?, last_name = ?, email = ?, user_status = ?, id_rol = ? WHERE username = ?";
                
                try (Connection con = cc.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                    if (con != null) {
                        ps.setString(1, nuevoUsername);
                        ps.setString(2, nombres);
                        ps.setString(3, apellidos);
                        ps.setString(4, correo);
                        ps.setString(5, estatus);
                        ps.setInt(6, idRol);
                        ps.setString(7, usuarioOriginal);
                        
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(this, "¡Usuario actualizado con éxito!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el usuario: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}