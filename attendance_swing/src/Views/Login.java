package Views;

import attendance_structure.*;
import primary_classes.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Login extends JPanel{


// this is our login view constructor
    public Login() {

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        Dimension uniform_dimension = new Dimension(100,30);

        // komar logo
        ImageIcon kust_logo = new ImageIcon("NewKomarLogo.png");
        Image temp_image = kust_logo.getImage();
        Image new_image = temp_image.getScaledInstance(225, 225, java.awt.Image.SCALE_SMOOTH);
        kust_logo = new ImageIcon(new_image);
        JLabel image = new JLabel(kust_logo);

        //creating a panel for username which contains a label, and a textField
        JPanel user_name_panel = new JPanel();
        JLabel user_name_lbl = new JLabel("Username: ");
        user_name_lbl.setPreferredSize(uniform_dimension);
        JTextField user_name_txtField = new JTextField();
        user_name_txtField.setPreferredSize(uniform_dimension);
        user_name_panel.add(user_name_lbl);
        user_name_panel.add(user_name_txtField);

        //creating a panel for password which contains a label, and a passwordField
        JPanel password_panel = new JPanel();
        JLabel password_lbl = new JLabel("Password: ");
        password_lbl.setPreferredSize(uniform_dimension);
        JPasswordField password_txtField = new JPasswordField();
        password_txtField.setPreferredSize(uniform_dimension);
        password_panel.add(password_lbl);
        password_panel.add(password_txtField);

        // creating buttons panel
        JPanel button_panel = new JPanel();
        JButton login_btn = new JButton("Login");
        login_btn.setPreferredSize(uniform_dimension);
        button_panel.add(login_btn);

        // adding actionListener to our button
        login_btn.addActionListener(e -> {

            String username = user_name_txtField.getText();    //getting username
            String password = String.valueOf(password_txtField.getPassword());     // getting password

            ArrayList<Student> student_users = Main_work.student_users;
            ArrayList<Teacher> teacher_users = Main_work.teacher_users;
            ArrayList<Admin> admin_users = Main_work.admin_users;

            // looping through all the students
            for (Student student : student_users) {
                if(student.getName().equals(username) && student.getPassword().equals(password)){  // checking if the username is a student
                    login_successful(new StudentView(student));
                }
            }

            // looping through all the teachers
            for (Teacher teacher : teacher_users) {
                if(teacher.getUsername().equals(username) && teacher.getPassword().equals(password)){  //checking if the username is a teacher
                    login_successful(new TeacherView(teacher));
                }
            }

            // looping through all the admins
            for (Admin admin_user : admin_users) {
                if(admin_user.getUsername().equals(username) && admin_user.getPassword().equals(password)){   // checking if the username is an admin
                    login_successful(new Insert_page());
                }
            }

        });

        // adding everything
        add(image);
        add(Box.createVerticalStrut(50));
        add(user_name_panel);
        add(password_panel);
        add(Box.createVerticalStrut(30));
        add(button_panel);

    }

    //  adding a view according to the user
    // removing everything inside our main frame and adding new items to it along with revalidating and repainting it
    void login_successful(JPanel view){
        Main main = Main.main;
        main.getContentPane().removeAll();
        main.add(view);
        main.getContentPane().revalidate();
        main.getContentPane().repaint();

    }
}
