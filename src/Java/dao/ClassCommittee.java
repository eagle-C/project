package Java.dao;

import Java.utils.ClassCommitteeUI;

import java.sql.*;

public class ClassCommittee {
PreparedStatement preparedStatement;
static Statement statement;
static ResultSet resultSet;
public ClassCommitteeUI classCommitteeUI;
static String SQL="SELECT * FROM userinformation WHERE job <> '无'";

    public ClassCommittee() throws SQLException {
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
        classCommitteeUI=new ClassCommitteeUI(array);
    }
    public static Object[][] selectNotCommittee() throws SQLException {
        String selectNotCommitteeSQL="SELECT * FROM userinformation WHERE job = '无'";
        try {
            statement=SQL_Readin.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet=statement.executeQuery(selectNotCommitteeSQL);
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
    public static int updateNotCommittee(String num,String job) throws SQLException {
        PreparedStatement preparedStatement;
        String updateNotCommitteeSQL="update userinformation set job=? where ID=?and job='无'";
        preparedStatement=SQL_Readin.con.prepareStatement(updateNotCommitteeSQL);
        preparedStatement.setString(1,job);
        preparedStatement.setString(2,num);
        return preparedStatement.executeUpdate();
    }

    public static int delectStudentCommittee(String num) throws SQLException {
        PreparedStatement preparedStatement;
        String delectSQL="update userinformation set job='无' where ID=?";
        preparedStatement=SQL_Readin.con.prepareStatement(delectSQL);
        preparedStatement.setString(1,num);
        return preparedStatement.executeUpdate();
    }

    public static void selectStudent(String text) throws SQLException {
        PreparedStatement preparedStatement;
        String selectSQL="SELECT * FROM userinformation WHERE (ID LIKE ? OR name LIKE ? or job LIKE ? ) AND job <> '无'";
        preparedStatement = SQL_Readin.con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        preparedStatement.setString(1,"%"+text+"%");
        preparedStatement.setString(2,"%"+text+"%");
        preparedStatement.setString(3,"%"+text+"%");
        ResultSet resultSet = preparedStatement.executeQuery();
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
        ClassCommitteeUI classCommitteeUI=new ClassCommitteeUI(array);
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
