package attendance_structure;

import primary_classes.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends info {
// some special attributes of our student object
    int id;
    private ArrayList<Course> courseList;
    int attendance_rate;

    // the constructor
    public Student(String name, String email, String address, String code, String username, String password) {
        this.setName(name);
        this.setEmail(email);
        this.setAddress(address);
        this.setCode(code);
        this.setUsername(username);
        this.setPassword(password);
    }

    // this method fills the courseList of a student object
    public void fill_course_list() {
        DBConnection connection = new DBConnection();   //  creating a connection object
        try {
            this.courseList = connection.get_course_list(null, this);  // getting all the courses of the corresponding student from the database
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    // simple getters and setters
    public int getAttendance_rate() {
        return attendance_rate;
    }

    public void setAttendance_rate(int attendance_rate) {
        this.attendance_rate += attendance_rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

}
