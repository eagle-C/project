package Java.dao;

import java.sql.*;

public class SQL_Readin
{
    static String sqlname="userinformation";//依据表名更改，默认先读账户密码表
    public static Connection con = null;
    static Statement state = null;
    public static ResultSet rs=null;

    public static void registersql() {//通过接受用户的账户名和密码，来注册数据库
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception exception) {}
        String uri = "jdbc:mysql://localhost/chopin?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=GMT&characterEncoding=gb2312";
        try { //uri的localhost可以继续修改，chopin部分的数据库名称也可以修改
            con = DriverManager.getConnection(uri, "root", "Hry303108..@#");//需要修改密码
            state = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("连接成功");
    }
    public static void activatesql(PreparedStatement ps) {//该静态方法用于用户已经输入数据表名字之后使用
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(-1);
        }
    }
}
