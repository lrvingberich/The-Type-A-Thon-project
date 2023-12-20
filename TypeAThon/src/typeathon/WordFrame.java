import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordFrame extends JFrame {

    int width = 300;
    int height = 150;
    private Timer timer;
    private JLabel timerLabel;
    private JTextField timeTextField;
    private JRadioButton radioModeCountdown;
    private JRadioButton radioModeCountup;

    String user;

    WordFrame(String text) {
        user = text;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getLayeredPane().setLayout(null);
        // 设置窗口属性
        setTitle("Timer example");
        setSize(width, height);

        // 创建面板并设置为垂直排列
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // 创建文本框用于输入时间
        timeTextField = new JTextField();
        // 设置文本框的首选大小
        timeTextField.setPreferredSize(new Dimension(200, 30));

        // 创建单选按钮
        radioModeCountdown = new JRadioButton("Pure mode");
        radioModeCountup = new JRadioButton("Add punctuation");

        // 将单选按钮放入组，确保只能选择一个
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioModeCountdown);
        radioGroup.add(radioModeCountup);

        // 默认选择倒计时模式
        radioModeCountdown.setSelected(true);

        // 创建标签用于显示倒计时
        timerLabel = new JLabel("Number of words：");

        // 创建按钮
        JButton startButton = new JButton("Start the game");

        // 添加组件到面板
        mainPanel.add(timerLabel);
        mainPanel.add(timeTextField);
        mainPanel.add(radioModeCountdown);
        mainPanel.add(radioModeCountup);
        mainPanel.add(startButton);

        // 添加面板到窗口
        add(mainPanel);

        // 显示窗口
        setVisible(true);
        //绑定事件
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的时间
                int seconds;
                try {
                    seconds = Integer.parseInt(timeTextField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(WordFrame.this, "Please enter a valid number！", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean symbol = radioModeCountup.isSelected();

                new GameFrame(user,GameProperty.GAME_WORD,seconds,0,"",symbol);
            }
        });
    }
}
