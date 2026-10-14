// clase que quedo obsoleta 
// ya no se logro rescatar
package proyectotallerbasedatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AccionEmpleado extends JFrame implements ActionListener {
    private JButton btnConsultarUsuarios, btnSalir, btnConsultarMercancia;
    private JLabel lblOpcion;

    public AccionEmpleado() {
        // Especificaciones de la ventana
        this.setTitle("Panel de Acciones - Empleado");
        this.setSize(850, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        // Formato del layout y tipo de letra
        JPanel pnlBotones = new JPanel();
        pnlBotones.setLayout(new BoxLayout(pnlBotones, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 20);
        
        // Inicialización de elementos 
        lblOpcion = new JLabel("**INGRESE LA OPCION**", SwingConstants.CENTER);
        lblOpcion.setFont(fuenteGigante);
        
        btnConsultarUsuarios = new JButton("CONSULTAR USUARIOS");
        btnConsultarUsuarios.setFont(fuenteGigante);
        
        btnConsultarMercancia = new JButton("CONSULTAR PRODUCTO"); 
        btnConsultarMercancia.setFont(fuenteGigante);
        
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuenteGigante);
        
        // Darle formato a los tamaños de los botones 
        Dimension tamanhoBotones = new Dimension(320, 80); // Ligeramente ampliado para que el texto quepa con holgura
        JComponent[] elementos = {lblOpcion, btnConsultarUsuarios, btnConsultarMercancia, btnSalir};
        for (JComponent elemento : elementos) {
            elemento.setPreferredSize(tamanhoBotones);
            elemento.setMaximumSize(tamanhoBotones);
            elemento.setMinimumSize(tamanhoBotones);
            elemento.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        
        // Darle acción a los botones 
        btnConsultarUsuarios.addActionListener(this);
        btnConsultarMercancia.addActionListener(this);
        btnSalir.addActionListener(this);
        
        // Insertar elementos en el panel vertical
        pnlBotones.add(Box.createRigidArea(new Dimension(0, 20))); // Separación superior
        pnlBotones.add(lblOpcion);
        pnlBotones.add(Box.createRigidArea(new Dimension(0, 15))); 
        pnlBotones.add(btnConsultarUsuarios);
        pnlBotones.add(Box.createRigidArea(new Dimension(0, 15))); 
        pnlBotones.add(btnConsultarMercancia);
        pnlBotones.add(Box.createRigidArea(new Dimension(0, 15))); 
        pnlBotones.add(btnSalir);
        
        JPanel panelContenedor = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH; 
        gbc.weighty = 1.0; 
       
        panelContenedor.add(pnlBotones, gbc);
        this.add(panelContenedor);
        
        this.setVisible(true); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalir) {
            new ProyectoTallerBaseDatos(); // Regresa al Login
            this.dispose();
        }
        if (e.getSource() == btnConsultarUsuarios) {
            new ConsultarDatosEmpleado(); // Abre la ventana limpia para empleados
            this.dispose();
        }
        if (e.getSource() == btnConsultarMercancia) {
            new ConsultarProductosEmpleado(); // Abre la ventana de productos para empleados
            this.dispose();
        }
    }
}