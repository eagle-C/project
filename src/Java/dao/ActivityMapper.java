package Java.dao;

import Java.pojo.Activity;
import Java.pojo.User;
import Java.utils.ActivityUI;
import Java.utils.ClassCommitteeUI;

import java.sql.*;

public class ActivityMapper {
    PreparedStatement preparedStatement;
    static Statement statement;
    static ResultSet resultSet;
    public ActivityUI activityUI;
    static String SQL="select * from activity_record where creator=?";
    public ActivityMapper() throws SQLException {
        if(User.identity){
            SQL="select * from activity_record where creator=?";
            try {
                preparedStatement = SQL_Readin.con.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setString(1,User.num);
                resultSet=preparedStatement.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            SQL="select * from activity_record ";
            try {
                preparedStatement = SQL_Readin.con.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//                preparedStatement.setString(1,User.num);
                resultSet=preparedStatement.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
            row[0] = resultSet.getObject(2);
            row[1] = resultSet.getObject(3);
            row[2] = resultSet.getObject(4);
            row[3] = resultSet.getObject(5);
            //row[0] = resultSet.getObject(1);
            //}
            array[index] = row;
            index++;
        }
        activityUI=new ActivityUI(array);
    }
    public static int insertActivity(Activity activity) throws SQLException {
        String insertActivitySQL="insert into activity_record (creator, activity_name, activity_time, activity_location, activity_content) values (?,?,?,?,?)";
        PreparedStatement preparedStatement;
        preparedStatement= SQL_Readin.con.prepareStatement(insertActivitySQL);
        preparedStatement.setString(1, User.num);
        preparedStatement.setString(2,activity.getName());
        preparedStatement.setString(3,activity.getTime());
        preparedStatement.setString(4,activity.getLocation());
        preparedStatement.setString(5,activity.getContent());
        return preparedStatement.executeUpdate();
    }
    public static Object[][] select() throws SQLException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = SQL_Readin.con.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1,User.num);
            resultSet=preparedStatement.executeQuery();
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
            row[0] = resultSet.getObject(2);
            row[1] = resultSet.getObject(3);
            row[2] = resultSet.getObject(4);
            row[3] = resultSet.getObject(5);
            //row[0] = resultSet.getObject(1);
            //}
            array[index] = row;
            index++;
        }
        return array;
    }

    public static int delectActivity(String text) throws SQLException {
        PreparedStatement preparedStatement;
        String delectSQL="delete from chopin.activity_record where creator=? and activity_name=?";
        preparedStatement=SQL_Readin.con.prepareStatement(delectSQL);
        preparedStatement.setString(1,User.num);
        preparedStatement.setString(2,text);
        return preparedStatement.executeUpdate();
    }
}
