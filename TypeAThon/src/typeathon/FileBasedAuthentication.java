import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileBasedAuthentication {

    // 游戏数据的类
    public static class GameData {
        private String user;
        private float wpm;
        private float accuracy;

        public GameData(String user,float wpm, float accuracy) {
            this.user = user;
            this.wpm = wpm;
            this.accuracy = accuracy;
        }

        public float getWpm() {
            return wpm;
        }

        public float getAccuracy() {
            return accuracy;
        }

        public String getUser()
        {
            return user;
        }
    }

    //存储路径
    private static final String FILE_PATH =  "users.txt";

    //注册
    public static boolean registerUser(String id,String username,String password) {

        File file =new File(FILE_PATH);


        if (!file.exists()) {
            try {
                // 如果文件不存在，则创建文件
                file.createNewFile();
                System.out.println("File created successfully!");
            } catch (IOException e) {
                System.out.println("Error creating file:" + e.getMessage());
            }
        }

        // 检查用户是否已存在
        if (userExists(username)) {
            System.out.println("The user already exists!");
            return false;
        }

        // 将用户信息写入文件
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            writer.println(id + "," + username + "," + password);
            System.out.println("Registered successfully！");
            return true;
        } catch (IOException e) {
            System.out.println("Registration failure：" + e.getMessage());
        }
        return false;
    }

    //登录
    public static boolean loginUser(String username,String password) {

        // 检查用户是否存在并验证密码
        if (validateUser(username, password)) {
            return true;
            // 可以在这里执行其他操作，比如显示得分等
        } else {
            return false;
        }
    }

    private static boolean userExists(String username) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 2 && userData[1].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred：" + e.getMessage());
        }
        return false;
    }

    private static boolean validateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 3 && userData[1].equals(username) && userData[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred：" + e.getMessage());
        }
        return false;
    }


    // 添加游戏数据
    public static boolean addGameData(String username, float wpm, float accuracy) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 3 && userData[1].equals(username)) {
                    if (userData.length >= 5) {
                        // 从数组索引3开始是WPM，索引4是准确率
                        StringBuilder newData = new StringBuilder(line);
                        newData.append(",").append(wpm).append(",").append(accuracy);
                        lines.add(newData.toString());
                    } else {
                        // 如果没有游戏数据，添加第一条数据
                        StringBuilder newData = new StringBuilder(line);
                        newData.append(",").append(wpm).append(",").append(accuracy);
                        lines.add(newData.toString());
                    }
                } else {
                    lines.add(line);
                }
            }

            // 写回文件
            try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
                System.out.println("Game data added successfully!");
                return true;
            } catch (IOException e) {
                System.out.println("An error occurred:" + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("An error occurred:" + e.getMessage());
        }
        return false;
    }


    // 获取用户的所有游戏数据
    public static List<GameData> getAllGameData(String username) {
        List<GameData> gameDataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 3 && userData[1].equals(username)) {
                    if (userData.length >= 5) {
                        // 游戏数据从数组索引3开始
                        for (int i = 3; i < userData.length; i += 2) {
                            float wpm = Float.parseFloat(userData[i]);
                            float accuracy = Float.parseFloat(userData[i + 1]);
                            gameDataList.add(new GameData(username,wpm, accuracy));
                        }
                    }
                    return gameDataList; // 找到用户后返回游戏数据列表
                }
            }
            System.out.println("The user is not found or the password is incorrect.");
        } catch (IOException e) {
            System.out.println("An error occurred:" + e.getMessage());
        }

        return gameDataList; // 如果未找到用户，返回空列表
    }

    // 计算前10的游戏数据
    public static List<GameData> getRanking() {
        List<List<GameData>> gameDataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 5) {
                    gameDataList.add(new ArrayList<>());
                    // 游戏数据从数组索引3开始
                    for (int i = 3; i < userData.length; i += 2) {
                        float wpm = Float.parseFloat(userData[i]);
                        float accuracy = Float.parseFloat(userData[i + 1]);
                        gameDataList.getLast().add(new GameData(userData[1], wpm, accuracy));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred:" + e.getMessage());
        }
        List<GameData> result = new ArrayList<>();
        for (int i = 0; i < gameDataList.size(); i++)
            result.add(new GameData(gameDataList.get(i).getFirst().getUser(),calculateAverageWPM(gameDataList.get(i)),0));

        Collections.sort(result, new Comparator<GameData>() {
            @Override
            public int compare(GameData o1, GameData o2) {
                if(o1.getWpm()<o2.getWpm())
                    return 1;
                else
                    return -1;
            }
        });

        return result;
    }


    //计算平均数据
    private static float calculateAverageWPM(List<GameData> data)  {
        int count = 0;
        float totalWPM = 0;
        for (int i = data.size()-1; i > -1 && i>data.size()-11; i--) {
            totalWPM += data.get(i).getWpm();
            count++;
        }
        return (float) (totalWPM / count);
    }
}
