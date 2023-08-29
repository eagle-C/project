package Java.utils;

import Java.dao.StudentInformation;
import Java.pojo.Coordinate;
import Java.pojo.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StudentInformationUi extends JFrame implements ActionListener {
    Box boxH1;

    Box boxV;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JButton button1;
    JButton button2;
    public StudentInformationUi(Student student) {

        setBounds(Coordinate.point.x, Coordinate.point.y, 400, 300);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        boxH1=Box.createHorizontalBox();
        Box boxH2=Box.createHorizontalBox();
        Box boxH3=Box.createHorizontalBox();
        Box boxH4=Box.createHorizontalBox();
        Box boxH5=Box.createHorizontalBox();

        boxV=Box.createVerticalBox();
        label1=new JLabel("学号:  ");
        label2=new JLabel("姓名:  ");
        label3=new JLabel("性别:  ");
        label4=new JLabel("职位:  ");
        JLabel label11=new JLabel(student.getNum());
        JLabel label22=new JLabel(student.getName());
        JLabel label33=new JLabel(student.getNum());
        JLabel label44=new JLabel(student.getJob());
        button1=new JButton("更改信息");
        button2=new JButton("更改密码");
        boxH5.add(label1);boxH5.add(label11);
        boxH2.add(label2);boxH2.add(label22);
        boxH3.add(label3);boxH3.add(label33);
        boxH4.add(label4);boxH4.add(label44);
        boxH1.add(button1);
        boxH1.add(button2);
        button1.addActionListener(this);
        button2.addActionListener(this);
        boxV.add(boxH5);
        boxV.add(boxH2);
        boxV.add(boxH3);
        boxV.add(boxH4);
        boxV.add(boxH1);
        add(boxV);
        addWindowListener(new CloseWindowListener());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Coordinate.point=this.getLocation();
        if(e.getSource()==button1){
            JFrame frame=new JFrame();
            frame.setBounds(Coordinate.point.x, Coordinate.point.y, 400, 300);
            //fame.//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new FlowLayout());
            Box boxH2=Box.createHorizontalBox();
            Box boxH3=Box.createHorizontalBox();
            Box boxV=Box.createVerticalBox();

            JLabel label1=new JLabel("姓名");
            JTextField textField1=new JTextField(15);
            JLabel label2=new JLabel("性别");
            JRadioButton radioButton1=new JRadioButton("男");
            JRadioButton radioButton2=new JRadioButton("女");
            ButtonGroup buttonGroup=new ButtonGroup();
            buttonGroup.add(radioButton1);
            buttonGroup.add(radioButton2);

            //JTextField textField2=new JTextField(15);
            JButton button=new JButton("确定");
            boxH2.add(label1);
            boxH2.add(textField1);
            boxH3.add(label2);
            //boxH3.add(textField2);
            boxH3.add(radioButton1);
            boxH3.add(radioButton2);
            boxV.add(boxH2);
            boxV.add(boxH3);
            boxV.add(button);
            frame.add(boxV);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Student student=new Student();
                    student.setName(textField1.getText());
                    //student.setGender(textField2.getText());
                    if(radioButton1.isSelected()){
                        student.setGender("男");
                    }else if(radioButton2.isSelected()){
                        student.setGender("女");
                    }
                    try {
                        int update = StudentInformation.update(student);
                        if(update==1){
                            JOptionPane.showMessageDialog(frame,"更改成功","提示",JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            StudentInformation studentInformation=new StudentInformation();
                        }else{
                            JOptionPane.showMessageDialog(frame,"更改失败","提示",JOptionPane.ERROR_MESSAGE);
                            dispose();
                            StudentInformation studentInformation=new StudentInformation();
                        }
                        frame.dispose();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            frame.setVisible(true);
        }else{
            JFrame frame=new JFrame();
            frame.setBounds(Coordinate.point.x, Coordinate.point.y, 400, 300);
            //fame.//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new FlowLayout());
            Box boxH2=Box.createHorizontalBox();
            Box boxH3=Box.createHorizontalBox();
            Box boxV=Box.createVerticalBox();

            JLabel label1=new JLabel("新密码");
            JPasswordField textField1=new JPasswordField(15);
            JLabel label2=new JLabel("重复密码");
            JPasswordField textField2=new JPasswordField(15);
            JButton button=new JButton("确定");
            boxH2.add(label1);
            boxH2.add(textField1);
            boxH3.add(label2);
            boxH3.add(textField2);
            boxV.add(boxH2);
            boxV.add(boxH3);
            boxV.add(button);
            frame.add(boxV);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s1=textField1.getText();
                    String s2=textField2.getText();
                    if (s1.equals(s2)){
                        try {
                            int updatePassword = StudentInformation.updatePassword(s1);
                            if(updatePassword==1){
                                JOptionPane.showMessageDialog(frame,"更改成功","提示",JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                                StudentInformation studentInformation=new StudentInformation();
                            }else{
                                JOptionPane.showMessageDialog(frame,"更改失败","提示",JOptionPane.ERROR_MESSAGE);
                                dispose();
                                StudentInformation studentInformation=new StudentInformation();
                            }
                            frame.dispose();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else{
                        JOptionPane.showMessageDialog(frame,"密码不一致","提示",JOptionPane.ERROR_MESSAGE);

                        textField1.setText("");
                        textField2.setText("");
                    }
                }
            });
            frame.setVisible(true);
        }
    }
}
