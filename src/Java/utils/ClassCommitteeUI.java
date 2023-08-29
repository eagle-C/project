package Java.utils;

import Java.dao.ClassCommittee;
import Java.dao.StudentManagement;
import Java.pojo.Coordinate;
import Java.pojo.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ClassCommitteeUI extends JFrame implements ActionListener {
    JTable table;
    Object columnName[] = {"学号", "姓名", "性别", "职位"};
    Box boxH;
    int i;
    Box boxV;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JTextField textField;
    DefaultTableModel tableModel;
    Object dateChange[][] = new Object[20][4];
//    static Point point = new Point(10, 10);
    int dateChangePointer = 0;

    public ClassCommitteeUI(Object[][] array) {
        setBounds(Coordinate.point.x, Coordinate.point.y, 600, 400);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        boxH = Box.createHorizontalBox();
        boxV = Box.createVerticalBox();
        button1 = new JButton("增加职位");
        button2 = new JButton("上传修改");
        button3 = new JButton("移除职位");
        button4 = new JButton("查询");
        textField = new JTextField(20);
        tableModel = new DefaultTableModel(array, columnName);
        //System.out.println(columnName.toString());
        table = new JTable(tableModel);
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                try {
                    System.out.println(tableModel.getValueAt(row, column));
                    System.out.println(tableModel.getValueAt(row, 0));
                    System.out.println("Modified row: " + row + ", column: " + column);
                    System.out.println(tableModel.getColumnName(column));
                    dateChange[dateChangePointer][0] = row;
                    dateChange[dateChangePointer][1] = column;
                    dateChange[dateChangePointer][2] = tableModel.getValueAt(row, column);
                    dateChange[dateChangePointer][3] = tableModel.getValueAt(row, 0);
                    dateChangePointer++;
                } catch (Exception exception) {
                    System.out.println(exception);
                }

            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
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
        if(User.identity==false){
            button1.setVisible(false);
            button2.setVisible(false);
            button3.setVisible(false);
            button4.setVisible(false);
            textField.setVisible(false);
        }
        boxV.add(scrollPane);
        add(boxV);
        addWindowListener(new CloseWindowListener());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object[][] notCommittee;
        if(e.getSource()==button1){
            Coordinate.point=this.getLocation();
            System.out.println("增加职位");
            try {
                notCommittee = ClassCommittee.selectNotCommittee();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            JFrame frame=new JFrame();
            frame.setBounds(Coordinate.point.x, Coordinate.point.y, 600, 400);
            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());
            Box boxH = Box.createHorizontalBox();
            Box boxV = Box.createVerticalBox();
            JLabel label1=new JLabel("学号");
            JTextField textField1=new JTextField(15);
            JLabel label2=new JLabel("职位");
            //JTextField textField2=new JTextField(15);
            String[] items = {"班长", "学习委员", "纪律委员","劳动委员","组织委员","生活委员","团支书","体育委员","思政委员","宣传委员","素拓委员"};
            JComboBox<String> comboBox = new JComboBox<>(items);
            JButton button = new JButton("确定");
            DefaultTableModel tableModel1 = new DefaultTableModel(notCommittee, columnName);
            //System.out.println(columnName.toString());
            JTable table = new JTable(tableModel1);
            JScrollPane scrollPane = new JScrollPane(table);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String num=textField1.getText();
                    String job=(String) comboBox.getSelectedItem();
                    try {
                        int updateNotCommittee = ClassCommittee.updateNotCommittee(num, job);
                        if(updateNotCommittee<=0){
                            JOptionPane.showMessageDialog(frame,"录入失败","提示",JOptionPane.ERROR_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(frame,"录入成功","提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                        frame.dispose();
                        dispose();
                        ClassCommittee classCommittee=new ClassCommittee();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            boxH.add(label1);
            boxH.add(textField1);
            boxH.add(label2);
            boxH.add(comboBox);
            boxH.add(button);
            boxV.add(boxH);
            boxV.add(scrollPane);
            frame.add(boxV);
            frame.setVisible(true);
        }else if(e.getSource()==button2){
            Coordinate.point=this.getLocation();
            System.out.println(dateChangePointer);
            System.out.println("编辑");
            for(i=0;i<dateChangePointer;i++){
                //StudentManagement studentManagement=new StudentManagement();
                int updateStudent = 0;
                try {
                    updateStudent = StudentManagement.updateStudent(dateChange[i]);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if(updateStudent<=0){
                    JOptionPane.showMessageDialog(this,"修改失败","提示",JOptionPane.ERROR_MESSAGE);
                }
            }
            if(i==dateChangePointer){
                JOptionPane.showMessageDialog(this,"修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(e.getSource()==button3){
            Coordinate.point=this.getLocation();
            JFrame frame=new JFrame();
            Coordinate.point=this.getLocation();
            frame.setBounds(Coordinate.point.x, Coordinate.point.y, this.getWidth(), this.getHeight());
            frame.setLocation(Coordinate.point.x,Coordinate.point.y);
            frame.setLayout(new FlowLayout());
            frame.setResizable(false);
            JLabel label=new JLabel("请输入要移除职位的学生的学号");
            JTextField textField=new JTextField(15);
            JButton button=new JButton("确定");
            Object[][] array;
            try {
                array = ClassCommittee.select();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            DefaultTableModel tableModel = new DefaultTableModel(array, columnName);
            //System.out.println(columnName.toString());
            JTable table = new JTable(tableModel);
            frame.add(label);
            frame.add(textField);
            frame.add(button);
            frame.add(table);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String num=textField.getText();
                    try {
                        int delectStudentCommittee = ClassCommittee.delectStudentCommittee(num);
                        if (delectStudentCommittee<=0){
                            JOptionPane.showMessageDialog(frame,"移除失败","提示",JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                    frame.dispose();
                    try {
                        ClassCommittee classCommittee=new ClassCommittee();
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
                ClassCommittee.selectStudent(text);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        }
    }
}