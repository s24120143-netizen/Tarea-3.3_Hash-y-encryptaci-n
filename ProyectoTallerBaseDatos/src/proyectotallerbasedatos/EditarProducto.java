
package proyectotallerbasedatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditarProducto extends JFrame implements ActionListener, ItemListener {
    private JLabel lblTitulo, lblSeleccionar, lblNombre, lblPrecio, lblCantidad;
    private JButton btnActualizar, btnSalir;
    private JComboBox<String> cmbProductos;
    private JTextField txtNombre, txtPrecio, txtCantidad;

    public EditarProducto() {
        super("Editar Producto");
        this.setSize(550, 480); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
        
        lblTitulo = new JLabel("** MODIFICAR PRODUCTO **");
        lblTitulo.setFont(fuenteGigante);
        
        lblSeleccionar = new JLabel("Seleccionar:");
        lblSeleccionar.setFont(fuenteGigante);
        cmbProductos = new JComboBox<>();
        cmbProductos.setFont(fuenteGigante);
        cmbProductos.addItemListener(this); 
        
        // Carga protegida para evitar que falle el constructor
        try {
            cargarProductos();
        } catch (Exception e) {
            System.out.println("Aviso al cargar productos en constructor: " + e.getMessage());
        }
        
        lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fuenteGigante);
        txtNombre = new JTextField(15);
        txtNombre.setFont(fuenteGigante);
        lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(fuenteGigante);
        txtPrecio = new JTextField(15);
        txtPrecio.setFont(fuenteGigante);
        lblCantidad = new JLabel("Cantidad (Existencia):");
        lblCantidad.setFont(fuenteGigante);
        txtCantidad = new JTextField(15);
        txtCantidad.setFont(fuenteGigante);
        btnActualizar = new JButton("ACTUALIZAR");
        btnActualizar.setFont(fuenteGigante);
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuenteGigante);
        //aciones
        btnSalir.addActionListener(this);
        btnActualizar.addActionListener(this);
        
        JPanel pnlFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlFormulario.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        pnlFormulario.add(lblSeleccionar); pnlFormulario.add(cmbProductos);
        pnlFormulario.add(lblNombre); pnlFormulario.add(txtNombre);
        pnlFormulario.add(lblPrecio); pnlFormulario.add(txtPrecio);
        pnlFormulario.add(lblCantidad); pnlFormulario.add(txtCantidad);

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
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String seleccionado = (String) cmbProductos.getSelectedItem();
            if (seleccionado != null) {
                Conexion cc = new Conexion();
                String sql = "SELECT nombre, precio, existencia FROM productos WHERE nombre = ?";
                
                try (Connection con = cc.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                    if (con != null) {
                        ps.setString(1, seleccionado);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                txtNombre.setText(rs.getString("nombre"));
                                txtPrecio.setText(String.valueOf(rs.getDouble("precio")));
                                txtCantidad.setText(String.valueOf(rs.getInt("existencia")));
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al consultar el producto: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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
            String productoOriginal = (String) cmbProductos.getSelectedItem();
            String nuevoNombre = txtNombre.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            String cantidadStr = txtCantidad.getText().trim();
            
            if (productoOriginal != null && !nuevoNombre.isEmpty() && !precioStr.isEmpty() && !cantidadStr.isEmpty()) {
                Conexion cc = new Conexion();
                String sql = "UPDATE productos SET nombre = ?, precio = ?, existencia = ? WHERE nombre = ?";
                
                try (Connection con = cc.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                    if (con != null) {
                        ps.setString(1, nuevoNombre);
                        ps.setDouble(2, Double.parseDouble(precioStr));
                        ps.setInt(3, Integer.parseInt(cantidadStr));
                        ps.setString(4, productoOriginal);
                        
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(this, "¡Producto actualizado con éxito!");
                    }
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor complete los campos correctamente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}