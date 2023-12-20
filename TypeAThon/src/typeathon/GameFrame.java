import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    int width = 1200;
    int height = 800;

    int curIndex;

    List<Character> game_char;//绘制的单词
    List<Integer> x_value;//记录单词的x位置
    List<Integer> y_value;//记录单词的y位置

    TypeA dataManager;//数据管理

    Graphics m_graphics;//存储画布

    int gameState;

    int game_time;
    boolean isSymbol;//是否加入符号
    int GameType;//游戏类型

    int word_num;

    String file_name,user;

    GameFrame(String user,int gameType, int word_num, int time,String filename, boolean symbol) {
        this.user = user;
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getLayeredPane().setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        this.setVisible(true);
        //设置游戏时长
        game_time = time;
        isSymbol = symbol;
        GameType = gameType;
        this.word_num = word_num;
        this.file_name = filename;
        //实例化对象
        game_char = new ArrayList<>();
        x_value = new ArrayList<>();
        y_value = new ArrayList<>();
        //读取单词
        dataManager = new TypeA("src/word.txt");
        //读取画布
        m_graphics = getGraphics();
        //初始化游戏
        InitialGame(true);

        //添加按键事件
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case KeyEvent.VK_ENTER:
                        if (gameState == GameProperty.GAME_READY) {
                            GenerateBackgroundText();
                            gameState = GameProperty.GAME_ING;
                            //如果是经典模式就开始记时
                            switch (GameType) {
                                case GameProperty.GAME_CLASSIC:
                                case GameProperty.GAME_QUOTE:
                                    startCountdownTask();
                                    break;
                                case GameProperty.GAME_WORD:
                                case GameProperty.GAME_DEATH:
                                    startTickTask();
                                    break;
                            }
                        }
                        break;
                    case KeyEvent.VK_BACK_SPACE://回退
                        if (curIndex > -1) {
                            paint_word(Color.gray);
                            dataManager.Backspace();
                            curIndex--;
                            if (curIndex != -1 && game_char.get(curIndex) == ' ')
                                curIndex--;
                        }
                        break;

                    default:
                        if (gameState == GameProperty.GAME_ING) {
                            // 处理按键事件
                            char keyChar = e.getKeyChar();
                            curIndex++;
                            if (game_char.get(curIndex) == ' ')
                                curIndex++;
                            if (dataManager.cheack(keyChar))
                                paint_word(Color.green);
                            else
                            {
                                paint_word(Color.red);
                                if(GameType==GameProperty.GAME_DEATH)
                                    gameState = GameProperty.GAME_OVER;

                            }
                            //游戏结束,显示时长
                            if ((game_char.get(game_char.size() - 1) == ' ' && curIndex == game_char.size() - 2) ||
                                    (curIndex == game_char.size() - 1)) {
                                gameState = GameProperty.GAME_OVER;
                            }
                        }

                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // 按键被按下时触发的事件
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // 按键被释放时触发的事件
            }
        });


    }

    private void InitialGame(boolean shuffle) {
        //开始游戏，并打乱单词排序
        switch (GameType) {
            case GameProperty.GAME_CLASSIC:
            case GameProperty.GAME_DEATH:
                dataManager.StartGame(shuffle, isSymbol);
                break;
            case GameProperty.GAME_WORD:
                dataManager.StartGame(shuffle,isSymbol, word_num);
                break;
            case GameProperty.GAME_QUOTE:
                dataManager.StartGame(file_name);
                break;

        }
        game_char.clear();
        //获取单词，并拆分
        List<String> info = dataManager.GetGameInfo();
        for (String s : info) {
            for (int j = 0; j < s.length(); j++) {
                game_char.add(s.charAt(j));
            }
        }
        //设置当前指针
        curIndex = -1;
        //设置游戏状态
        gameState = GameProperty.GAME_READY;
        //生成单词的位置信息
        Generate_pos();
    }

    public void paint_word(Color color) {
        int x = 20, y = 50;
        String draw_char;
        m_graphics.setColor(color);
        m_graphics.drawString(game_char.get(curIndex).toString(), x_value.get(curIndex), y_value.get(curIndex));
    }

    //生成单词的位置坐标
    private void Generate_pos() {
        x_value.clear();
        y_value.clear();
        //设置字体
        m_graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        FontMetrics fontMetrics = m_graphics.getFontMetrics();
        int x = GameProperty.X_BEGIN_DRAW, y = GameProperty.Y_BEGIN_DRAW;
        for (int i = 0; i < game_char.size(); i++) {
            x_value.add(x);
            y_value.add(y);
            x += fontMetrics.stringWidth(game_char.get(i).toString());
            if (x >= width - 20) {
                y += fontMetrics.getHeight() + 5;
                x = GameProperty.X_BEGIN_DRAW;
            }
        }
    }

    //生成背景文字
    void GenerateBackgroundText() {
        m_graphics.setColor(Color.gray);
        for (int i = 0; i < game_char.size(); i++) {
            m_graphics.drawString(game_char.get(i).toString(), x_value.get(i), y_value.get(i));
        }
    }

    //倒计时任务
    private void startCountdownTask() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // 执行耗时任务，即倒计时
                for (int i = game_time * 10; i > 0; i--) {
                    // 更新倒计时文本或其他操作
                    if (i % 10 == 0)
                        System.out.println("Remaining time: " + i / 10+" WPM:"+dataManager.StaticsWPM(game_time-i/10+1));
                    Thread.sleep(100); // 休眠1秒，模拟耗时操作
                }
                return null;
            }

            @Override
            protected void done() {
                float wpm = dataManager.StaticsWPM(game_time);
                float accuracy = dataManager.StaticsAccuracy() * 100;
                //写入数据
                FileBasedAuthentication.addGameData(user,wpm,accuracy);
                // 耗时任务完成后的操作，弹出带有两个按钮的窗口
                String[] options = {"rechallenge", "New challenge"};
                int result = JOptionPane.showOptionDialog(null,
                        String.format("Game over！\nwpm:%s\naccuracy:%s%%", wpm, accuracy),
                        "",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);
                gameState = GameProperty.GAME_OVER;
                repaint();
                // 根据用户的选择执行不同的操作
                switch (result) {
                    case 0:
                        InitialGame(false);
                        break;
                    case 1:
                        InitialGame(true);
                        break;
                    default:
                        // 默认情况，可以忽略或执行其他操作
                        break;
                }
            }

        };

        // 启动 SwingWorker
        worker.execute();
    }

    int time_count;
    //倒计时任务
    private void startTickTask() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                time_count = 0;
                // 执行耗时任务，即倒计时
                while (true) {
                    if (gameState == GameProperty.GAME_OVER)
                        return null;
                    Thread.sleep(1000); // 休眠1秒，模拟耗时操作
                    time_count++;
                    System.out.println("Past time: "+time_count+" WPM："+dataManager.StaticsWPM(time_count));
                }
            }

            @Override
            protected void done() {
                float wpm = dataManager.StaticsWPM(time_count);
                float accuracy = dataManager.StaticsAccuracy() * 100;
                //写入数据
                FileBasedAuthentication.addGameData(user,wpm,accuracy);

                // 耗时任务完成后的操作，弹出带有两个按钮的窗口
                String[] options = {"rechallenge", "New challenge"};
                int result = JOptionPane.showOptionDialog(null,
                        String.format("Game over！\n用时:%d\nwpm:%s\n correct rate:%s%%",time_count, wpm, accuracy),
                        "",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);
                gameState = GameProperty.GAME_OVER;
                repaint();
                // 根据用户的选择执行不同的操作
                switch (result) {
                    case 0:
                        InitialGame(false);
                        break;
                    case 1:
                        InitialGame(true);
                        break;
                    default:
                        // 默认情况，可以忽略或执行其他操作
                        break;
                }
            }
        };

        // 启动 SwingWorker
        worker.execute();
    }
}
