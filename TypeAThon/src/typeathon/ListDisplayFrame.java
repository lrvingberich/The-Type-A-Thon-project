import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListDisplayFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public ListDisplayFrame(String user) {

        // 创建表格模型
        tableModel = new DefaultTableModel();
        // 添加列名到表格模型
        tableModel.addColumn("WPM"); // 列名为 "WPM"
        tableModel.addColumn("Accuracy"); // 列名为 "Accuracy"

        table = new JTable(tableModel);



        // 设置窗口属性
        setSize(400, 300);
        setLocationRelativeTo(null); // 让窗口居中显示]

        List<FileBasedAuthentication.GameData> data = FileBasedAuthentication.getAllGameData(user);
        for(int i =0;i<data.size();i++)
        {
            Object[] rowData = {String.valueOf(data.get(i).getWpm()),String.valueOf(data.get(i).getAccuracy())};
            addData(rowData);
        }

        // 添加表格到滚动窗格中
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        // 添加滚动窗格到窗口中
        add(scrollPane);

        setVisible(true);
    }

    // 添加数据到表格模型
    public void addData(Object[] rowData) {
        tableModel.addRow(rowData);
    }
}
