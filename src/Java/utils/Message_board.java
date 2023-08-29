package Java.utils;

import Java.dao.SQL_Readin;
import Java.pojo.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message_board extends JFrame {
    JComboBox<String> date; // 下拉框，用于选择日期
    JScrollPane scrollPane; // 滚动面板，用于显示留言内容
    JTextArea area; // 文本区域，用于显示留言内容
    JButton addButton; // 添加留言的按钮
    Connection con; // 数据库连接对象
    Statement statement; // 数据库查询语句对象
    ResultSet resultSet; // 查询结果集
    String query; // 查询语句字符串

    public Message_board() throws SQLException {
        setSize(700, 500);
        setLocation(Coordinate.point);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);//设置窗口居中
        setLayout(new FlowLayout());
        //setConnection(); // 建立数据库连接
        statement = SQL_Readin.con.createStatement(); // 创建数据库查询语句对象
        setlayout(); // 设置界面布局
        setResizable(false);//禁用窗口扩大功能
        addWindowListener(new CloseWindowListener());
        setVisible(true);
    }

//    private void setConnection() {
//        try {
//            // 加载MySQL数据库的JDBC驱动
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            // 建立数据库连接
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my?useSSL=true&serverTimezone=CST", "root", "Cj+-*/1314");
//            statement = con.createStatement(); // 创建数据库查询语句对象
//        } catch (Exception e) {
//            System.out.println("连接失败");
//        }
//    }

    private void setlayout() {
        setJComboBox(); // 设置日期下拉框
        JLabel label = new JLabel("按照日期查询:");
        Font font = new Font("宋体", Font.BOLD, 15);
        addButton = new JButton("添加留言");
        area = new JTextArea();
        area.setEnabled(false); // 禁用文本区域编辑，用于显示留言内容
        area.setFont(font);
        scrollPane = new JScrollPane(area); // 创建滚动面板，并将文本区域添加到其中
        scrollPane.setPreferredSize(new Dimension(650, 350));//设置合适的窗格大小
        addButton.setPreferredSize(new Dimension(100, 40));//设置合适的按钮大小
        add(label);
        add(date); // 将日期下拉框添加到界面
        add(scrollPane); // 将滚动面板添加到界面
        add(addButton); // 将添加留言按钮添加到界面
        addButton.addActionListener((e) -> {
            // 为添加留言按钮添加点击事件监听器
            JFrame frame = new JFrame();//新建留言窗口
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);//设置窗口居中
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new FlowLayout());
            frame.setResizable(false);//禁用窗口扩大功能
            JButton sureButton;
            LocalDate timDate = LocalDate.now();
            String gettime = timDate.toString(); // 获取当前日期
            JLabel timeJLabel = new JLabel("留言时间" + gettime);
            frame.add(timeJLabel);
            JTextArea word = new JTextArea();
            JScrollPane wordPane = new JScrollPane(word); // 创建滚动面板，并将文本区域添加到其中
            wordPane.setPreferredSize(new Dimension(400, 250));
            frame.add(wordPane);
            sureButton = new JButton("确认");
            frame.add(sureButton);
            sureButton.addActionListener((E) -> {
                if (word.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "不要提交空白留言", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 构建插入留言的SQL语句
                String insertString = "insert into message_board(message_time,message_content,message_name) values('" + gettime + "','" + word.getText() + "','" + "匿名" + "')";
                try {
                    statement.execute(insertString); // 执行插入操作
                    JOptionPane.showMessageDialog(null, "留言成功，留言时间：" + LocalDateTime.now(), "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e2) {
                    System.out.println("更新失败");
                }
                setJComboBox(); // 更新日期下拉框的内容
                frame.dispose();
            });
            frame.setVisible(true);
        });
    }

    private void setJComboBox() {
        date = null; // 初始化日期下拉框
        query = "select * from message_board";
        try {
            resultSet = statement.executeQuery(query); // 执行查询语句
//            while (resultSet.next()) {
//                length++;
//            }
//            resultSet.beforeFirst(); // 统计数据库的行数
        } catch (Exception e) {
            System.out.println("query fail!");
        }
        date = new JComboBox<String>(); // 创建日期下拉框
        try {
            while (resultSet.next()) {
                date.addItem(resultSet.getString(3)); // 将留言日期添加到下拉框中
            }
        } catch (Exception e) {
            System.out.println("传送失败");
        }
        date.addActionListener((e) -> {
            // 为日期下拉框添加选择事件监听器
            String time = date.getItemAt(date.getSelectedIndex());
            query = "select message_content from message_board where message_time = '" + time + "'";
            try {
                resultSet = statement.executeQuery(query); // 执行查询语句
                resultSet.next();
                area.setText(resultSet.getString(1)); // 在文本区域显示选定日期的留言内容
            } catch (Exception E) {
                System.out.println("显示失败");
            }
        });
    }

//    public static void main(String[] args) {
//        Message_board leave_Manag = new Message_board();
//    }
}
