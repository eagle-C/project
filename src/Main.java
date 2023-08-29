import Java.dao.SQL_Readin;
import Java.utils.Login_In;

import java.sql.SQLException;

// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。

public class Main {
    public static void main(String[] args) throws SQLException {
        // 当文本光标位于高亮显示的文本处时按 Alt+Enter，
        // 可查看 IntelliJ IDEA 对于如何修正该问题的建议。
        System.out.printf("Hello and welcome!");
        System.out.printf("Hello and welcome!1");
        System.out.printf("Hello and welcome!2");
        System.out.println("hotfix");
        System.out.println("hotfix3");
        System.out.println("aaa");
        SQL_Readin.registersql();
//        StudentManagement studentManagement=new StudentManagement();
//        ClassCommittee classCommittee=new ClassCommittee();
//        ActivityMapper activityMapper=new ActivityMapper();
        Login_In loginIn=new Login_In();
//        Graph graph=new Graph();
        // 按 Shift+F10 或点击间距中的绿色箭头按钮以运行脚本。
    }
}