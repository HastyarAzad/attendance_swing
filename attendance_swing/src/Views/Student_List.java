package Views;

import attendance_structure.Course;
import attendance_structure.Student;
import attendance_structure.Teacher;
import primary_classes.DBConnection;
import primary_classes.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student_List extends JPanel{

    // we are storing the students of the current course and later on updating the database based on this arrayList
    static ArrayList<Student> attendance_values = new ArrayList<>();

    // this is the constructor of our class which accepts a course and a student object
    Student_List(Course course, Teacher teacher){

        // we are filling all the students for the course object
        course.fill_students(course);
        setLayout(new BorderLayout());

        // we have the top panel which contains the class name, time, date
        JPanel top_panel = new JPanel();
        top_panel.setLayout(new BoxLayout(top_panel,BoxLayout.X_AXIS));

        JLabel class_name = new JLabel(course.getName());
        JLabel time = new JLabel(course.getTime());
        JLabel date = new JLabel(course.getDate());

        top_panel.add(Box.createRigidArea(new Dimension(30,30)));
        top_panel.add(class_name);
        top_panel.add(Box.createHorizontalGlue());
        top_panel.add(time);
        top_panel.add(Box.createHorizontalGlue());
        top_panel.add(date);

        Dimension uniform_dimension = new Dimension(150,50);

        // this is the center panel which contains a panel containing all the students that have enrolled for this course
        // each student has three radio buttons which indicates their absence value
        JPanel center_panel = create_fields(course.getStudents());

        // this is our bottom panel containing our back button, warning label and submit button
        JPanel bottom_panel = new JPanel();
        bottom_panel.setLayout(new BoxLayout(bottom_panel, BoxLayout.X_AXIS));
        JButton submit = new JButton("Submit");
        submit.setPreferredSize(uniform_dimension);
        JButton back = new JButton("Back");
        back.setPreferredSize(uniform_dimension);
        JLabel warning_label = new JLabel("");

        bottom_panel.add(Box.createHorizontalGlue());
        bottom_panel.add(Box.createRigidArea(new Dimension(1,100)));
        bottom_panel.add(back);
        bottom_panel.add(Box.createHorizontalGlue());
        bottom_panel.add(warning_label);
        bottom_panel.add(Box.createHorizontalGlue());
        bottom_panel.add(submit);
        bottom_panel.add(Box.createHorizontalGlue());

        // adding everything together
        add(top_panel,BorderLayout.NORTH);
        add(center_panel,BorderLayout.CENTER);
        add(bottom_panel,BorderLayout.SOUTH);

        // returning to the previous view
        back.addActionListener(e -> {
            Main main = Main.main;
            main.getContentPane().removeAll();
            main.add(new TeacherView(teacher));
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });

        // this is the actionlistener for the submit button
        // it sends the attendance value arrayList and the course object to the database to be updated
        submit.addActionListener(e -> {
            if (attendance_values.size() == course.getStudents().size()){
                DBConnection connection = new DBConnection();
                try {
                    connection.submit_attendance(attendance_values,course);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                warning_label.setText("Submitted successfully");// setting the label's text
            }else{
                warning_label.setText("Please check all the students");// setting the label's text
            }
        });

    }

    // creating a panel for every student along with three radio buttons
    JPanel create_fields(ArrayList<Student> students){

        JPanel student_list_panel = new JPanel();
        student_list_panel.setLayout(new BoxLayout(student_list_panel,BoxLayout.Y_AXIS));
        student_list_panel.add(Box.createVerticalStrut(50));

        // looping through the arrayList
        for (Student student : students) {
            student.fill_course_list();// filling a student's course list

            JPanel student_panel = new JPanel();// this is the student panel
            student_panel.setLayout(new BoxLayout(student_panel,BoxLayout.X_AXIS));
            student_panel.setPreferredSize(new Dimension(700,20));
            student_panel.setMaximumSize(new Dimension(700,20));
            student_panel.setMinimumSize(new Dimension(700,20));

            JLabel student_name = new JLabel(student.getName());
            student_name.setPreferredSize(new Dimension(100,10));
            student_name.setMaximumSize(new Dimension(100,10));
            student_name.setMinimumSize(new Dimension(100,10));

            JRadioButton present = new JRadioButton("Present");

            // adding action listeners to each radio button
            present.addActionListener(e -> {
                if (present.isSelected()){
                    store_attendance_values(student, 0);// calling store attendance values method
                }
            });

            JRadioButton absent = new JRadioButton("Absent");

            absent.addActionListener(e -> {
                if (absent.isSelected()){
                    store_attendance_values(student, 3);// calling store attendance values method
                }
            });

            JRadioButton late = new JRadioButton("Late");

            late.addActionListener(e ->{
                if (late.isSelected()){
                    store_attendance_values(student, 1);// calling store attendance values method
                }

            });

            // adding everything together
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(present);
            buttonGroup.add(absent);
            buttonGroup.add(late);

            student_panel.add(Box.createHorizontalGlue());
            student_panel.add(student_name);
            student_panel.add(Box.createHorizontalStrut(20));
            student_panel.add(present);
            student_panel.add(Box.createHorizontalStrut(20));
            student_panel.add(absent);
            student_panel.add(Box.createHorizontalStrut(20));
            student_panel.add(late);
            student_panel.add(Box.createHorizontalGlue());


            student_list_panel.add(student_panel);
        }
        student_list_panel.add(Box.createVerticalGlue());// pushing all the panels upward
        return student_list_panel;// returning the panel
    }


    // this method adds a student and it's corresponding attendance value to the arrayList
    public void store_attendance_values(Student student, int value){
        if(attendance_values.contains(student)){  //checking if its already there
            attendance_values.get(attendance_values.indexOf(student)).setAttendance_rate(value);
        }else{
            student.setAttendance_rate(value);
            attendance_values.add(student);
        }
    }

}
