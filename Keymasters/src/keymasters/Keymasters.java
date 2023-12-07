
package keymasters;

import java.util.Random;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
public class Keymasters {

    public static void main(String[] args) {
        System.out.println("Hello");
        System.out.println("This is newly added words from branch.");
        System.out.println("github is goated");
        
        JFrame frame=new JFrame();
        JPanel panel=new JPanel();
        JButton button=new JButton("Click");
        Color c=new Color(100,105,200);
        
        panel.setBackground(c);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(button);
        
        frame.getContentPane().setBackground(c);
        frame.getContentPane().add(panel,BorderLayout.CENTER);
        
        frame.setSize(400,500);
        frame.setVisible(true);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }
    
}
