package Java.utils;

import Java.dao.ActivityMapper;
import Java.dao.ClassCommittee;
import Java.dao.StudentInformation;
import Java.dao.StudentManagement;
import Java.pojo.Coordinate;
import Java.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Graph extends JFrame {
    MyPanel panel[]=new MyPanel[8];
    String panelName1[]={"学生管理","班委会人员管理","值日安排管理","日常费用记录","班级活动记录","奖惩记录","请假管理","留言簿"};
    String panelName0[]={"个人信息查看","班委会人员查看","值日安排查看","日常费用查看","班级活动记录","奖惩记录查看","请假","留言簿"};
    JPanel jPanel=new JPanel();
    JLabel label;
    public Graph() throws HeadlessException {

        setSize(600,550);
        setLocation(Coordinate.point);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setTitle("班级管理系统");
        //setLocationRelativeTo(null);
        if(User.identity){
            SetLayout1();
            SetOpen1();
        }else{
            SetLayout0();
            SetOpen0();
        }


        setVisible(true);
    }



    private void SetLayout1() {
        label=new JLabel("欢迎使用班级管理系统");
        jPanel.setPreferredSize(new Dimension(600, 50));
        label.setFont(new Font("宋体",Font.BOLD,30));
        jPanel.add(label);
        add(jPanel);
        for(int i=0;i<8;i++){
            panel[i]=new MyPanel(panelName1[i]);
            add(panel[i]);
        }
    }
    private void SetLayout0() {
        label=new JLabel("欢迎使用班级管理系统");
        jPanel.setPreferredSize(new Dimension(600, 50));
        label.setFont(new Font("宋体",Font.BOLD,30));
        jPanel.add(label);
        add(jPanel);
        for(int i=0;i<8;i++){
            panel[i]=new MyPanel(panelName0[i]);
            add(panel[i]);
        }
    }
    private void SetOpen1() {

        panel[0].button.addActionListener((e)->{
            Coordinate.point=getLocation();
            dispose();
            try {
                StudentManagement studentManagement=new StudentManagement();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });//点击按钮打开学生管理模块

        panel[1].button.addActionListener((e)->{
            Coordinate.point=getLocation();
            dispose();
            try {
                ClassCommittee classCommittee=new ClassCommittee();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });//点击按钮打开班委会人员管理模块

        panel[2].button.addActionListener((e)->{
            Coordinate.point=getLocation();
            dispose();
            Duty_Roster e2=new Duty_Roster("select * from duty_management");
        });//点击按钮打开值日安排管理模块

        panel[3].button.addActionListener((e)->{
            Coordinate.point=getLocation();
            dispose();
            Class_Expense classExpense=new Class_Expense("select * from expense_management");
        });//点击按钮打开日常费用记录模块

        panel[4].button.addActionListener((e)->{
            dispose();
            Coordinate.point=getLocation();
            try {
                ActivityMapper activityMapper=new ActivityMapper();

            } catch (SQLException ex) {

                throw new RuntimeException(ex);
            }
        });//点击按钮打开班级活动记录模块

        panel[5].button.addActionListener((e)->{
            Coordinate.point=getLocation();
            dispose();
            Punishment e1 = new Punishment("select * from reward_punishment");
        });//点击按钮打开奖惩记录模块

        panel[6].button.addActionListener((e)->{
            dispose();
            Coordinate.point=getLocation();
            try {
                Leave_Management leaveManagement = new Leave_Management();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });//点击按钮打开请假管理模块

        panel[7].button.addActionListener((e)->{
            dispose();
            Coordinate.point=getLocation();
            try {
                Message_board leave_Manag = new Message_board();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });//点击按钮打开留言簿模块
    }
//    public static void main(String[] args) {
//        Graph graph=new Graph();
//    }
private void SetOpen0() {

    panel[0].button.addActionListener((e)->{
        Coordinate.point=getLocation();
        dispose();
        //StudentManagement studentManagement=new StudentManagement();
        try {
            StudentInformation studentInformation=new StudentInformation();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    });//点击按钮打开学生管理模块

    panel[1].button.addActionListener((e)->{
        Coordinate.point=getLocation();
        dispose();
        try {
            ClassCommittee classCommittee=new ClassCommittee();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    });//点击按钮打开班委会人员管理模块

    panel[2].button.addActionListener((e)->{
        Coordinate.point=getLocation();
        dispose();
        Duty_Roster e2=new Duty_Roster("select * from duty_management");
    });//点击按钮打开值日安排管理模块

    panel[3].button.addActionListener((e)->{
        Coordinate.point=getLocation();
        dispose();
        Class_Expense classExpense=new Class_Expense("select * from expense_management");
    });//点击按钮打开日常费用记录模块

    panel[4].button.addActionListener((e)->{
        dispose();
        Coordinate.point=getLocation();
        try {
            ActivityMapper activityMapper=new ActivityMapper();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    });//点击按钮打开班级活动记录模块

    panel[5].button.addActionListener((e)->{
        Coordinate.point=getLocation();
        dispose();
        Punishment e1 = new Punishment("select * from reward_punishment");
    });//点击按钮打开奖惩记录模块

    panel[6].button.addActionListener((e)->{
        dispose();
        Coordinate.point=getLocation();
        try {
            Leave_Application leaveApplication = new Leave_Application();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    });//点击按钮打开请假管理模块

    panel[7].button.addActionListener((e)->{
        dispose();
        Coordinate.point=getLocation();
        try {
            Message_board leave_Manag = new Message_board();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    });//点击按钮打开留言簿模块
}
    //    public static void main(String[] args) {
//        Graph graph=new Graph();
//    }
    private  class  MyPanel extends  JPanel{
        JButton button;
        public  MyPanel(String name) {
            setPreferredSize(new Dimension(600, 50));
            button = new JButton(name);
            button.setPreferredSize(new Dimension(300, 45));
            button.setBackground(Color.black);
            button.setForeground(Color.white);
            add(button);
        }
    }
}
