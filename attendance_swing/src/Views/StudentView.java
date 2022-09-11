package Views;

import attendance_structure.Student;
import primary_classes.Main;

import javax.swing.*;
import java.awt.*;

// this is our student view
public class StudentView extends JPanel {

// it accepts a student as a parameter so that the page gets constructed according to the student logged in
    StudentView(Student student){

        // filling the courseList of the student
        student.fill_course_list();

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        // creating the top panel which contains student name and the title
        JPanel top_panel = new JPanel();
        top_panel.setLayout(new BoxLayout(top_panel,BoxLayout.X_AXIS));

        JLabel checking_attendance = new JLabel("Checking Attendance");
        JLabel student_name = new JLabel(student.getName());

        top_panel.add(checking_attendance);
        top_panel.add(Box.createHorizontalGlue());
        top_panel.add(student_name);

        // creating the bottom panel and adding the back button to it
        JPanel bottom_panel = new JPanel(new GridBagLayout());
        JButton back = new JButton("Back");
        bottom_panel.add(back);

        // adding everything to the StudentView
        add(top_panel);
        add(Box.createVerticalStrut(30));
        add(new Student_table(student));  // adding a table and passing a student object to it's constructor
        add(Box.createVerticalStrut(30));
        add(bottom_panel);

        // adding actionListener to our button and going back to the previous view
        back.addActionListener(e ->{
            Main main = Main.main;
            main.getContentPane().removeAll();
            main.add(new Login());
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });
    }
}
