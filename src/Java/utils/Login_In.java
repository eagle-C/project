package Java.utils;

import Java.dao.SQL_Readin;
import Java.pojo.Coordinate;
import Java.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login_In extends  JFrame implements ActionListener {
    JLabel studentnumberlabel,studentpasswordlabel,teachernumberlabel,teacherpasswordlabel;
    JTextField studentnumberfield, teachernumberfield;
    JPasswordField studentpasswordField,teacherpasswordfield;
    JButton studentsign_in,studentsign_up,teachersign_up,teachersign_in;
    JTabbedPane tabbedPane;
    JPanel studentpanel,teacherpanel;
    Font font=new Font("宋体",Font.BOLD,20);
    Connection con;
    Statement statement;
    ResultSet resultSet;
    public  Login_In() throws SQLException {
        setSize(600,350);
        setLocation(Coordinate.point);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setTitle("登录注册页面");
        setlayout();
        statement = SQL_Readin.con.createStatement();
//        setBase();
        setActionevent();
//        setLocationRelativeTo(null);
        setVisible(true);
    }



//    private void setBase() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my?useSSL=true&serverTimezone=CST", "root", "Cj+-*/1314");
//            statement = con.createStatement();
//        }
//        catch (Exception E) {
//            System.out.println("连接失败");
//        }
//    }
    private void setActionevent() {
        studentsign_in.addActionListener(this);
        teachersign_in.addActionListener(this);
        studentsign_up.addActionListener(this);
        teachersign_up.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query,insert;
        if(e.getSource()==studentsign_in){
            query="select ID,Password from userinformation where Password='"+ studentpasswordField.getText()+"' and ID='"+studentnumberfield.getText()+"'";
            //System.out.println(query);
            try {
                resultSet=statement.executeQuery(query);
                if (resultSet.next()){
                    System.out.println("学生端登录成功");
                    Coordinate.point=getLocation();
                    User.num=studentnumberfield.getText();
                    System.out.println(User.num);
                    User.identity=false;
                    dispose();
                    Graph graph=new Graph();
                }
                else {
                    JOptionPane.showMessageDialog(null,"登录失败，请检查学号和密码是否正确输入","登录",JOptionPane.YES_OPTION);
                }
            }
            catch (Exception E){
                System.out.println("学生端登录失败");
            }

        }//学生登录端

        else if(e.getSource()==teachersign_in){
            query="select ID,Password from userinformation where Password='"+ teacherpasswordfield.getText()+"' and ID='"+teachernumberfield.getText()+"'";
            try {
                resultSet=statement.executeQuery(query);
                if (resultSet.next()){
                    System.out.println("教师端登录成功");
                    Coordinate.point=getLocation();
                    User.num=studentnumberfield.getText();
                    System.out.println(User.num);
                    User.identity=true;
                    dispose();
                    Graph graph=new Graph();
                }
                else {
                    JOptionPane.showMessageDialog(null,"登录失败，请检查账号和密码是否正确输入","登录",JOptionPane.YES_OPTION);
                }
            }
            catch (Exception E){
                System.out.println("教师端登录失败");
            }
        }//教师登录端

        else if(e.getSource()==studentsign_up){
            insert="insert into userinformation(ID,Password,Identity) values('"+studentnumberfield.getText()+"','"+studentpasswordField.getText()+"',0)";
            //System.out.println(insert);
            try {
                String s=JOptionPane.showInputDialog(null,"请再次输入密码","确认密码",JOptionPane.PLAIN_MESSAGE);
                if (s.equals(studentpasswordField.getText())){
                    statement.execute(insert);
                    JOptionPane.showMessageDialog(null,"注册成功","注册",JOptionPane.PLAIN_MESSAGE);
                    studentpasswordField.setText(null);
                }
                else {
                    //System.out.println(s+","+studentpasswordField.getText());
                    JOptionPane.showMessageDialog(null,"注册失败，密码输入错误","注册",JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception E){
                System.out.println(E);
                System.out.println("学生端注册失败");
            }
        }//学生注册端

        else {
            insert="insert into userinformation(ID,Password,Identity) values('"+teachernumberfield.getText()+"','"+teacherpasswordfield.getText()+"',1)";
            try{
                String s=JOptionPane.showInputDialog(null,"请再次输入密码","确认密码",JOptionPane.PLAIN_MESSAGE);
                if (s.equals(teacherpasswordfield.getText())){
                    statement.execute(insert);
                    JOptionPane.showMessageDialog(null,"注册成功","注册",JOptionPane.PLAIN_MESSAGE);
                    studentpasswordField.setText(null);
                }
                else {
                    JOptionPane.showMessageDialog(null,"注册失败，密码输入错误","注册",JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception E){
                System.out.println(E);
                System.out.println("教师端注册失败");
            }
        }//教师注册端

    }
    private void setlayout() {
        tabbedPane=new JTabbedPane(JTabbedPane.TOP);
        studentpanel=new JPanel();
        teacherpanel=new JPanel();
        tabbedPane.add("学生端登录,注册",studentpanel);
        tabbedPane.add("教师端登录,注册",teacherpanel);
        add(tabbedPane);
        tabbedPane.setPreferredSize(new Dimension(600,350));
        studentsetlayout();
        teachersetlayout();
    }


    private void studentsetlayout() {
        studentnumberlabel=new JLabel("学号:");
        studentnumberfield =new JTextField(35);
        studentpasswordlabel=new JLabel("密码:");
        studentpasswordField=new JPasswordField(35);
        studentsign_in=new JButton("登录");
        studentsign_up=new JButton("注册");

        studentnumberlabel.setFont(font);
        studentpasswordlabel.setFont(font);
        studentnumberfield.setPreferredSize(new Dimension(0,30));
        studentpasswordField.setPreferredSize(new Dimension(0,30));
        studentsign_in.setPreferredSize(new Dimension(200,35));
        studentsign_up.setPreferredSize(new Dimension(200,35));

        JPanel p1=new JPanel();
        p1.setPreferredSize(new Dimension(600,50));
        p1.add(studentnumberlabel);
        p1.add(studentnumberfield);
        studentpanel.add(p1);

        JPanel p2=new JPanel();
        p2.setPreferredSize(new Dimension(600,50));
        p2.add(studentpasswordlabel);
        p2.add(studentpasswordField);
        studentpanel.add(p2);

        JPanel p3=new JPanel();
        p3.setPreferredSize(new Dimension(600,100));
        p3.add(studentsign_in);
        p3.add(Box.createHorizontalStrut(50));
        p3.add(studentsign_up);
        studentpanel.add(p3);

    }
    private void teachersetlayout() {
        teachernumberlabel=new JLabel("账号:");
        teachernumberfield =new JTextField(35);
        teacherpasswordlabel=new JLabel("密码:");
        teacherpasswordfield=new JPasswordField(35);
        teachersign_in=new JButton("登录");
        teachersign_up=new JButton("注册");

        teachernumberlabel.setFont(font);
        teacherpasswordlabel.setFont(font);
        teachernumberfield.setPreferredSize(new Dimension(0,30));
        teacherpasswordfield.setPreferredSize(new Dimension(0,30));
        teachersign_in.setPreferredSize(new Dimension(200,35));
        teachersign_up.setPreferredSize(new Dimension(200,35));

        JPanel P1=new JPanel();
        P1.setPreferredSize(new Dimension(600,50));
        P1.add(teachernumberlabel);
        P1.add(teachernumberfield);
        teacherpanel.add(P1);

        JPanel P2=new JPanel();
        P2.setPreferredSize(new Dimension(600,50));
        P2.add(teacherpasswordlabel);
        P2.add(teacherpasswordfield);
        teacherpanel.add(P2);

        JPanel P3=new JPanel();
        P3.setPreferredSize(new Dimension(600,100));
        P3.add(teachersign_in);
        P3.add(Box.createHorizontalStrut(50));
        P3.add(teachersign_up);
        teacherpanel.add(P3);
    }

//    public static void main(String[] args) {
//        Login_In loginIn=new Login_In();
//    }


}
