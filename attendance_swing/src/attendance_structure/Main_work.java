package attendance_structure;

import primary_classes.DBConnection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main_work {

    // we are getting all the users and storing them inside arrayLists
    static public ArrayList<Student> student_users = new ArrayList<>();
    static public ArrayList<Teacher> teacher_users = new ArrayList<>();
    static public ArrayList<Admin> admin_users = new ArrayList<>();

    public static void start() {

        // creating a connection and getting all the values from the database
        DBConnection connection = new DBConnection();
        try {
            teacher_users = connection.get_teachers();
            student_users = connection.get_Students();
            admin_users = connection.get_admins();
            connection.getConn().close(); // closing the connection
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


}
