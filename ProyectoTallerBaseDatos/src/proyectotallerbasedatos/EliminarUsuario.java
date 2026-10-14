
package proyectotallerbasedatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EliminarUsuario extends JFrame implements ActionListener {
    private JLabel lblTitulo, lblUsuarioSelect;
    private JButton btnEliminar, btnSalir;
    private JComboBox<String> cmbUsuarios;

    public EliminarUsuario() {
        super("Eliminar Usuario");
        this.setSize(550, 380); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
        
        // Inicialización de elementos
        lblTitulo = new JLabel("** ELIMINAR USUARIO **");
        lblTitulo.setFont(fuenteGigante);
        lblUsuarioSelect = new JLabel("Seleccionar Usuario:");
        lblUsuarioSelect.setFont(fuenteGigante);     
        cmbUsuarios = new JComboBox<>();
        cmbUsuarios.setFont(fuenteGigante);
        cargarUsuarios(); // Método para cargar los usernames desde la base de datos      
        btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setFont(fuenteGigante);
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuenteGigante);
        
        // Dar acciones a los botones 
        btnSalir.addActionListener(this);
        btnEliminar.addActionListener(this);      
        // Panel para el formulario
        JPanel pnlFormulario = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlFormulario.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));       
        pnlFormulario.add(lblUsuarioSelect);
        pnlFormulario.add(cmbUsuarios);
        // Panel para los botones inferiores
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Dimension tamanhoBotones = new Dimension(140, 35);
        btnEliminar.setPreferredSize(tamanhoBotones);
        btnSalir.setPreferredSize(tamanhoBotones);      
        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnSalir);
        // Agregar elementos al panel principal vertical
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(pnlFormulario);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalir) {
            new Accion(1); 
            this.dispose(); 
        }
        if (e.getSource() == btnEliminar) {
            String usuarioSeleccionado = (String) cmbUsuarios.getSelectedItem();
            if (usuarioSeleccionado != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar al usuario: " + usuarioSeleccionado + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    Conexion cc = new Conexion();
                    String sql = "DELETE FROM usuarios WHERE username = ?";
                    
                    try (Connection con = cc.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                        if (con != null) {
                            ps.setString(1, usuarioSeleccionado);
                            ps.executeUpdate();
                            
                            JOptionPane.showMessageDialog(this, "¡Usuario eliminado con éxito de la base de datos 'tienda'!");
                            cmbUsuarios.removeItem(usuarioSeleccionado);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error al eliminar de la base de datos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay ningún usuario seleccionado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}