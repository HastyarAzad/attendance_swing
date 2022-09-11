package Views;

import attendance_structure.Course;
import attendance_structure.Teacher;
import primary_classes.Main;


import javax.swing.*;
import java.awt.*;

// this is our teacher view panel
public class TeacherView extends JPanel{

    // the constructor accepts a teacher object so that the page gets constructed accordingly
    TeacherView(Teacher teacher){

        // setting the main's layout to gridLayout
        Main main = Main.main;
        main.getContentPane().setLayout(new GridLayout());

        setLayout(new BorderLayout());
        JPanel title_panel = new JPanel(new GridBagLayout());
        JLabel title = new JLabel("Classes");
        title_panel.add(title);

        // creating bottom panel and adding back button to it
        // setting it's size
        Dimension bottomPanelDimension = new Dimension(300,300);
        JPanel bottom_panel = new JPanel(new GridBagLayout());
        bottom_panel.setPreferredSize(bottomPanelDimension);
        bottom_panel.setMaximumSize(bottomPanelDimension);
        bottom_panel.setMinimumSize(bottomPanelDimension);

        Dimension buttonDimension = new Dimension(200,50);
        JButton back = new JButton("Back");
        back.setPreferredSize(buttonDimension);
        back.setMinimumSize(buttonDimension);
        back.setMaximumSize(buttonDimension);
        bottom_panel.add(back);

//        adding everything
        add(title_panel,BorderLayout.NORTH);
        add(create_Courses(teacher),BorderLayout.CENTER); // creating courses according to the teacher
        add(bottom_panel,BorderLayout.SOUTH);

        // returning to the previous view
        back.addActionListener(e ->{
            main.getContentPane().removeAll();
            main.getContentPane().setLayout(new GridBagLayout());
            main.add(new Login());
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });

    }

    // returns a panel containing all the courses of the teacher
    static JPanel create_Courses(Teacher teacher){

        // creating a panel
        JPanel all_courses_panel = new JPanel();
        all_courses_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 30));

        // filling the courseList of the teacher
        teacher.fill_course_list();

        // creating a course box for every course the teacher has
        for (Course course : teacher.courseList) {

            JPanel course_panel = new JPanel();
            course_panel.setPreferredSize(new Dimension(200,120));
            course_panel.setLayout(new BoxLayout(course_panel,BoxLayout.Y_AXIS));
            course_panel.setBorder(BorderFactory.createLineBorder(Color.blue,4,true));

            JLabel course_title = new JLabel(course.getName());
            course_title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel course_teacher = new JLabel(course.getTeacherName());
            course_teacher.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton view_button = new JButton("View");
            view_button.setAlignmentX(Component.CENTER_ALIGNMENT);

            // this is the view button of each course
            // it sends the course and teacher object to the Student_List view
            view_button.addActionListener(e -> {
                Main main = Main.main;
                main.getContentPane().removeAll();
                main.add(new Student_List(course,teacher));
                main.getContentPane().revalidate();
                main.getContentPane().repaint();
            });

            // adding everything
            course_panel.add(Box.createVerticalStrut(5));
            course_panel.add(course_title);
            course_panel.add(new JSeparator());
            course_panel.add(Box.createVerticalStrut(5));
            course_panel.add(course_teacher);
            course_panel.add(Box.createVerticalStrut(5));
            course_panel.add(new JSeparator());
            course_panel.add(view_button);
            course_panel.add(Box.createVerticalStrut(5));

            all_courses_panel.add(course_panel);
        }

        // returning the big panel
        return all_courses_panel;
    }

}
