package Views;

import attendance_structure.Course;
import attendance_structure.Main_work;
import attendance_structure.Student;
import attendance_structure.Teacher;
import primary_classes.DBConnection;
import primary_classes.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Register_student extends JPanel {

    // standard label dimension
    static Dimension lbl_dimension = new Dimension(100,20);

    Register_student(){

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JPanel students_panel = new JPanel();// student panel
        students_panel.setLayout(new BoxLayout(students_panel,BoxLayout.X_AXIS));
        JLabel student_lbl = new JLabel("Student");
        student_lbl.setPreferredSize(lbl_dimension);
        student_lbl.setMaximumSize(lbl_dimension);
        student_lbl.setMinimumSize(lbl_dimension);

        JComboBox students = new JComboBox();// a combobox contains all the available students
        students.addItem("");
        fill_students(students);// filling up the students

        students_panel.add(student_lbl);
        students_panel.add(students);
        students_panel.add(Box.createHorizontalGlue());

        JPanel course_panel = new JPanel();// course panel
        course_panel.setLayout(new BoxLayout(course_panel,BoxLayout.X_AXIS));
        JLabel course_lbl = new JLabel("Course");
        course_lbl.setPreferredSize(lbl_dimension);
        course_lbl.setMaximumSize(lbl_dimension);
        course_lbl.setMinimumSize(lbl_dimension);

        JComboBox courses = new JComboBox();// a combobox containing all the available courses
        courses.addItem("");
        fill_courses(courses);// filling up the courses

        course_panel.add(course_lbl);
        course_panel.add(courses);
        course_panel.add(Box.createHorizontalGlue());

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
        add(students_panel);
        add(Box.createVerticalStrut(20));
        add(course_panel);
        add(Box.createVerticalStrut(20));
        add(buttons_panel);

        // this takes us back to the previous panel
        back.addActionListener(e -> {
            Main main = Main.main;
            main.getContentPane().removeAll();
            main.add(new Insert_page());
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });

        // this is where we register a student to a course
        insert.addActionListener(e -> {

            // checking if both are selected
            if(!( Objects.requireNonNull(courses.getSelectedItem()).toString().equals("") || Objects.requireNonNull(students.getSelectedItem()).toString().equals("") )){

                int course_id = 0;
                int student_id = 0;

                // looping through all the students
                for (Student student : Main_work.student_users) {
                    if(student.getName().equals(students.getSelectedItem().toString())){
                        student_id = student.getId();
                    }
                }

                try {
                    DBConnection connection = new DBConnection();
//                    looping through all the courses
                    for (Course course : connection.get_courses()) {
                        if(course.getName().equals(courses.getSelectedItem().toString())){
                            course_id = course.getId();
                        }
                    }

                    connection.register_student(student_id,course_id);// sending student_id and course_id to the register_student method
                    connection.getConn().close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }else{
                warning_lbl.setText("Please fill in all the fields");
            }
        });
    }

    // filling all the students for the combobox
    static void fill_students(JComboBox teachers){
        DBConnection connection = new DBConnection();
        ArrayList<Student> students = new ArrayList<>();
        try {
            students = connection.get_Students();
            connection.getConn().close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        for (Student student : students) {
            teachers.addItem(student.getName());
        }
    }

    // filling all the courses for the combobox
    static void fill_courses(JComboBox courses){
        DBConnection connection = new DBConnection();
        ArrayList<Course> courses_list = new ArrayList<>();
        try {
            courses_list = connection.get_courses();
            connection.getConn().close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        for (Course course : courses_list) {
            courses.addItem(course.getName());
        }
    }
}
