package attendance_structure;

import javafx.util.Pair;
import primary_classes.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Teacher extends info {

    public ArrayList<Course> courseList = new ArrayList<>();
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teacher(String name, String email, String address, String code, String username, String password) {
        this.setName(name);
        this.setEmail(email);
        this.setAddress(address);
        this.setCode(code);
        this.setUsername(username);
        this.setPassword(password);
        this.setId(0);
    }

    public void fill_course_list(){
        DBConnection connection = new DBConnection();
        try {
            this.courseList = connection.get_course_list(this, null);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}
