package Java.dao;

import Java.pojo.Student;
import Java.pojo.User;
import Java.utils.StudentInformationUi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentInformation {
    public StudentInformation() throws SQLException {
        String SQL="select * from userinformation where ID=?";
        PreparedStatement preparedStatement=SQL_Readin.con.prepareStatement(SQL);
        preparedStatement.setString(1, User.num);
        ResultSet resultSet=preparedStatement.executeQuery();
        Student student=new Student();
        while(resultSet.next()){
            student.setNum(resultSet.getString(1));
            student.setName(resultSet.getString(4));
            student.setGender(resultSet.getString(5));
            student.setJob(resultSet.getString(6));
        }


        StudentInformationUi studentInformationUi=new StudentInformationUi(student);
    }

    public static int update(Student student) throws SQLException {
        String SQL="update userinformation set name=?,gender=? where ID=?" ;
        PreparedStatement preparedStatement=SQL_Readin.con.prepareStatement(SQL);
        preparedStatement.setString(1, student.getName());
        preparedStatement.setString(2,student.getGender());
        preparedStatement.setString(3,User.num);

        return preparedStatement.executeUpdate();
    }
    public static int updatePassword(String password) throws SQLException {
        String SQL="update userinformation set Password=? where ID=?" ;
        PreparedStatement preparedStatement=SQL_Readin.con.prepareStatement(SQL);
        preparedStatement.setString(1, password);
        preparedStatement.setString(2,User.num);
        return preparedStatement.executeUpdate();
    }
}
