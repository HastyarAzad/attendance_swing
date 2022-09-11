package primary_classes;

import Views.Login;
import attendance_structure.Main_work;

import javax.swing.*;
import java.awt.*;

// this is our main class from where our program starts

public class Main extends JFrame {

    // this is our main frame, we will call it and reset its components when switching from one window to an other
    public static Main main = new Main();

    // the constructor of our Main
    Main(){
        setSize(new Dimension(700,700));
        getContentPane().setLayout(new GridBagLayout());
        setTitle("KUST Attendance Management System");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        add(new Login()); // adding new Login() instance to our main frame
    }

    public static void main(String[] args) {
        //setting its visibility and close operations
        main.setVisible(true);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // getting all users (teachers, students, admins) into our program and storing them inside arrayLists
        Main_work.start();
    }
}
