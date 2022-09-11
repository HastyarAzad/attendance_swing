package Views;

import attendance_structure.Course;
import attendance_structure.Teacher;
import javafx.embed.swing.JFXPanel;
import primary_classes.DBConnection;
import primary_classes.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

// this is where we create a course and insert it to the database using a DBConnection object
public class Insert_Course extends JFXPanel {

    // some dimensions
    static Dimension lbl_dimension = new Dimension(100,20);
    static Dimension txt_field_dimension = new Dimension(300,30);

    // this is insert course constructor
    Insert_Course() throws SQLException {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        Main main = Main.main;
        main.setLayout(new GridBagLayout());// setting the layout of the main frame to GridBagLayout

        JPanel name_panel = new JPanel();// this is our name panel contains name label and textField

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

        JPanel time_panel = new JPanel();// time panel

        time_panel.setLayout(new BoxLayout(time_panel,BoxLayout.X_AXIS));
        JLabel time_lbl = new JLabel("Time: ");
        time_lbl.setPreferredSize(lbl_dimension);
        time_lbl.setMinimumSize(lbl_dimension);
        time_lbl.setMaximumSize(lbl_dimension);

        JTextField time_txt = new JTextField();
        time_txt.setPreferredSize(txt_field_dimension);
        time_txt.setMaximumSize(txt_field_dimension);
        time_txt.setMinimumSize(txt_field_dimension);

        time_panel.add(time_lbl);
        time_panel.add(time_txt);
        time_panel.add(Box.createHorizontalStrut(20));

        JPanel code_panel = new JPanel();// code panel

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

        JPanel teacher_panel = new JPanel();// teacher panel contains all the teachers
        teacher_panel.setLayout(new BoxLayout(teacher_panel,BoxLayout.X_AXIS));
        JLabel teacher_lbl = new JLabel("Teacher");
        teacher_lbl.setPreferredSize(lbl_dimension);
        teacher_lbl.setMaximumSize(lbl_dimension);
        teacher_lbl.setMinimumSize(lbl_dimension);

        JComboBox teachers = new JComboBox();
        fill_teachers(teachers);// all the teachers are added to teachers which are brought from the database

        teacher_panel.add(teacher_lbl);
        teacher_panel.add(teachers);
        teacher_panel.add(Box.createHorizontalGlue());


        JPanel buttons_panel = new JPanel();// buttons panel
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

        // adding everything
        add(name_panel);
        add(Box.createVerticalStrut(20));
        add(teacher_panel);
        add(Box.createVerticalStrut(20));
        add(time_panel);
        add(Box.createVerticalStrut(20));
        add(code_panel);
        add(Box.createVerticalStrut(20));
        add(buttons_panel);

        //  takes us back to previous view
        back.addActionListener(e -> {
            main.getContentPane().removeAll();
            main.add(new Insert_page());
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });

        // inserts a new course Object to the database
        insert.addActionListener(e -> {
            // checks if everything is filled up
            if(!(name_txt.getText().equals("") || code_txt.getText().equals("") || Objects.requireNonNull(teachers.getSelectedItem()).toString().equals(""))){

                try {
                    DBConnection connection = new DBConnection();
                    Teacher teacher = connection.get_teacher(teachers.getSelectedItem().toString());

                    Course course = new Course(name_txt.getText(),code_txt.getText(),teacher,time_txt.getText());
                    connection.insert_course(course);
                    connection.getConn().close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }else{
                warning_lbl.setText("Please fill in all the fields");
            }
        });
    }

//    filling the teachers inside the comboBox
    static void fill_teachers(JComboBox teachers) throws SQLException {
        DBConnection connection = new DBConnection();
        ArrayList<Teacher> teachers_list = connection.get_teachers();// getting all the available teachers
        connection.getConn().close();
        for (Teacher teacher : teachers_list) {
            teachers.addItem(teacher.getName());// simply adding
        }
    }
}
