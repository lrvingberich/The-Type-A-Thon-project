import javax.swing.*;
import java.awt.*;

/*在一个面板上实现标题自动下落*/
class Title extends JPanel implements Runnable {
    int width = 500;
    int height = 150;
    int N;
    int[] x;//存储标题中的每个字的横坐标
    int[] y;//存储标题中的每个字的纵坐标
    String[] strs;

    Title() {
        strs =  new String[]{"T","Y","P","E","A","T","H","O","N"};
        N=strs.length;
        x  = new int[N];
        y = new int[N];

        setBounds(0, 0, width, height);//设置面板大小
        setOpaque(false);//透明
        setplace();//设置标题每个字初始的横纵坐标
    }

    void setplace() {
        for (int i = 0; i < N; i++) {
            x[i] = (int) ((width-N*3) / N * i)+20;
            y[i] = 10;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);//设置画笔颜色为红
        g.setFont(new Font("Arial", Font.PLAIN, 50)); // 设置画笔字体
        for (int i = 0; i < N; i++) {
            g.drawString(strs[i], x[i], y[i]);//在指定位置画出标题的字
            y[i]++;//标题的字纵坐标下移一像素
            if (y[i] > height - 50) {//如果到达height-50，则保持在那个位置
                y[i] = height - 50;
            }
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);//实现每10毫秒重绘一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();//调用重绘函数
        }
    }
}