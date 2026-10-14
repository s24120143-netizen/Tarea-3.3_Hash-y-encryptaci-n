
package proyectotallerbasedatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InsertarProducto extends JFrame implements ActionListener {
    private JLabel lblAgregar, lblNombre, lblPrecio, lblCantidad;
    private JButton btnInsertar, btnSalir;
    private JTextField txtNombre, txtPrecio, txtCantidad;

    public InsertarProducto() {
        super("Insertar Producto");
        this.setSize(550, 420);
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
        
        // Inicialización de elementos
        lblAgregar = new JLabel("** INSERTAR PRODUCTO **");
        lblAgregar.setFont(fuenteGigante);
        lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fuenteGigante);
        txtNombre = new JTextField(15);
        txtNombre.setFont(fuenteGigante);
        lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(fuenteGigante);
        txtPrecio = new JTextField(15);
        txtPrecio.setFont(fuenteGigante);
        lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(fuenteGigante);
        txtCantidad = new JTextField(15);
        txtCantidad.setFont(fuenteGigante);
        btnInsertar = new JButton("INSERTAR");
        btnInsertar.setFont(fuenteGigante);
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuenteGigante);
        
        // Dar acciones a los botones 
        btnSalir.addActionListener(this);
        btnInsertar.addActionListener(this);
                JPanel pnlFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlFormulario.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        pnlFormulario.add(lblNombre);
        pnlFormulario.add(txtNombre);
        pnlFormulario.add(lblPrecio);
        pnlFormulario.add(txtPrecio);
        pnlFormulario.add(lblCantidad);
        pnlFormulario.add(txtCantidad);

        // Panel para los botones de abajo
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Dimension tamanhoBotones = new Dimension(140, 35);
        btnInsertar.setPreferredSize(tamanhoBotones);
        btnSalir.setPreferredSize(tamanhoBotones);
        
        pnlBotones.add(btnInsertar);
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
        if (e.getSource() == btnInsertar) {
            String nombre = txtNombre.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            String cantidadStr = txtCantidad.getText().trim();
            
            if (nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // guarda los datos mediante la clase de conexion 
            Conexion cc = new Conexion();
            String sql = "INSERT INTO productos (nombre, precio, existencia) VALUES (?, ?, ?)";
            
            try (Connection con = cc.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                if (con != null) {
                    ps.setString(1, nombre);
                    ps.setDouble(2, Double.parseDouble(precioStr));
                    ps.setInt(3, Integer.parseInt(cantidadStr));
                    
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "¡Producto insertado con éxito en la base de datos 'tienda'!");
                    limpiar();
                }
            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error al insertar el producto: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void limpiar(){
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
    }
}