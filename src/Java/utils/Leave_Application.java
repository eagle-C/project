package Java.utils;

import Java.dao.SQL_Readin;
import Java.pojo.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;

public class Leave_Application extends JFrame implements ActionListener {
    JLabel nameLabel, timeLabel = new JLabel(), reasonLabel, numberLabel; // 标签，用于显示提示信息
    JTextField nameField, numberField; // 文本框，用于输入姓名和学号
    JTextArea reasonArea; // 文本区域，用于输入请假理由
    JButton sureButton; // 确认提交按钮
    JPanel p1, p2, p3; // 面板，用于组织界面布局
    LocalDateTime Date; // 当前日期和时间
    //Connection con; // 数据库连接对象
    Statement statement; // 数据库查询语句对象
    ResultSet resultSet; // 查询结果集

    public Leave_Application() throws SQLException {
        //setSize(600, 350);
        setBounds(Coordinate.point.x, Coordinate.point.y, 600, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);//设置窗口居中
        setLayout(new FlowLayout());
        setTitle("请假申请");
        //setBase(); // 建立数据库连接
        statement = SQL_Readin.con.createStatement();
        init(); // 初始化界面组件
        setResizable(false);
        setVisible(true);
    }

//    private void setBase() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my?useSSL=true&serverTimezone=CST", "root", "Cj+-*/1314");
//            statement = con.createStatement(); // 创建数据库查询语句对象
//        } catch (Exception E) {
//            System.out.println("连接失败");
//        }
//    }

    private void init() {
        nameLabel = new JLabel("姓名:");
        reasonLabel = new JLabel("请假理由:");
        numberLabel = new JLabel("学号:");
        numberField = new JTextField(35);
        nameField = new JTextField(35);
        reasonArea = new JTextArea(5, 35);

        Date = LocalDateTime.now(); // 获取当前日期和时间
        timeLabel = new JLabel("请假时间:" + Date.getYear() + "年" + Date.getMonthValue() + "月" + Date.getDayOfMonth() + "日" + Date.getHour() + "时" + Date.getMinute() + "分");
        add(timeLabel); // 在界面上显示请假时间

        p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        p1.setPreferredSize(new Dimension(600, 30));//设置容器合适大小
        p1.add(Box.createHorizontalStrut(100));
        p1.add(nameLabel);
        p1.add(Box.createHorizontalStrut(27));
        p1.add(nameField);
        add(p1);//利用p1普通窗格并且添加垂直间距使组件对齐

        p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        p2.setPreferredSize(new Dimension(600, 30));//设置容器合适大小
        p2.add(Box.createHorizontalStrut(100));
        p2.add(numberLabel);
        p2.add(Box.createHorizontalStrut(27));
        p2.add(numberField);
        add(p2);//利用p2普通窗格并且添加垂直间距使组件对齐

        p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.setPreferredSize(new Dimension(600, 100));//设置容器合适大小
        p3.add(Box.createHorizontalStrut(100));
        p3.add(reasonLabel);
        p3.add(Box.createHorizontalStrut(5));
        p3.add(reasonArea);
        add(p3);//利用p3普通窗格并且添加垂直间距使组件对齐

        sureButton = new JButton("确认提交");
        sureButton.setPreferredSize(new Dimension(100, 30));//设置容器合适大小
        add(Box.createVerticalStrut(100));
        add(sureButton);

        sureButton.addActionListener(this); // 添加按钮点击事件监听器
        addWindowListener(new CloseWindowListener());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (numberField.getText().equals("") || nameField.getText().equals("") || reasonArea.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请正确填写，不要留有空白", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Add, leave_number, leave_name, leave_reason, leave_time, leave_approval_status;
        leave_number = numberField.getText();
        leave_name = nameField.getText();
        leave_reason = reasonArea.getText();
        leave_time = Date.getYear() + "年" + Date.getMonthValue() + "月" + Date.getDayOfMonth() + "日" + Date.getHour() + "时" + Date.getMinute() + "分";
        Add = "insert into leave_management(leave_number,leave_name,leave_reason,leave_time) values('" + leave_number + "','" + leave_name + "','" + leave_reason + "','" + leave_time + "')";
        //Sql插入语句
        try {
            statement.execute(Add); // 执行插入操作
        } catch (Exception E) {
            System.out.println("发送失败");
            return;
        }

        JOptionPane.showMessageDialog(null,"请假申请提交成功","提交申请",JOptionPane.INFORMATION_MESSAGE);//对话框提示
        this.dispose(); // 关闭当前窗口
        Graph graph=new Graph();
    }

 //   public static void main(String[] args) {
//        Leave_Application leaveApplication = new Leave_Application();
//    }
}

