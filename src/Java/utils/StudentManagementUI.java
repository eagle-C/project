package Java.utils;

import Java.dao.StudentManagement;
import Java.pojo.Coordinate;
import Java.pojo.Student;
import com.sun.tools.javac.Main;


import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class StudentManagementUI extends JFrame implements ActionListener {
    JTable table;
    Object columnName[]={"学号","姓名","性别","职位"};
    Box boxH;
    Box boxV;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JTextField textField;
    DefaultTableModel tableModel;
    int i;
    Object dateChange[][]=new Object[20][4];
    //static Point point=new Point(10,10);
    int dateChangePointer=0;
    public StudentManagementUI(Object[][] array) {
        setBounds(Coordinate.point.x, Coordinate.point.y, 600, 400);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        boxH=Box.createHorizontalBox();
        boxV=Box.createVerticalBox();
        button1=new JButton("录入");
        button2=new JButton("上传修改");
        button3=new JButton("删除");
        button4=new JButton("查询");
        textField=new JTextField(20);
        tableModel = new DefaultTableModel(array, columnName);
        //System.out.println(columnName.toString());
        table=new JTable(tableModel);
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                try {
                    System.out.println(tableModel.getValueAt(row,column));
                    System.out.println(tableModel.getValueAt(row,0));
                    System.out.println("Modified row: " + row + ", column: " + column);
                    System.out.println(tableModel.getColumnName(column));
                    dateChange[dateChangePointer][0]=row;
                    dateChange[dateChangePointer][1]=column;
                    dateChange[dateChangePointer][2]=tableModel.getValueAt(row,column);
                    dateChange[dateChangePointer][3]=tableModel.getValueAt(row,0);
                    dateChangePointer++;
                }catch (Exception exception){
                    System.out.println(exception);
                }

            }
        });
        JScrollPane scrollPane=new JScrollPane(table);
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        boxH.add(button1);
        boxH.add(button2);
        boxH.add(button3);
        boxH.add(textField);
        boxH.add(button4);
        boxV.add(boxH);
        boxV.add(scrollPane);
        add(boxV);
        addWindowListener(new CloseWindowListener());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==button1){
            Coordinate.point=this.getLocation();
            System.out.println("录入");
            JFrame frame=new JFrame();

            frame.setBounds(Coordinate.point.x, Coordinate.point.y, this.getWidth(), this.getHeight());
            frame.setLocation(Coordinate.point.x,Coordinate.point.y);
            frame.setResizable(false);

            frame.setLayout(new FlowLayout());
            JLabel label1=new JLabel("学号");
            JLabel label2=new JLabel("姓名");
            JLabel label3=new JLabel("性别");
            JLabel label4=new JLabel("工作");
            JTextField textField1=new JTextField(15);
            JTextField textField2=new JTextField(15);
            JTextField textField3=new JTextField(15);
            JTextField textField4=new JTextField(15);
            JButton button=new JButton("确定");
            frame.add(label1);
            frame.add(textField1);
            frame.add(label2);
            frame.add(textField2);
            frame.add(label3);
            frame.add(textField3);
            frame.add(label4);
            frame.add(textField4);
            frame.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Student student=new Student();
                    student.setGender(textField3.getText());
                    student.setJob(textField4.getText());
                    student.setNum(textField1.getText());
                    student.setName(textField2.getText());
                    Object newRow[]={student.getNum(),student.getName(),student.getGender(),student.getJob()};

                    try {
                            tableModel.insertRow(0, newRow);
                            //tableModel.removeRow(0);
                            int insertStudent = StudentManagement.insertStudent(student);
                            if(insertStudent<=0){
                                JOptionPane.showMessageDialog(frame,"录入失败","提示",JOptionPane.ERROR_MESSAGE);
                            }
                        JOptionPane.showMessageDialog(frame,"录入成功","提示",JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame,"录入失败","提示",JOptionPane.ERROR_MESSAGE);
                        //frame.dispose();
                        dispose();
                        try {
                            StudentManagement studentManagement=new StudentManagement();
                        } catch (SQLException exc) {
                            throw new RuntimeException(exc);
                        }
                        throw new RuntimeException(ex);

                    }
                    frame.dispose();
                }
            });
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // 设置窗口关闭时隐藏窗口而不关闭程序
            frame.setVisible(true);
        }else if(e.getSource()==button2){
            Coordinate.point=this.getLocation();
            System.out.println(dateChangePointer);
            System.out.println("编辑");
            for(i=0;i<dateChangePointer;i++){
                try {
                    //StudentManagement studentManagement=new StudentManagement();
                    int updateStudent = StudentManagement.updateStudent(dateChange[i]);
                    if(updateStudent<=0){
                        JOptionPane.showMessageDialog(this,"修改失败","提示",JOptionPane.ERROR_MESSAGE);
                        dispose();
                        try {
                            StudentManagement studentManagement=new StudentManagement();
                        } catch (SQLException exc) {
                            throw new RuntimeException(exc);
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this,"编辑失败2","提示",JOptionPane.ERROR_MESSAGE);
                    dispose();
                    try {
                        StudentManagement studentManagement=new StudentManagement();
                    } catch (SQLException exc) {
                        throw new RuntimeException(exc);
                    }
                    throw new RuntimeException(ex);
                }
            }
            if(i==dateChangePointer){
                JOptionPane.showMessageDialog(this,"修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
            }

        }else if(e.getSource()==button3){
            JFrame frame=new JFrame();
            Coordinate.point=this.getLocation();
            frame.setBounds(Coordinate.point.x, Coordinate.point.y, this.getWidth(), this.getHeight());
            frame.setLocation(Coordinate.point.x,Coordinate.point.y);
            frame.setLayout(new FlowLayout());
            frame.setResizable(false);
            JLabel label=new JLabel("请输入要删除的学生的学号");
            JTextField textField=new JTextField(15);
            JButton button=new JButton("确定");
            frame.add(label);
            frame.add(textField);
            frame.add(button);
            ////////////////////////////////
            Object[][] array;
            try {
              array = StudentManagement.select();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            DefaultTableModel tableModel = new DefaultTableModel(array, columnName);
            //System.out.println(columnName.toString());
            JTable table=new JTable(tableModel);
            frame.add(table);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String num=textField.getText();
                    try {
                        int delectStudent = StudentManagement.delectStudent(num);
                        if (delectStudent<=0){
                            JOptionPane.showMessageDialog(frame,"删除失败","提示",JOptionPane.ERROR_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(frame,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                    frame.dispose();
                    try {
                        StudentManagement studentManagement=new StudentManagement();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            frame.setVisible(true);
        }else if(e.getSource()==button4){
            Coordinate.point=this.getLocation();
            String text = textField.getText();
            try {
                StudentManagement.selectStudent(text);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        }
    }
}
