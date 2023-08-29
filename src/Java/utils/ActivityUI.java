package Java.utils;

import Java.dao.ActivityMapper;
import Java.pojo.Activity;
import Java.pojo.Coordinate;
import Java.pojo.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static javax.swing.JOptionPane.*;

public class ActivityUI extends JFrame implements ActionListener {
    JTable table;
    Object columnName[]={"活动名","活动时间","活动地点","内容"};
    Box boxH;
    Box boxV;
    JButton button1;
    static JButton button2;
    DefaultTableModel tableModel;
    Object dateChange[][]=new Object[20][5];
    //static Point point=new Point(10,10);
    int dateChangePointer=0;
    public ActivityUI(Object[][] array) {
        setBounds(Coordinate.point.x, Coordinate.point.y, 600, 400);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        boxH=Box.createHorizontalBox();
        boxV=Box.createVerticalBox();
        button1=new JButton("创建活动");
        button2=new JButton("删除活动");
        tableModel = new DefaultTableModel(array, columnName);
        //System.out.println(columnName.toString());
        table=new JTable(tableModel);
//        tableModel.addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                int row = e.getFirstRow();
//                int column = e.getColumn();
//                try {
//                    System.out.println(tableModel.getValueAt(row,column));
//                    System.out.println(tableModel.getValueAt(row,0));
//                    System.out.println("Modified row: " + row + ", column: " + column);
//                    System.out.println(tableModel.getColumnName(column));
//                    dateChange[dateChangePointer][0]=row;
//                    dateChange[dateChangePointer][1]=column;
//                    dateChange[dateChangePointer][2]=tableModel.getValueAt(row,column);
//                    dateChange[dateChangePointer][3]=tableModel.getValueAt(row,0);
//                    dateChangePointer++;
//                }catch (Exception exception){
//                    System.out.println(exception);
//                }
//
//            }
//        });
        JScrollPane scrollPane=new JScrollPane(table);
        button1.addActionListener(this);
        button2.addActionListener(this);
        boxH.add(button1);
        boxH.add(button2);
        boxV.add(boxH);
        boxV.add(scrollPane);
        add(boxV);
        if(User.identity==false){
            button1.setVisible(false);
            button2.setVisible(false);
        }
        addWindowListener(new CloseWindowListener());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==button1){
            Coordinate.point=this.getLocation();
            JFrame frame=new JFrame();
            frame.setBounds(Coordinate.point.x, Coordinate.point.y, 600, 400);
            frame.setLayout(new FlowLayout());
            JLabel label[]=new JLabel[6];
            JTextField textField[]=new JTextField[6];
            JTextArea textArea=new JTextArea(4,30);
            JButton button=new JButton("确定");
            label[1]=new JLabel("活动名");
            label[2]=new JLabel("活动时间");
            label[3]=new JLabel("活动地点");
            label[4]=new JLabel("活动内容");
            textField[1]=new JTextField(15);
            textField[2]=new JTextField(15);
            textField[3]=new JTextField(15);
            frame.add(label[1]);
            frame.add(textField[1]);
            frame.add(label[2]);
            frame.add(textField[2]);
            frame.add(label[3]);
            frame.add(textField[3]);
            frame.add(label[4]);
            frame.add(textArea);
            frame.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Activity activity=new Activity();
                    activity.setName(textField[1].getText());
                    activity.setTime(textField[2].getText());
                    activity.setLocation(textField[3].getText());
                    activity.setContent(textArea.getText());
                    int insertActivity;
                    try {
                        insertActivity = ActivityMapper.insertActivity(activity);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (insertActivity<=0){
                        showMessageDialog(frame,"创建失败","提示", ERROR_MESSAGE);
                    }else{
                        showMessageDialog(frame,"创建成功","提示",INFORMATION_MESSAGE);
                    }
                    dispose();
                    frame.dispose();
                    try {
                        ActivityMapper activityMapper=new ActivityMapper();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            frame.setVisible(true);
        } else if (e.getSource()==button2) {
            Coordinate.point=this.getLocation();
            JFrame frame=new JFrame();
            frame.setBounds(Coordinate.point.x, Coordinate.point.y, 600, 400);
            frame.setLayout(new FlowLayout());
            JLabel label=new JLabel("请输入要删除的活动名");
            JTextField textField=new JTextField(15);
            JButton button=new JButton("确定");
            frame.add(label);
            frame.add(textField);
            frame.add(button);
            Object[][] select;
            try {
                select = ActivityMapper.select();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            tableModel = new DefaultTableModel(select, columnName);
            //System.out.println(columnName.toString());
            table=new JTable(tableModel);
            JScrollPane scrollPane=new JScrollPane(table);
            frame.add(scrollPane);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = textField.getText();
                    int delectActivity;
                    try {
                        delectActivity = ActivityMapper.delectActivity(text);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (delectActivity<=0){
                        showMessageDialog(frame,"删除失败","提示", ERROR_MESSAGE);
                    }else{
                        showMessageDialog(frame,"删除成功","提示", INFORMATION_MESSAGE);
                    }
                    dispose();
                    frame.dispose();
                    try {
                        ActivityMapper activityMapper=new ActivityMapper();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            frame.setVisible(true);
        }
    }
}
