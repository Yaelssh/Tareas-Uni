package Suscripcion;

//Importación de paquetes para sql e interfaz grafica
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

//Herencia a JFrame
public class Suscripcion extends JFrame {

    //Tipos de datos 
    JMenuBar mb;
    JMenu Regresar, DataBase;
    JMenuItem bd_c, bd_r, bd_u, bd_d, volver;
    JLabel lbl, lbl_id, lbl_n, lbl_a, lbl_e, lbl_s, lbl_ns;
    JTextField txt_id, txt_n, txt_a, txt_e, txt_s, txt_ns;
    JButton btn_agg, btn_list, btn_edit, btn_del, btn_sid, btn_sns;

    //Constructor de suscripción
    Suscripcion() {

        //Construcción del layout de la interfaz
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBounds(150, 50, 800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(null);
        this.setBackground(Color.BLUE);

        //Agregar el menu al layout
        this.menu();

        //Configuración de los elementos graficos de la interfaz
        //Parte superior 
        this.lbl = new JLabel("Bienvenido al gestor de suscripcion");
        this.lbl.setBounds(300, 20, 400, 30);
        this.lbl.setFont(new Font("Times New Roman", Font.ITALIC, 20)); //<- Cambia el fondo y tamaño de la letra

        //Resto del layout
        //Creación de los Labels (etiquetas)
        this.lbl_id = new JLabel("ID");
        this.lbl_id.setBounds(100, 60, 200, 30);

        this.lbl_n = new JLabel("Nombre");
        this.lbl_n.setBounds(100, 100, 200, 30);

        this.lbl_a = new JLabel("Apellido");
        this.lbl_a.setBounds(100, 140, 200, 30);

        this.lbl_e = new JLabel("edad");
        this.lbl_e.setBounds(100, 180, 200, 30);

        this.lbl_s = new JLabel("sexo");
        this.lbl_s.setBounds(100, 220, 200, 30);

        this.lbl_ns = new JLabel("numero de suscriptor");
        this.lbl_ns.setBounds(100, 260, 200, 30);

        //Creación de los TextField (Campo de texto)
        this.txt_id = new JTextField("");
        this.txt_id.setBounds(250, 60, 250, 30);

        this.txt_n = new JTextField("");
        this.txt_n.setBounds(250, 100, 250, 30);

        this.txt_a = new JTextField("");
        this.txt_a.setBounds(250, 140, 250, 30);

        this.txt_e = new JTextField("");
        this.txt_e.setBounds(250, 180, 250, 30);

        this.txt_s = new JTextField("");
        this.txt_s.setBounds(250, 220, 250, 30);

        this.txt_ns = new JTextField("");
        this.txt_ns.setBounds(250, 260, 250, 30);

        //Creación de los button (botones)
        this.btn_agg = new JButton("Agregar");
        this.btn_agg.setBounds(130, 300, 120, 40);
        //Action listener llama a un metodo que seleccionemos creados más abajo
        this.btn_agg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Agregar();
            }
        });

        this.btn_list = new JButton("Consultar DB");
        this.btn_list.setBounds(260, 300, 120, 40);
        this.btn_list.addActionListener((var e) -> Consultar());

        this.btn_edit = new JButton("Editar");
        this.btn_edit.setBounds(390, 300, 120, 40);
        this.btn_edit.addActionListener((var e) -> Editar());

        this.btn_del = new JButton("Eliminar");
        this.btn_del.setBounds(520, 300, 120, 40);
        this.btn_del.addActionListener((var e) -> Eliminar());

        this.btn_sid = new JButton("buscar id");
        this.btn_sid.setBounds(650, 300, 120, 40);
        this.btn_sid.addActionListener((var e) -> BuscarID());

        this.btn_sns = new JButton("Buscar NS");
        this.btn_sns.setBounds(780, 300, 120, 40);
        this.btn_sns.addActionListener((var e) -> BuscarNS());

        //agregar elementos del layout
        //agregar labels (Etiqueta)
        this.add(lbl);
        this.add(lbl_id);
        this.add(lbl_n);
        this.add(lbl_a);
        this.add(lbl_e);
        this.add(lbl_s);
        this.add(lbl_ns);

        //Agregar textfield (campo de texto)
        this.add(txt_id);
        this.add(txt_n);
        this.add(txt_a);
        this.add(txt_e);
        this.add(txt_s);
        this.add(txt_ns);

        //Agregar botones
        this.add(btn_agg);
        this.add(btn_list);
        this.add(btn_edit);
        this.add(btn_del);
        this.add(btn_sid);
        this.add(btn_sns);

        //Muestra la interfaz
        this.setVisible(true);
    }

    //Metodos y consulta a la base de datos
    public void Agregar() {
        try {
            Connection cnx;
            PreparedStatement ps;
            ResultSet rs;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", ""); //<- Conexion a la base de datos de 2026TI32
            System.out.println("Conexion exitosa");

            PreparedStatement psNs = cnx.prepareStatement("SELECT MAX(id) FROM suscription"); //<- identifica el valor más alto de id
            ResultSet rsNs = psNs.executeQuery();

            //Se valida el numero de id para sumar uno y crear una matricula al usuario
            int sigid = 1;
            if (rsNs.next() && rsNs.getInt(1) > 0) {
                sigid = rsNs.getInt(1) + 1;
            }

            String NumSus = String.valueOf(1000 + sigid); //<- Creación de la matricula

            ps = cnx.prepareStatement("insert into suscription(nombre, apellido, edad, sexo, numsuscriptor) values(?,?,?,?,?)"); //<- agrega los valores en la tabla suscription

            ps.setString(1, this.txt_n.getText());
            ps.setString(2, this.txt_a.getText());
            ps.setString(3, this.txt_e.getText());
            ps.setString(4, this.txt_s.getText());
            ps.setString(5, NumSus);
            ps.execute();
            JOptionPane.showMessageDialog(rootPane, "registro guardado");
            this.txt_n.setText("");

        } catch (SQLException ex) {
            System.getLogger(Suscripcion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    //Consulta toda la base de datos
    public void Consultar() {
        try {
            Connection cnx;
            PreparedStatement ps;
            ResultSet rs;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            System.out.println("Conexion exitosa");
            ps = cnx.prepareStatement("select * from suscription");
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("id") + (" ") + rs.getString("nombre"));
            }

            
            //vaciar campo de texto
            this.txt_id.setText("");
            this.txt_n.setText("");
            this.txt_a.setText("");
            this.txt_e.setText("");
            this.txt_s.setText("");
            this.txt_ns.setText("");

            this.btn_sid.setEnabled(true);
            this.btn_agg.setEnabled(true);
            this.btn_list.setEnabled(true);
            this.btn_del.setEnabled(true);
            this.btn_sns.setEnabled(true);
            this.btn_edit.setEnabled(true);

            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error de conexion");
        }
    }

    public void BuscarID() {
        try {
            Connection cnx;
            PreparedStatement ps;
            ResultSet rs;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            System.out.println("Conexion exitosa");
            ps = cnx.prepareStatement("select * from suscription where id = ?");
            ps.setInt(1, Integer.parseInt(this.txt_id.getText()));
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("id") + (" ") + rs.getString("nombre"));
                this.txt_n.setText(rs.getString("nombre"));
                this.txt_a.setText(rs.getString("apellido"));
                this.txt_e.setText(rs.getString("edad"));
                this.txt_s.setText(rs.getString("sexo"));
                this.txt_ns.setText(rs.getString("numsuscriptor"));

            }

            //Deshabilitar botones despues de elegir id
            this.btn_sid.setEnabled(false);
            this.btn_agg.setEnabled(false);
            this.btn_list.setEnabled(true);
            this.btn_del.setEnabled(true);
            this.btn_sns.setEnabled(true);
            this.btn_edit.setEnabled(true);

            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error de conexion");
        }
    }

    public void BuscarNS() {
        try {
            Connection cnx;
            PreparedStatement ps;
            ResultSet rs;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            System.out.println("Conexion exitosa");
            ps = cnx.prepareStatement("select * from suscription where numsuscriptor = ?");
            ps.setInt(1, Integer.parseInt(this.txt_ns.getText()));
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("numsuscriptor") + (" ") + rs.getString("nombre"));
                this.txt_id.setText(rs.getString("id"));
                this.txt_n.setText(rs.getString("nombre"));
                this.txt_a.setText(rs.getString("apellido"));
                this.txt_e.setText(rs.getString("edad"));
                this.txt_s.setText(rs.getString("sexo"));
                this.txt_ns.setText(rs.getString("numsuscriptor"));

            }
            //Deshabilitar botones despues de elegir ns
            this.btn_sid.setEnabled(false);
            this.btn_agg.setEnabled(false);
            this.btn_list.setEnabled(true);
            this.btn_del.setEnabled(true);
            this.btn_sns.setEnabled(true);
            this.btn_edit.setEnabled(true);

            cnx.close();

        } catch (SQLException ex) {
            System.out.println("Error de conexion");
        }
    }

    public void Editar() {
        try {
            Connection cnx;
            PreparedStatement ps;
            cnx = DriverManager.getConnection("jdbc:mysql://127.0.0.1/2026TI32", "root", "");
            ps = cnx.prepareStatement("update suscription set nombre = ?, apellido = ?, edad = ?, sexo = ? where id = ?");
            ps.setString(1, this.txt_n.getText());
            ps.setString(2, this.txt_a.getText());
            ps.setString(3, this.txt_e.getText());
            ps.setString(4, this.txt_s.getText());
            ps.setInt(5, Integer.parseInt(this.txt_id.getText()));
            ps.executeUpdate();

            //habilitar botones
            this.btn_sid.setEnabled(true);
            this.btn_agg.setEnabled(true);
            this.btn_list.setEnabled(true);
            this.btn_del.setEnabled(true);
            this.btn_sns.setEnabled(true);
            this.btn_edit.setEnabled(true);

            this.Consultar();
            System.out.println("Conexion exitosa");
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }

    public void Eliminar() {
        try {
            Connection cnx;
            PreparedStatement ps;
            cnx = DriverManager.getConnection("jdbc:mysql://127.0.0.1/2026TI32", "root", "");
            ps = cnx.prepareStatement("DELETE FROM suscription WHERE id = ?");
            ps.setInt(1, Integer.parseInt(this.txt_id.getText()));
            ps.executeUpdate();

            this.Consultar();
            System.out.println("Conexion exitosa");
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }

    public void Return() {
        
        //vacia el texto y activa los botones desde el menu

        this.txt_id.setText("");
        this.txt_n.setText("");
        this.txt_a.setText("");
        this.txt_e.setText("");
        this.txt_s.setText("");
        this.txt_ns.setText("");

        this.btn_sid.setEnabled(true);
        this.btn_agg.setEnabled(true);
        this.btn_list.setEnabled(true);
        this.btn_del.setEnabled(true);
        this.btn_sns.setEnabled(true);
        this.btn_edit.setEnabled(true);

    }

    //metodo del menu superior siguiendo CRUD 
    public void menu() {
        //Regresar vuelve al inicio
        
        this.mb = new JMenuBar();
        this.setJMenuBar(mb);
        this.Regresar = new JMenu("Regresar");
        this.mb.add(this.Regresar);

        this.volver = new JMenuItem("Volver");
        this.volver.addActionListener((e) -> this.Return());
        this.Regresar.add(this.volver);

        this.DataBase = new JMenu("Base de datos");
        this.mb.add(DataBase);

        this.bd_c = new JMenuItem("C - Crear");
        this.bd_c.addActionListener((e) -> this.Agregar());
        this.DataBase.add(this.bd_c);

        this.bd_r = new JMenuItem("R - consultar");
        this.bd_r.addActionListener((e) -> this.Consultar());
        this.DataBase.add(this.bd_r);

        this.bd_u = new JMenuItem("U - Actualizar");
        this.bd_u.addActionListener((e) -> this.Editar());
        this.DataBase.add(this.bd_u);

        this.bd_d = new JMenuItem("D - Borrar");
        this.bd_d.addActionListener((e) -> this.Eliminar());
        this.DataBase.add(this.bd_d);

    }

    public static void main(String[] args) {
        new Suscripcion();
    }

}
