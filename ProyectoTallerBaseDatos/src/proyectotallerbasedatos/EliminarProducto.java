
package proyectotallerbasedatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EliminarProducto extends JFrame implements ActionListener {
    private JLabel lblTitulo, lblProductoSelect;
    private JButton btnEliminar, btnSalir;
    private JComboBox<String> cmbProductos;

    public EliminarProducto() {
        super("Eliminar Producto");
        this.setSize(550, 380);
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
        
        // Inicialización de elementos
        lblTitulo = new JLabel("** ELIMINAR PRODUCTO **");
        lblTitulo.setFont(fuenteGigante);        
        lblProductoSelect = new JLabel("Seleccionar Producto:");
        lblProductoSelect.setFont(fuenteGigante);        
        cmbProductos = new JComboBox<>();
        cmbProductos.setFont(fuenteGigante);
        cargarProductos();   
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
        pnlFormulario.add(lblProductoSelect);
        pnlFormulario.add(cmbProductos);

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

    private void cargarProductos() {
        Conexion cc = new Conexion();
        String sql = "SELECT nombre FROM productos";
        
        try (Connection con = cc.conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (con != null) {
                while (rs.next()) {
                    cmbProductos.addItem(rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalir) {
            new Accion(1); 
            this.dispose(); 
        }
        if (e.getSource() == btnEliminar) {
            String productoSeleccionado = (String) cmbProductos.getSelectedItem();
            if (productoSeleccionado != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar el producto: " + productoSeleccionado + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    Conexion cc = new Conexion();
                    String sql = "DELETE FROM productos WHERE nombre = ?";
                    try (Connection con = cc.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                     if (con != null) {
                            ps.setString(1, productoSeleccionado);
                            ps.executeUpdate();
                            
                            JOptionPane.showMessageDialog(this, "¡Producto eliminado con éxito de la base de datos 'tienda'!");
                            cmbProductos.removeItem(productoSeleccionado); 
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error al eliminar de la base de datos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay ningún producto seleccionado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}