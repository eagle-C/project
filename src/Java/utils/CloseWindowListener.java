package Java.utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseWindowListener extends WindowAdapter {//当用户退出窗口时关闭数据库链接
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            Graph graph=new Graph();
            System.out.println("打开新窗口");
        }
}