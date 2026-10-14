
package proyectotallerbasedatos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ConsultarProducto extends JFrame implements ActionListener {
    private JLabel lblTitulo;
    private JTable tblProductos;
    private JScrollPane scrollPane;
    private JButton btnSalir;

    public ConsultarProducto() {
        super("Consultar Productos");
        this.setSize(650, 500); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 15);
        
        // Inicialización de elementos
        lblTitulo = new JLabel("** CONSULTAR PRODUCTOS **");
        lblTitulo.setFont(fuenteGigante);
        //cargar la tabla
        String[] columnas = {"ID", "Nombre", "Precio", "Cantidad (Existencia)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        cargarDatosProductos(modelo);
        
        tblProductos = new JTable(modelo);
        tblProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        tblProductos.getTableHeader().setFont(fuenteGigante);
        tblProductos.setRowHeight(25);       
        scrollPane = new JScrollPane(tblProductos);
        scrollPane.setPreferredSize(new Dimension(550, 300));
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuenteGigante);
        btnSalir.addActionListener(this);
        
        // Panel para los botones de abajo
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
    

    private void cargarDatosProductos(DefaultTableModel modelo) {
        Conexion cc = new Conexion();
        String sql = "SELECT id, nombre, precio, existencia FROM productos";
        
        try (Connection con = cc.conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (con != null) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        "$" + rs.getDouble("precio"),
                        rs.getInt("existencia")
                    };
                    modelo.addRow(fila);
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
    }
}