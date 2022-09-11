package attendance_structure;

import javafx.util.Pair;
import primary_classes.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private String name;
    private String code;
    private Teacher teacher;
    private String time;
    private String date;
    private int id;
    ArrayList<Student> students;

    public void fill_students(Course course){
        DBConnection connection = new DBConnection();
        try {
            setStudents(connection.get_course_students(course));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getTeacherName() {
        return teacher.getName();
    }

    public String getTime() {
        return time;
    }

    public Course(String name, String code, Teacher teacher, String time) {
        this.name = name;
        this.code = code;
        this.teacher = teacher;
        this.time = time;
    }

}
