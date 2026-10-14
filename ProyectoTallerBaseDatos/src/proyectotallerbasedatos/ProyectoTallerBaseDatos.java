
package proyectotallerbasedatos;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jvnet.substance.SubstanceLookAndFeel;

public class ProyectoTallerBaseDatos extends JFrame implements ActionListener {
    public JButton btnLogin;
    public JTextField txtUsuario;
    JPasswordField txtContraseña;
    public JLabel lblUsuario, lblContraseña, lblTipo;
    public JLabel lblMensaje;
    public JButton btnCancelar;
    
    public ProyectoTallerBaseDatos() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.RavenSkin");
        this.setTitle("Login");
        this.setSize(850, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initCompo();
        this.setVisible(true);
    }

    private void initCompo() {
        JPanel pnl = new JPanel(new GridLayout(5, 2, 15, 15));
        txtUsuario = new JTextField(35);
        txtContraseña = new JPasswordField(35);
        Font fuenteGigante = new Font("Arial", Font.BOLD, 20);
        txtUsuario.setFont(fuenteGigante);
        txtContraseña.setFont(fuenteGigante);
        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(fuenteGigante);
        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setFont(fuenteGigante);
        JLabel lblUsuarioText = new JLabel("Usuario:"); 
        lblUsuarioText.setFont(fuenteGigante);
        JLabel lblContrasenaText = new JLabel("Contraseña:"); 
        lblContrasenaText.setFont(fuenteGigante);
        lblMensaje = new JLabel("", JLabel.CENTER);
        lblMensaje.setFont(fuenteGigante);
        btnLogin.addActionListener(this);
         btnCancelar.addActionListener(this);
        pnl.add(lblUsuarioText); pnl.add(txtUsuario);
        pnl.add(lblContrasenaText); pnl.add(txtContraseña);
        pnl.add(btnLogin); pnl.add(btnCancelar);
        pnl.add(lblMensaje);
        JPanel panelContenedor = new JPanel(new GridBagLayout());
        panelContenedor.add(pnl);
        this.add(panelContenedor);
    }

    public static void main(String[] args) { 
        new ProyectoTallerBaseDatos();
    }

@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String usuario = txtUsuario.getText().trim();
            String password = new String(txtContraseña.getPassword()).trim();
            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese usuario y contraseña.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Conexion cc = new Conexion();
            // Consulta estricta que busca el id_rol real asociado a ese usuario y contraseña cifrada
            String sql = "SELECT id_rol FROM usuarios WHERE username = ? AND password_user = SHA2(?, 256)";
        
            try (Connection c = cc.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
                if (c != null) {
                    ps.setString(1, usuario);
                    ps.setString(2, password);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            int rolBD = rs.getInt("id_rol"); // 1 administrador, 2 empleado

                            JOptionPane.showMessageDialog(this, "¡Bienvenido, " );
                            new Accion(rolBD); 
                            this.dispose(); // Cierra el login
                        } else {
                            JOptionPane.showMessageDialog(this, "Credenciales incorrectas o usuario no registrado.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
                        }}}
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error en la consulta de acceso: " + ex.getMessage());
            }
        }
        if (e.getSource() == btnCancelar) {
            System.exit(0);
        }
    }
}