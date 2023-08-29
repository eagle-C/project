package Java.utils;
import Java.dao.SQL_Readin;
import Java.pojo.Coordinate;
import Java.pojo.User;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
public class Class_Expense extends JFrame implements ActionListener, TableModelListener {
    private ArrayList<String> dormitory_numbers = new ArrayList<String>();
    private JTable table;
    private TableModel model;
    private Object columnName[] = {"名称", "金额", "说明"};
    private PreparedStatement cleaning = null;
    private Box boxH;
    private Box boxV;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextField textField;
    static Object[][] data;

    public Class_Expense(String inserts) {
        setJTable(inserts);//第一次建表，选中所有数据
        setBounds(Coordinate.point.x, Coordinate.point.y, 600, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        boxH = Box.createHorizontalBox();
        boxV = Box.createVerticalBox();
        button1 = new JButton("录入");
        button2 = new JButton("提交");
        button3 = new JButton("删除");
        button4 = new JButton("查询");
        textField = new JTextField(20);
        model = new DefaultTableModel(data, columnName);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        boxH.add(button1);
        boxH.add(button2);
        boxH.add(button3);
        boxH.add(textField);
        boxH.add(button4);
        boxV.add(boxH);
        boxV.add(scrollPane);
        add(boxV);
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        model.addTableModelListener(this);
        setVisible(true);
        addWindowListener(new Close_WindowListener());
        if(User.identity==false)//为学生，禁止一部分操作
        {
            button1.setVisible(false);
            button2.setVisible(false);
            button3.setVisible(false);
        }
    }

    private void setJTable(String inserts) {
        ArrayList<Object[]> array = new ArrayList<>();
        try {
            cleaning = SQL_Readin.con.prepareStatement(inserts);//首先读入所有数据
            SQL_Readin.activatesql(cleaning);//执行查找
            while (SQL_Readin.rs.next()) {
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(SQL_Readin.rs.getString(1));
                temp.add(SQL_Readin.rs.getString(2));
                temp.add(SQL_Readin.rs.getString(3));
                array.add(temp.toArray());
            }
            data = new Object[array.size()][3];
            for (int i = 0; i < array.size(); i++)
                for (int j = 0; j < 3; j++)
                    data[i][j] = array.get(i)[j];
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button1)//录入
        {
            Point p = getLocation();
            dispose();
            addnewdata_forexpense insertnewdata = new addnewdata_forexpense(p, getWidth(), getHeight());
        } else if (e.getSource() == button2)//修改
        {
            for (String temp : dormitory_numbers) {
                try {
                    PreparedStatement temper = SQL_Readin.con.prepareStatement(temp);
                    temper.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            dispose();
            Class_Expense newrefresh = new Class_Expense("select * from expense_management");
        } else if (e.getSource() == button3)//删除
        {
            dispose();
            deletedata_forcash we = new deletedata_forcash();
        } else if (e.getSource() == button4)//查询
        {
            String temp = textField.getText();
            String factory = "select * from expense_management where expense_name like '%" + temp + "%'"
                    + " or expense_amount = '" + temp + "'"
                    + " or expense_description like '%" + temp + "%'";
            dispose();
            Class_Expense reopen = new Class_Expense(factory);
        }
    }
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if (column == 0) {
            String name = table.getValueAt(row, column).toString();
            String sql = "UPDATE expense_management SET expense_name = '" + name + "' WHERE expense_amount = "
                    + table.getValueAt(row, column + 1)+ " AND expense_description = '" + table.getValueAt(row, column + 2) + "';";
            dormitory_numbers.add(sql);
        }
        else {
            String name = table.getValueAt(row, column).toString();
            String sql = "UPDATE expense_management SET expense_amount = " + table.getValueAt(row, 1) +
                    ", expense_description = '" + table.getValueAt(row, 2) +
                    "' where expense_name = '" + table.getValueAt(row, 0) + "'";
            dormitory_numbers.add(sql);
        }
    }
    class Close_WindowListener extends WindowAdapter {//当用户退出窗口时关闭数据库链接
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            Graph graph=new Graph();
            System.out.println("打开新窗口");
        }
    }

}
class addnewdata_forexpense extends JFrame implements ActionListener
{
    addnewdata_forexpense(Point point, int x, int y)
    {
        init(point,x,y);
    }
    JLabel label1=new JLabel("名称");
    JLabel label2=new JLabel("日期");
    JLabel label3=new JLabel("金额");
    JLabel label4=new JLabel("说明");
    JTextField textField1=new JTextField(15);
    JTextField textField2=new JTextField(15);
    JTextField textField3=new JTextField(15);
    JTextField textField4=new JTextField(15);
    JButton button=new JButton("确定");
    JButton exitadd=new JButton("退出");
    private void init(Point point,int width,int height) {
        setBounds(point.x, point.y, width,height);
        setLocation(point.x, point.y);
        setLayout(new FlowLayout());
        add(label1);add(textField1);
        add(label3);add(textField3);
        add(label4);add(textField4);
        add(button);add(exitadd);
//        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // 设置窗口关闭时隐藏窗口而不关闭程序
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        button.addActionListener(this);
        exitadd.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==button)
        {
            String s1=textField1.getText();
            LocalDateTime currentDateTime = LocalDateTime.now();
            String s2=currentDateTime.toString();
            String s3=textField3.getText();
            String s4=textField4.getText();
            if(s1.equals("")||s3.equals("")||s4.equals(""))
                JOptionPane.showMessageDialog(null,"请输入完整信息");
            else
            {
                try {
                    PreparedStatement adddata= SQL_Readin.con.prepareStatement("insert into expense_management values(?,?,?)");
                    adddata.setString(1,s1);
                    adddata.setDouble(2, Double.parseDouble(s3));adddata.setString(3,s4);
                    adddata.executeUpdate();
                    textField1.setText("");textField3.setText("");textField4.setText("");
                } catch (SQLException ex) {throw new RuntimeException(ex);}
            }
        }
        else if(e.getSource()==exitadd)
        {
            dispose();
            Class_Expense newwindow=new Class_Expense("select * from expense_management");
        }
    }
}
class deletedata_forcash extends JFrame implements ActionListener
{
    JTextField del;
    JButton delete;
    JButton exitdel;
    JLabel number;

    deletedata_forcash()
    {
        init();
    }
    private void init()
    {
        setTitle("删除信息");
        setSize(300,200);
        setLocation(400,250);
        setLayout(new FlowLayout());
        setVisible(true);
        setResizable(false);
//        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        number=new JLabel("请输入要删除的名称");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(number);
        del=new JTextField(10);
        del.setBounds(50,50,150,30);
        add(del);
        delete=new JButton("删除");
        exitdel=new JButton("退出");
        add(delete);add(exitdel);
        delete.addActionListener(this);
        exitdel.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==delete)
        {
            String s1=del.getText();
            try {
                PreparedStatement adddata= SQL_Readin.con.prepareStatement("delete from expense_management where expense_name like '%"+s1+"%'");
                adddata.executeUpdate();
                del.setText("");
            } catch (SQLException ex) {throw new RuntimeException(ex);}
        }
        else if(e.getSource()==exitdel)
        {
            dispose();
            Class_Expense newwindow=new Class_Expense("select * from expense_management");
        }
    }
}


