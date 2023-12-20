import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class RinkFrame extends JFrame {

        private JTable table;
        private DefaultTableModel tableModel;

        public RinkFrame() {

            // 创建表格模型
            tableModel = new DefaultTableModel();
            // 添加列名到表格模型
            tableModel.addColumn("user"); // 列名为 "WPM"
            tableModel.addColumn("WPM"); // 列名为 "Accuracy"

            table = new JTable(tableModel);



            // 设置窗口属性
            setSize(400, 300);
            setLocationRelativeTo(null); // 让窗口居中显示]

            List<FileBasedAuthentication.GameData> data = FileBasedAuthentication.getRanking();
            for(int i =0;i<data.size();i++)
            {
                Object[] rowData = {data.get(i).getUser(),String.valueOf(data.get(i).getWpm())};
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
