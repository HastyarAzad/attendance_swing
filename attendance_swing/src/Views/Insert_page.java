package Views;

import primary_classes.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// this is our insert page  which belongs to the admin users
public class Insert_page extends JPanel {

//    this is the constructor of our class
    Insert_page() {
        Main main = Main.main;
        main.setLayout(new GridBagLayout());// setting the layout of our main frame to GridBagLayout

        setSize(new Dimension(900, 700));
        setLayout(new GridBagLayout());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width / 2) + 700 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        // creating a vertical box called B_box and adding top and bottom buttons to it along with the title label
        JPanel v_box = new JPanel();
        v_box.setLayout(new BoxLayout(v_box, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("What do you want to add??");
        title.setFont(new Font("Verdana", Font.PLAIN, 38));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        v_box.add(title);
        v_box.add(Box.createVerticalStrut(50));

        // this panel contains buttons which are responsible for adding a course, student and teacher
        JPanel first_row_buttons = new JPanel();
        first_row_buttons.setLayout(new BoxLayout(first_row_buttons, BoxLayout.X_AXIS));
        Dimension button_size = new Dimension(150, 40);

        Font font = new Font("SansSerif", Font.PLAIN, 20);
        JButton course_btn = new JButton("Course");
        course_btn.setFocusable(false);
        course_btn.setFont(font);
        course_btn.setPreferredSize(button_size);
        course_btn.setMaximumSize(button_size);
        course_btn.setMinimumSize(button_size);

        JButton student_btn = new JButton("Student");
        student_btn.setFocusable(false);
        student_btn.setFont(font);
        student_btn.setMinimumSize(button_size);
        student_btn.setMaximumSize(button_size);
        student_btn.setPreferredSize(button_size);

        JButton teacher_btn = new JButton("Teacher");
        teacher_btn.setFocusable(false);
        teacher_btn.setFont(font);
        teacher_btn.setMinimumSize(button_size);
        teacher_btn.setMaximumSize(button_size);
        teacher_btn.setPreferredSize(button_size);

        first_row_buttons.add(student_btn);
        first_row_buttons.add(Box.createHorizontalGlue());
        first_row_buttons.add(course_btn);
        first_row_buttons.add(Box.createHorizontalGlue());
        first_row_buttons.add(teacher_btn);

        // this panel contains back and register buttons
        JPanel second_row_buttons = new JPanel();
        second_row_buttons.setLayout(new BoxLayout(second_row_buttons, BoxLayout.X_AXIS));

        JButton back = new JButton("Back");
        back.setFocusable(false);
        back.setFont(font);
        back.setMinimumSize(button_size);
        back.setMaximumSize(button_size);
        back.setPreferredSize(button_size);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton register_student = new JButton("Register");
        register_student.setFocusable(false);
        register_student.setFont(font);
        register_student.setMinimumSize(button_size);
        register_student.setMaximumSize(button_size);
        register_student.setPreferredSize(button_size);
        register_student.setAlignmentX(Component.CENTER_ALIGNMENT);

        second_row_buttons.add(back);
        second_row_buttons.add(Box.createHorizontalStrut(20));
        second_row_buttons.add(register_student);

        v_box.add(first_row_buttons);
        v_box.add(Box.createVerticalStrut(20));
        v_box.add(second_row_buttons);

        add(v_box);

        // the student_btn directs us to the insert_members page and passes "student" as a parameter
        student_btn.addActionListener(e -> {
            main.getContentPane().removeAll();
            main.add(new Insert_members("student"));
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });

        // the course_btn  directs us to the Insert_Course page
        course_btn.addActionListener(e -> {
            main.getContentPane().removeAll();
            try {
                main.add(new Insert_Course());
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });

        // the student_btn directs us to the insert_members page and passes "teacher" as a parameter
        teacher_btn.addActionListener(e -> {
            main.getContentPane().removeAll();
            main.add(new Insert_members("teacher"));
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });

        // this button takes us back to the previous view
        back.addActionListener(e -> {
            main.getContentPane().removeAll();
            main.add(new Login());
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });

        // the register_student directs us to the Register_student page
        register_student.addActionListener(e -> {
            main.getContentPane().removeAll();
            main.add(new Register_student());
            main.getContentPane().revalidate();
            main.getContentPane().repaint();
        });
    }
}
