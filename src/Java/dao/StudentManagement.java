package Java.dao;

import Java.pojo.Student;
import Java.utils.StudentManagementUI;

import java.sql.*;

public class StudentManagement {

    StudentManagementUI studentManagementUI;
    static Statement statement;
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;
    static String SQL="select * from userinformation";
    static String insertSQL="insert into userinformation(ID, Identity, name, gender, job) VALUES (?,?,?,?,?)";
    Object[][] array;
    public StudentManagement() throws SQLException {
        try {
            statement=SQL_Readin.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet=statement.executeQuery(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 获取结果集的元数据，包括列数和列名等信息
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

// 确定结果集的大小，并创建相应大小的数组
        resultSet.last();  // 将结果集指针移动到最后一行
        int rowCount = resultSet.getRow();  // 获取结果集的行数
        resultSet.beforeFirst();  // 将结果集指针重置到第一行
        Object[][] array = new Object[rowCount][];

// 遍历结果集，将每行数据存储到数组中
        int index = 0;
        while (resultSet.next()) {
            Object[] row = new Object[columnCount];
            //for (int i = 1; i <= 5; i++) {
                row[0] = resultSet.getObject(1);
                row[1] = resultSet.getObject(4);
                row[2] = resultSet.getObject(5);
                row[3] = resultSet.getObject(6);
                //row[0] = resultSet.getObject(1);
            //}
            array[index] = row;
            index++;
        }
        studentManagementUI=new StudentManagementUI(array);
    }
    public static int insertStudent(Student student) throws SQLException {
        preparedStatement= SQL_Readin.con.prepareStatement(insertSQL);
        preparedStatement.setString(1,student.getNum());
        preparedStatement.setString(2,"0");
        preparedStatement.setString(3,student.getName());
        preparedStatement.setString(4,student.getGender());
        preparedStatement.setString(5,student.getJob());
        return preparedStatement.executeUpdate();
    }

    public static int updateStudent(Object[] objects) throws SQLException {
        String updateSQL = "UPDATE userinformation SET ";
        if ((int) objects[1] == 0) {
            updateSQL += "ID = ?";
        } else if ((int) objects[1] == 1) {
            updateSQL += "name = ?";
        } else if ((int) objects[1] == 2) {
            updateSQL += "gender = ?";
        } else if ((int) objects[1] == 3) {
            updateSQL += "job = ?";
        }

        updateSQL += " WHERE ID = ?";
        preparedStatement=SQL_Readin.con.prepareStatement(updateSQL);
        //preparedStatement.setString();
        preparedStatement.setObject(1,objects[2]);
        preparedStatement.setObject(2,objects[3]);
        return preparedStatement.executeUpdate();
    }

    public static int delectStudent(String num) throws SQLException {
        String delectSQL="delete from userinformation where ID=?";
        preparedStatement=SQL_Readin.con.prepareStatement(delectSQL);
        preparedStatement.setString(1,num);
        return preparedStatement.executeUpdate();
    }

    public static void selectStudent(String text) throws SQLException {
        String selectSQL="select * from userinformation where ID like ? or name like ?";
        preparedStatement = SQL_Readin.con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        preparedStatement.setString(1,"%"+text+"%");
        preparedStatement.setString(2,"%"+text+"%");
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
////
// 确定结果集的大小，并创建相应大小的数组
        resultSet.last();  // 将结果集指针移动到最后一行
        int rowCount = resultSet.getRow();  // 获取结果集的行数
        resultSet.beforeFirst();  // 将结果集指针重置到第一行
        Object[][] array = new Object[rowCount][];

// 遍历结果集，将每行数据存储到数组中
        int index = 0;
        while (resultSet.next()) {
            Object[] row = new Object[columnCount];
            //for (int i = 1; i <= 5; i++) {
            row[0] = resultSet.getObject(1);
            row[1] = resultSet.getObject(4);
            row[2] = resultSet.getObject(5);
            row[3] = resultSet.getObject(6);
            //row[0] = resultSet.getObject(1);
            //}
            array[index] = row;
            index++;
        }
        StudentManagementUI studentManagementUI=new StudentManagementUI(array);
        return;
    }
    public static Object[][] select() throws SQLException {
        try {
            statement=SQL_Readin.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet=statement.executeQuery(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 获取结果集的元数据，包括列数和列名等信息
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

// 确定结果集的大小，并创建相应大小的数组
        resultSet.last();  // 将结果集指针移动到最后一行
        int rowCount = resultSet.getRow();  // 获取结果集的行数
        resultSet.beforeFirst();  // 将结果集指针重置到第一行
        Object[][] array = new Object[rowCount][];

// 遍历结果集，将每行数据存储到数组中
        int index = 0;
        while (resultSet.next()) {
            Object[] row = new Object[columnCount];
            //for (int i = 1; i <= 5; i++) {
            row[0] = resultSet.getObject(1);
            row[1] = resultSet.getObject(4);
            row[2] = resultSet.getObject(5);
            row[3] = resultSet.getObject(6);
            //row[0] = resultSet.getObject(1);
            //}
            array[index] = row;
            index++;
        }
        return array;
    }
}
