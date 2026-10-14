
package proyectotallerbasedatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Accion extends JFrame implements ActionListener {
    private JButton btnInsertar, btnConsultarUsuarios, btnSalir, btnInsertarMercancia, btnConsultarMercancia,
            btnModificarUsuarios, btnModificarMercancia, btnEliminarUsuarios, btnEliminarProducto;
    private JLabel lblOpcion;
    private int rolUsuario; //contiene el rol de usuario

    public Accion(int rolUsuario) {
        this.rolUsuario = rolUsuario;
        this.setTitle(rolUsuario == 1 ? "Panel de Administrador" : "Panel de Empleado");
        this.setSize(850, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    
        
        JPanel pnlBotones = new JPanel();
        pnlBotones.setLayout(new BoxLayout(pnlBotones, BoxLayout.Y_AXIS));
        Font fuenteGigante = new Font("Arial", Font.BOLD, 20);
        
        lblOpcion = new JLabel(rolUsuario == 1 ? "** MENÚ ADMINISTRADOR **" : "** MENÚ EMPLEADO **", SwingConstants.CENTER);
        lblOpcion.setFont(fuenteGigante);
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuenteGigante);
        btnSalir.addActionListener(this);

        Dimension tamanhoBotones = new Dimension(280, 60);
        pnlBotones.add(Box.createRigidArea(new Dimension(0, 20)));
        pnlBotones.add(lblOpcion);
        pnlBotones.add(Box.createRigidArea(new Dimension(0, 15))); 

        if (rolUsuario == 1) {
            btnInsertar = new JButton("AGREGAR USUARIO");
            btnConsultarUsuarios = new JButton("CONSULTAR USUARIOS");
            btnInsertarMercancia = new JButton("INSERTAR PRODUCTO");
            btnConsultarMercancia = new JButton("CONSULTAR PRODUCTO"); 
            btnModificarUsuarios = new JButton("MODIFICAR USUARIO"); 
            btnModificarMercancia = new JButton("MODIFICAR PRODUCTO"); 
            btnEliminarUsuarios = new JButton("ELIMINAR USUARIO"); 
            btnEliminarProducto = new JButton("ELIMINAR PRODUCTO"); 

            JButton[] botonesAdmin = {btnInsertar, btnConsultarUsuarios, btnInsertarMercancia, 
                btnConsultarMercancia, btnModificarUsuarios, btnModificarMercancia, btnEliminarUsuarios, btnEliminarProducto};

            for (JButton btn : botonesAdmin) {
                btn.setFont(fuenteGigante);
                configurarBoton(btn, tamanhoBotones);
                btn.addActionListener(this);
                pnlBotones.add(btn);
                pnlBotones.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        } else {
            btnConsultarUsuarios = new JButton("DIRECTORIO DE USUARIOS");
            btnConsultarMercancia = new JButton("CONSULTAR PRODUCTOS");
            JButton[] botonesEmpleado = {btnConsultarUsuarios, btnConsultarMercancia};
            for (JButton btn : botonesEmpleado) {
                btn.setFont(fuenteGigante);
                configurarBoton(btn, tamanhoBotones);
                btn.addActionListener(this);
                pnlBotones.add(btn);
                pnlBotones.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        configurarBoton(btnSalir, tamanhoBotones);
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

    private void configurarBoton(JComponent comp, Dimension dim) {
        comp.setPreferredSize(dim);
        comp.setMaximumSize(dim);
        comp.setMinimumSize(dim);
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalir) {
            new ProyectoTallerBaseDatos();
            this.dispose();
        }
        
        // Acciones Administrador
        if (rolUsuario == 1) {
            if (e.getSource() == btnInsertar) {
                new AgregarNuevoUsuario();
                this.dispose();
            } else if (e.getSource() == btnConsultarUsuarios) {
                new ConsultarUsuarios();
                this.dispose();
            } else if (e.getSource() == btnInsertarMercancia) {
                new InsertarProducto();
                this.dispose();
            } else if (e.getSource() == btnConsultarMercancia) {
                new ConsultarProducto();
                this.dispose();
            } else if (e.getSource() == btnModificarUsuarios) {
                new EditarUsuario(); 
                this.dispose();
            } else if (e.getSource() == btnModificarMercancia) {
                new EditarProducto(); 
                this.dispose();
            } else if (e.getSource() == btnEliminarUsuarios) {
                new EliminarUsuario();
                this.dispose();
            } else if (e.getSource() == btnEliminarProducto) {
                new EliminarProducto();
                this.dispose();
            }
        } else {
            // Acciones Empleado
            if (e.getSource() == btnConsultarUsuarios) {
                new ConsultarDatosEmpleado(); 
                this.dispose();
            } else if (e.getSource() == btnConsultarMercancia) {
                new ConsultarProducto(); 
                this.dispose();
            }
        }
    }
}