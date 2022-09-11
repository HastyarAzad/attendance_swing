package primary_classes;

import attendance_structure.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class DBConnection {

// setting the database location and it's username and password
    String url = "jdbc:mysql://localhost:3306/attendance_system";
    String username = "root";
    String password = "";

    Connection conn; // a connection object for every DBConnectino object

    public DBConnection() {
        try {
            // loading the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url,username,password); // making a connection through the driver
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // some getters and setters
    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    //    getting all the admins and returning an arraylist of that type
    public ArrayList<Admin> get_admins() throws SQLException {

        String query = "select * from admin";
        ResultSet resultSet = conn.createStatement().executeQuery(query);
        ArrayList<Admin> admins = new ArrayList<>();

        while(resultSet.next()){
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            String code = resultSet.getString("code");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            admins.add(new Admin(name,email,address,code,username,password));
        }
        return admins;
    }

    //  getting all the courses of either a student or a teacher and returning it inside an arraylist
    public ArrayList<Course> get_course_list(Teacher teacher,Student student) throws SQLException {

        ArrayList<Course> courses = new ArrayList<>();
        String query = null;

        if(Objects.isNull(teacher)){
            query = "select course_id from student_course_attendance where student_id = "+student.getId()+";";
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while(resultSet.next()){
                int id = resultSet.getInt("course_id");
                courses.add(get_course(id));
            }

        }else if(Objects.isNull(student)){
            query = "select * from course where teacher_id = "+teacher.getId()+";";
            ResultSet resultSet = conn.createStatement().executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String code = resultSet.getString("code");
//            String teacher = resultSet.getString("teacher");
                String time = resultSet.getString("time");
                Course course = new Course(name,code,teacher,time);
                course.setId(id);
                courses.add(course);
            }
        }

        return courses;
    }

    // getting a simple course through its id
    public Course get_course(int id) throws SQLException {
        Course course = null;
        Teacher teacher = null;
        String query = "select * from course where id = "+id+";";
        ResultSet resultSet = conn.createStatement().executeQuery(query);

        while(resultSet.next()) {
            String name = resultSet.getString("name");
            String code = resultSet.getString("code");
            String time = resultSet.getString("time");
            int course_id = resultSet.getInt("id");
            for (Teacher teacher_user : Main_work.teacher_users) {
                if(teacher_user.getId() == resultSet.getInt("id")){
                    teacher = teacher_user;
                }
            }
            course = new Course(name,code,teacher,time);
            course.setId(course_id);
        }

        return course;
    }

    // getting all available courses
    public ArrayList<Course> get_courses() throws SQLException {
        Course course = null;
        Teacher teacher = null;
        String query = "select * from course ;";

        ResultSet resultSet = conn.createStatement().executeQuery(query);
        ArrayList<Course> courses= new ArrayList<>();

        while(resultSet.next()) {
            String name = resultSet.getString("name");
            String code = resultSet.getString("code");
            String time = resultSet.getString("time");
            int course_id = resultSet.getInt("id");
            for (Teacher teacher_user : Main_work.teacher_users) {
                if(teacher_user.getId() == resultSet.getInt("id")){
                    teacher = teacher_user;
                }
            }
            course = new Course(name,code,teacher,time);
            course.setId(course_id);
            courses.add(course);
        }

        return courses;
    }

    // inserting a course object into the database
    public void insert_course(Course course) throws SQLException {

        String query="insert into course (id, name, code, teacher_id, time) values (?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, null);
        ps.setString(2, course.getName());
        ps.setString(3, course.getCode());
        ps.setInt(4, course.getTeacher().getId());
        ps.setString(5, course.getTime());

        int i = ps.executeUpdate();
        System.out.println("no. of rows updated ="+i);
        ps.close();

    }

    // getting all the teachers and returning an arrayList of student type
    public ArrayList<Teacher> get_teachers() throws SQLException {

        String query = "select * from teacher";
        ResultSet resultSet = conn.createStatement().executeQuery(query);
        ArrayList<Teacher> teachers = new ArrayList<>();

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            String code = resultSet.getString("code");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            Teacher teacher = new Teacher(name,email,address,code,username,password);
            teachers.add(teacher);
            teacher.setId(id);
        }
        return teachers;
    }

    public Teacher get_teacher(String teacher_name) throws SQLException {
        String query = "select * from teacher where name = \'"+teacher_name+"\';";
        ResultSet resultSet = conn.createStatement().executeQuery(query);
        Teacher teacher = null;
        int id;

        while(resultSet.next()){
            id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            String code = resultSet.getString("code");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            teacher = new Teacher(name,email,address,code,username,password);
            teacher.setId(id);
        }
        return teacher;
    }

    //  inserting a teacher object
    public void insert_teacher(Teacher teacher) throws SQLException {
        String query="insert into teacher (id, name, email, address, code, username, password)values(?,?,?,?,?,?,?)";
        insert(query, teacher.getName(), teacher.getEmail(), teacher.getAddress(), teacher.getCode(), teacher.getUsername(), teacher.getPassword());
    }

//    this helps insert_student and insert_teacher methods
    private void insert(String query, String name, String email, String address, String code, String username, String password) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, null);
        ps.setString(2, name);
        ps.setString(3, email);
        ps.setString(4, address);
        ps.setString(5, code);
        ps.setString(6, username);
        ps.setString(7, password);
        int i = ps.executeUpdate();
        System.out.println("no. of rows updated ="+i);
        ps.close();
    }

    // inserting a student object
    public void insert_student(Student student) throws SQLException {
        String query="insert into student (id, name, email, address, code, username, password)values(?,?,?,?,?,?,?)";
        insert(query, student.getName(), student.getEmail(), student.getAddress(), student.getCode(), student.getUsername(), student.getPassword());
    }

    // getting all the students
    public ArrayList<Student> get_Students() throws SQLException {

        String query = "select * from student";
        ResultSet resultSet = conn.createStatement().executeQuery(query);
        ArrayList<Student> students = new ArrayList<>();

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            String code = resultSet.getString("code");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            Student student = new Student(name,email,address,code,username,password);
            student.setId(id);
            students.add(student);

        }
        return students;
    }

    // getting all the students of a course object
    public ArrayList<Student> get_course_students(Course course) throws SQLException {
        String query = "select * from student_course_attendance where course_id = "+course.getId()+";";
        ResultSet resultSet = conn.createStatement().executeQuery(query);
        ArrayList<Student> students = new ArrayList<>();
        while(resultSet.next()){
            int id = resultSet.getInt("student_id");
            Student student = get_Student(id);
            student.setId(id);
            students.add(student);
        }
        return students;
    }

    // getting a student through its id
    public Student get_Student(int id) throws SQLException {
        Student student = null;
        String query = "select * from student where id = "+id+";";
        ResultSet resultSet = conn.createStatement().executeQuery(query);
        while(resultSet.next()){
            int std_id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            String code = resultSet.getString("code");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            student = new Student(name,email,address,code,username,password);
            student.setId(std_id);
        }
        return student;
    }

    // getting attendance rate of a student for a particular course
    public int get_attendance_rate(Student student, Course course) throws SQLException {
        String query = "select * from student_course_attendance where student_id = "+student.getId()+" and course_id = "+course.getId()+";";
        ResultSet resultSet = conn.createStatement().executeQuery(query);

        int attendance_rate = 0;

        while(resultSet.next()){
            attendance_rate = resultSet.getInt("attendance_value");
            System.out.println(attendance_rate);
        }

        return attendance_rate;
    }

    // submitting attendance for a course object
    public void submit_attendance(ArrayList<Student> attendance_values,Course course) throws SQLException {
        for (Student student : attendance_values) {
            String query = "UPDATE student_course_attendance SET attendance_value = '"+student.getAttendance_rate()+"' WHERE student_id = "+student.getId()+" and course_id = "+course.getId()+";";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            System.out.println(preparedStatement.executeUpdate());
        }
    }

    // registering a student for a course
    public void register_student(int student_id,int course_id) throws SQLException {
        String query = "insert into student_course_attendance (student_id, course_id, attendance_value) values ('"+student_id+"','"+course_id+"','0');";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        System.out.println(preparedStatement.executeUpdate());
        preparedStatement.close();
    }

}
