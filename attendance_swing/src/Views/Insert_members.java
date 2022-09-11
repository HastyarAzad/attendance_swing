package Views;

import attendance_structure.Student;
import attendance_structure.Teacher;
import primary_classes.DBConnection;
import primary_classes.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Insert_members extends JPanel {

    static Dimension lbl_dimension = new Dimension(100,20);
    static Dimension txt_field_dimension = new Dimension(300,30);

    //    this is the constructor of our class
    Insert_members(String type){

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        Main main = Main.main;
        main.setLayout(new GridBagLayout());// setting the layout of our main frame to GridBagLayout

        JPanel name_panel = new JPanel();// this is the name panel

        name_panel.setLayout(new BoxLayout(name_panel,BoxLayout.X_AXIS));
        JLabel name_lbl = new JLabel("Name: ");
        name_lbl.setPreferredSize(lbl_dimension);
        name_lbl.setMinimumSize(lbl_dimension);
        name_lbl.setMaximumSize(lbl_dimension);

        JTextField name_txt = new JTextField();
        name_txt.setPreferredSize(txt_field_dimension);
        name_txt.setMaximumSize(txt_field_dimension);
        name_txt.setMinimumSize(txt_field_dimension);

        name_panel.add(name_lbl);
        name_panel.add(name_txt);
        name_panel.add(Box.createHorizontalStrut(20));

        JPanel email_panel = new JPanel();// this is the email panel

        email_panel.setLayout(new BoxLayout(email_panel,BoxLayout.X_AXIS));
        JLabel email_lbl = new JLabel("Email: ");
        email_lbl.setPreferredSize(lbl_dimension);
        email_lbl.setMinimumSize(lbl_dimension);
        email_lbl.setMaximumSize(lbl_dimension);

        JTextField email_txt = new JTextField();
        email_txt.setPreferredSize(txt_field_dimension);
        email_txt.setMaximumSize(txt_field_dimension);
        email_txt.setMinimumSize(txt_field_dimension);


        email_panel.add(email_lbl);
        email_panel.add(email_txt);
        email_panel.add(Box.createHorizontalStrut(20));

        JPanel address_panel = new JPanel();// this is the address panel

        address_panel.setLayout(new BoxLayout(address_panel,BoxLayout.X_AXIS));
        JLabel address_lbl = new JLabel("Address: ");
        address_lbl.setPreferredSize(lbl_dimension);
        address_lbl.setMinimumSize(lbl_dimension);
        address_lbl.setMaximumSize(lbl_dimension);

        JTextField address_txt = new JTextField();
        address_txt.setPreferredSize(txt_field_dimension);
        address_txt.setMaximumSize(txt_field_dimension);
        address_txt.setMinimumSize(txt_field_dimension);


        address_panel.add(address_lbl);
        address_panel.add(address_txt);
        address_panel.add(Box.createHorizontalStrut(20));

        JPanel code_panel = new JPanel();// this is the code panel

        code_panel.setLayout(new BoxLayout(code_panel,BoxLayout.X_AXIS));
        JLabel code_lbl = new JLabel("Code: ");
        code_lbl.setPreferredSize(lbl_dimension);
        code_lbl.setMinimumSize(lbl_dimension);
        code_lbl.setMaximumSize(lbl_dimension);

        JTextField code_txt = new JTextField();
        code_txt.setPreferredSize(txt_field_dimension);
        code_txt.setMaximumSize(txt_field_dimension);
        code_txt.setMinimumSize(txt_field_dimension);


        code_panel.add(code_lbl);
        code_panel.add(code_txt);
        code_panel.add(Box.createHorizontalStrut(20));

        JPanel username_panel = new JPanel();// this is the username panel

        username_panel.setLayout(new BoxLayout(username_panel,BoxLayout.X_AXIS));
        JLabel username_lbl = new JLabel("Username: ");
        username_lbl.setPreferredSize(lbl_dimension);
        username_lbl.setMinimumSize(lbl_dimension);
        username_lbl.setMaximumSize(lbl_dimension);

        JTextField username_txt = new JTextField();
        username_txt.setPreferredSize(txt_field_dimension);
        username_txt.setMaximumSize(txt_field_dimension);
        username_txt.setMinimumSize(txt_field_dimension);


        username_panel.add(username_lbl);
        username_panel.add(username_txt);
        username_panel.add(Box.createHorizontalStrut(20));

        JPanel password_panel = new JPanel();// this is the password panel

        password_panel.setLayout(new BoxLayout(password_panel,BoxLayout.X_AXIS));
        JLabel password_lbl = new JLabel("Password: ");
        password_lbl.setPreferredSize(lbl_dimension);
        password_lbl.setMinimumSize(lbl_dimension);
        password_lbl.setMaximumSize(lbl_dimension);

        JTextField password_txt = new JTextField();
        password_txt.setPreferredSize(txt_field_dimension);
        password_txt.setMaximumSize(txt_field_dimension);
        password_txt.setMinimumSize(txt_field_dimension);

        password_panel.add(password_lbl);
        password_panel.add(password_txt);
        password_panel.add(Box.createHorizontalStrut(20));

        JPanel buttons_panel = new JPanel();// this is teh buttons panel
        buttons_panel.setLayout(new BoxLayout(buttons_panel, BoxLayout.X_AXIS));
        JButton back = new JButton("Back");
        JButton insert = new JButton("Insert");
        JLabel warning_lbl = new JLabel();
        warning_lbl.setFont(new Font("SanSerif",Font.BOLD,20));
        warning_lbl.setForeground(Color.red);

        buttons_panel.add(back);
        buttons_panel.add(Box.createHorizontalGlue());
        buttons_panel.add(warning_lbl);
        buttons_panel.add(Box.createHorizontalGlue());
        buttons_panel.add(insert);

        // adding up everything
        add(name_panel);
        add(Box.createVerticalStrut(20));
        add(email_panel);
        add(Box.createVerticalStrut(20));
        add(address_panel);
        add(Box.createVerticalStrut(20));
        add(code_panel);
        add(Box.createVerticalStrut(20));
        add(username_panel);
        add(Box.createVerticalStrut(20));
        add(password_panel);
        add(Box.createVerticalStrut(20));
        add(buttons_panel);

        // takes us to the previous panel
        back.addActionListener(e -> {
            main.getContentPane().removeAll();
            main.add(new Insert_page());
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });

        // inserts a new member whether it's  a student or a teacher
        insert.addActionListener(e -> {
            // checks if all the fields are filled up
            if(!(name_txt.getText().equals("") || email_txt.getText().equals("") || address_txt.getText().equals("") || code_txt.getText().equals("") || username_txt.getText().equals("") || password_txt.getText().equals(""))){
                DBConnection connection = new DBConnection();

                // checks for the type of the object
                if(type.equals("student")){
                    Student student = new Student(name_txt.getText(),email_txt.getText(),address_txt.getText(),code_txt.getText(),username_txt.getText(),password_txt.getText());
                    try {
                        connection.insert_student(student);
                        connection.getConn().close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else if(type.equals("teacher")){
                    Teacher teacher = new Teacher(name_txt.getText(),email_txt.getText(),address_txt.getText(),code_txt.getText(),username_txt.getText(),password_txt.getText());
                    try {
                        connection.insert_teacher(teacher);
                        connection.getConn().close();
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                    }
                }
            }else{
                warning_lbl.setText("Please fill in all the fields");
            }


        });
    }
}
