package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();

        String sql =
                "SELECT student_id, name, major, email, phone "
                + "FROM new_student ORDER BY student_id";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getString("student_id"),
                        resultSet.getString("name"),
                        resultSet.getString("major"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                );

                students.add(student);
            }
        }

        return students;
    }

    public int insert(Student student) throws SQLException {
        String sql =
                "INSERT INTO new_student "
                + "(student_id, name, major, email, phone) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getMajor());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getPhone());

            return statement.executeUpdate();
        }
    }

    public int update(
            Student student,
            String originalStudentId) throws SQLException {
        String sql =
                "UPDATE new_student "
                + "SET student_id = ?, name = ?, major = ?, "
                + "email = ?, phone = ? "
                + "WHERE student_id = ?";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getMajor());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getPhone());
            statement.setString(6, originalStudentId);

            return statement.executeUpdate();
        }
    }
}