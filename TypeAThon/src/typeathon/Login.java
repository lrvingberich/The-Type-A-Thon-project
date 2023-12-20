import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public Login(String title) {
        super(title);

        // 设置窗口属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // 让窗口居中显示
        setVisible(true);
    }

    public void createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // 添加用户名和密码的标签和文本框
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // 添加登录按钮的监听器
        loginButton = new JButton("Log in");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理登录按钮点击事件
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                // 在这里添加处理登录逻辑的代码
                // 例如，检查用户名和密码是否匹配数据库中的记录
                System.out.println("Logging in: " + username);
                // 清空文本框
                usernameField.setText("");
                passwordField.setText("");

                if(FileBasedAuthentication.loginUser(username,String.valueOf(password)))
                    new UIFrame(username);
                else
                    System.out.println("Login failure");
            }
        });
        panel.add(loginButton);

        // 添加注册按钮的监听器
        registerButton = new JButton("Sign in");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在此处理注册按钮点击事件
                openRegisterFrame();
            }
        });
        panel.add(registerButton);

//        setContentPane(panel);
        add(panel);
        setVisible(true);


    }

    public void createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // 添加用户名和密码的标签和文本框
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // 添加注册按钮的监听器
        registerButton = new JButton("Sign in");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理注册按钮点击事件
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                // 在这里添加处理注册逻辑的代码
                // 例如，将用户名和密码保存到数据库
                System.out.println("Registering: " + username);
                // 清空文本框
                usernameField.setText("");
                passwordField.setText("");

                if(FileBasedAuthentication.registerUser("tets",username,String.valueOf(password)))
                    System.out.println("Registered successfully");
                else
                    System.out.println("Registration failure");

            }
        });
        panel.add(registerButton);

        setContentPane(panel);
        setVisible(true);
    }

    private void openRegisterFrame() {
        Login registerFrame = new Login("Register");
        registerFrame.createRegisterPanel();
    }
}
