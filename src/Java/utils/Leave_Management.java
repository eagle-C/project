package Java.utils;

import Java.dao.SQL_Readin;
import Java.pojo.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Leave_Management extends JFrame implements ActionListener {
    JTable table; // 表格组件，用于显示请假记录
    JScrollPane scrollPane; // 表格的滚动面板
    String Data[][];
    JComboBox<String> comboBox; // 下拉框，用于选择要批准的请假记录
    String columnNames[], query;
    Connection con; // 数据库连接对象
    Statement statement; // 数据库查询语句对象
    ResultSet resultSet;
    int length = 0; // 请假记录的数量

    public Leave_Management() throws SQLException {
        setLocation(Coordinate.point);
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("请假批准(教师)");
        statement= SQL_Readin.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //setBase(); // 建立数据库连接
        setTable(); // 设置表格内容
        setLayoutComponents(); // 设置界面布局
        addWindowListener(new CloseWindowListener());
        setResizable(false);
        setVisible(true);
    }

//    private void setBase() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my?useSSL=true&serverTimezone=CST", "root", "Cj+-*/1314");
//            statement = con.createStatement();
//        } catch (Exception E) {
//            System.out.println("连接失败");
//        }
//    }

    private void setTable() {
        columnNames = new String[]{"姓名", "时间", "学号", "请假理由"};
        query = "select leave_number,leave_time,leave_name,leave_reason from leave_management";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                length++;
            }
            resultSet.beforeFirst();
        } catch (Exception e) {
            System.out.println("查询错误");
        }
        Data = new String[length][];
        try {
            for (int i = 0; resultSet.next(); i++) {
                Data[i] = new String[4];
                for (int j = 0; j < 4; j++) {
                    Data[i][j] = resultSet.getString(j + 1);
                }
            }
        } catch (Exception e) {
            System.out.println("遍历失败");
        }
        table = new JTable(Data, columnNames);
    }

    private void setLayoutComponents() {
        scrollPane = new JScrollPane(table); // 将表格添加到滚动面板中
        add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(550, 250));

        comboBox = new JComboBox<String>();
        query = "select leave_name,leave_number from leave_management";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                comboBox.addItem("学号:" + resultSet.getString(2) + "     姓名:" + resultSet.getString(1));
            }
        } catch (Exception e) {
            System.out.println("添加失败");
        }
        add(new JLabel("批准:"));
        add(comboBox);
        comboBox.addActionListener(this); // 为下拉框添加事件监听器
    }

//    public static void main(String[] args) {
//        Leave_Management leaveManagement = new Leave_Management();
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String number, name, select;
        JComboBox<String> source = (JComboBox<String>) e.getSource();
        select = (String) source.getSelectedItem();
        try {
            resultSet = statement.executeQuery("select leave_name,leave_number from leave_management");
            while (resultSet.next()) {
                if (select.equals("学号:" + resultSet.getString(2) + "     姓名:" + resultSet.getString(1))) {
                    name = resultSet.getString(1);
                    number = resultSet.getString(2);

                    UIManager.put("OptionPane.yesButtonText", "批准"); // 更改 Yes 按钮的文本
                    UIManager.put("OptionPane.noButtonText", "不批准"); // 更改 No 按钮的文本
                    UIManager.put("OptionPane.cancelButtonText", "还没想好,先返回"); // 更改 Cancel 按钮的文本

                    int n=JOptionPane.showConfirmDialog(null,"是否确定批准","提示",JOptionPane.YES_NO_CANCEL_OPTION);
                    switch (n){

                        case  JOptionPane.YES_OPTION :
                            statement.execute("update  leave_management set leave_approval_status='批准' where leave_name='" + name + "'  and leave_number='" + number + "'");
                            break;
                            case  JOptionPane.NO_OPTION:
                                statement.execute("update  leave_management set leave_approval_status='不准' where leave_name='" + name + "'  and leave_number='" + number + "'");
                                break;
                                case  JOptionPane.CANCEL_OPTION:
                                    return;
                    }
                    return;
                }
            }
        } catch (Exception e1) {
            System.out.println("更新错误");
        }
    }
}
