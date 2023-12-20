import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
public class UIFrame extends JFrame {
    int width = 500;
    int height = 700;

    String user;
    Font font = new Font("Verdana", Font.PLAIN, 20); // 设置字体
    JLabel info = new JLabel("Game data");
    JLabel Sort = new JLabel("Leaderboard");
    JLabel Timed = new JLabel("Timing mode");
    JLabel Words = new JLabel("Word pattern");
    JLabel Quotes = new JLabel("Citation pattern");
    JLabel Death = new JLabel("Sudden death model");

    /*主界面设置*/
    public UIFrame(String text) {
        user = text;
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getLayeredPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Title title = new Title();//新建一个标题对象
        this.add(title);//往窗口中加入标题面板
        Thread t = new Thread(title);//将标题面板加入一个线程
        t.start();//启动线程，实现标题面板下落

        buildButton();
        add_JB_Listener();
        this.setVisible(true);
    }

    /*设置按钮规格*/
    public void buildButton() {
        info.setForeground(Color.red);
        Sort.setForeground(Color.red);
        Timed.setForeground(Color.red);
        Words.setForeground(Color.red);
        Quotes.setForeground(Color.red);
        Death.setForeground(Color.red);
        info.setFont(font);
        Sort.setFont(font);
        Timed.setFont(font);
        Words.setFont(font);
        Quotes.setFont(font);
        Death.setFont(font);
        info.setBounds(width / 3, height * 2 / 10, width / 3, 50);
        Sort.setBounds(width / 3, height * 3 / 10 ,width / 3, 50);
        Timed.setBounds(width / 3, height * 4 / 10, width / 3, 50);
        Words.setBounds(width / 3, height * 5 / 10, width / 3, 50);
        Quotes.setBounds(width / 3, height * 6 / 10, width / 3, 50);
        Death.setBounds(width / 3, height * 7 / 10, width / 3, 50);
        info.setHorizontalAlignment(JLabel.CENTER);
        Sort.setHorizontalAlignment(JLabel.CENTER);
        Timed.setHorizontalAlignment(JLabel.CENTER);
        Words.setHorizontalAlignment(JLabel.CENTER);
        Quotes.setHorizontalAlignment(JLabel.CENTER);
        Death.setHorizontalAlignment(JLabel.CENTER);

        this.add(info);
        this.add(Sort);
        this.add(Timed);
        this.add(Words);
        this.add(Quotes);
        this.add(Death);
    }

    /*按钮添加监听器*/
    public void add_JB_Listener() {

        Timed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TimedFrame timedFrame = new TimedFrame(user);
            }

            public void mouseEntered(MouseEvent e) {
                Timed.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Timed.setBorder(null);
            }
        });

        Words.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new WordFrame(user);
            }

            public void mouseEntered(MouseEvent e) {
                Words.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Words.setBorder(null);
            }
        });

        Quotes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new QuoteFrame(user);
            }

            public void mouseEntered(MouseEvent e) {
                Quotes.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Quotes.setBorder(null);
            }
        });

        Death.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new DeathFrame("");
            }

            public void mouseEntered(MouseEvent e) {
                Death.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Death.setBorder(null);
            }
        });


        Sort.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RinkFrame();
            }

            public void mouseEntered(MouseEvent e) {
                Sort.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Sort.setBorder(null);
            }
        });


        info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ListDisplayFrame(user);
            }

            public void mouseEntered(MouseEvent e) {
                info.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                info.setBorder(null);
            }
        });

    }
}
