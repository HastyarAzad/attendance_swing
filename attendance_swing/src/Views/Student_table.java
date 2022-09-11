package Views;

import attendance_structure.Course;
import attendance_structure.Student;
import primary_classes.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student_table extends JPanel {
    static DefaultTableModel model = new DefaultTableModel();
    static JTable table;

    Student_table(Student student) {

        ArrayList<Course> courses = student.getCourseList();

        //setting the layout
        setLayout(new GridLayout());

        // instantiating  the table and setting it's grid color

        table = new JTable();
        table.setGridColor(Color.black);
        JScrollPane pane = new JScrollPane(table);


        // create a table model and set a Column Identifiers to this model
        Object[] columns = { "Course name", "time","Date","Attendance Rate"};

        model.setColumnIdentifiers(columns);

        // set the model to the table
        table.setModel(model);

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        table.setBackground(Color.white);
        table.setForeground(Color.black);
        Font font = new Font("SansSerif", Font.PLAIN, 14);
        table.setFont(font);
        table.setRowHeight(30);

        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setIntercellSpacing(new Dimension(20,0));
        table.setPreferredScrollableViewportSize(new Dimension(600,400));

        model.setRowCount(0);
        for (Course course : courses) {
            DBConnection connection = new DBConnection();

            Object[] row = new Object[5];
            row[0] = course.getName();
            row[1] = course.getTime();
//            row[2] = course.get;
            try {
                row[3] = connection.get_attendance_rate(student,course);
            } catch (SQLException ignored) {}
            model.addRow(row);
        }

        add(pane);

    }
}