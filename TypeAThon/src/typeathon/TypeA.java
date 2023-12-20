import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TypeA {
    List<String> words;//记录所有的单词
    List<String> game_words;//游戏单词

    int word_index;//单词指针
    int char_index;//字符指针
    int correct_num;//正确输入的数目
    int wrong_num;//错误输入的数目

    public TypeA(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            //读取单词内容
            words = new ArrayList<>(List.of(reader.readLine().split(" ")));
            game_words = new ArrayList<>(words);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //倒计时模式
    public void StartGame(boolean shuffle, boolean symbol) {
        if (shuffle) {
            //打乱单词的顺序
            Collections.shuffle(words);
            game_words.clear();
            String[] charList = {" ", ",", "!", "?", ";"};
            Random random = new Random();
            int randomIndex;
            for (String word : words) {
                game_words.add(word);
                if (symbol)//中间加入符号
                {
                    randomIndex = random.nextInt(charList.length );
                    game_words.add(charList[randomIndex]);

                } else
                    game_words.add(" ");
            }
        }
        //初始化变量
        correct_num = wrong_num = 0;
        word_index = 0;
        char_index = -1;
    }

    //有限单词模式
    public void StartGame(boolean shuffle,boolean symbol,int number) {
        if (shuffle) {
            //打乱单词的顺序
            Collections.shuffle(words);
            game_words.clear();
            String[] charList = {" ", ",", "!", "?", ";"};
            Random random = new Random();
            int randomIndex;
            for (int i = 0; i < number; i++) {
                game_words.add(words.get(i));
                if (symbol)//中间加入符号
                {
                    randomIndex = random.nextInt(charList.length );
                    game_words.add(charList[randomIndex]);

                } else
                    game_words.add(" ");
            }
        }
        //初始化变量
        correct_num = wrong_num = 0;
        word_index = 0;
        char_index = -1;
    }


    //引文模式
    public void StartGame(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            //读取单词内容
            words = new ArrayList<>(List.of(reader.readLine().split(" ")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        game_words.clear();
        for (int i = 0; i < words.size(); i++) {
            game_words.add(words.get(i));
            game_words.add(" ");

            //初始化变量
            correct_num = wrong_num = 0;
            word_index = 0;
            char_index = -1;
        }
    }

    //获取游戏信息
    public List<String> GetGameInfo() {
        return game_words;
    }

    //检查是否输入正确
    public boolean cheack(char val) {
        //确保不会越界
        char_index++;
        if (game_words.get(word_index).length() == char_index) {
            word_index++;
            char_index = 0;
        }
        //如果是空格则跳过
        if (game_words.get(word_index).equals(" "))
            word_index++;
        //判断是否输入正确
        if (game_words.get(word_index).charAt(char_index) == val) {
            correct_num++;
            return true;
        }

        wrong_num += 1;
        return false;
    }

    //统计WPM
    public float StaticsWPM(int second) {
        return (float) correct_num * 60 / 5 / second;
    }

    //统计正确率
    public float StaticsAccuracy() {
        return (float) correct_num / (correct_num + wrong_num);
    }

    //后退
    public void Backspace() {
        char_index--;
        if (char_index == -1 && word_index != 0) {
            word_index -= 1;
            char_index = game_words.get(word_index).length() - 1;
        }
        //如果是空格则跳过
        if (game_words.get(word_index).equals(" "))
        {
            word_index--;
            char_index = game_words.get(word_index).length() - 1;
        }
    }

    //跳转到下一个单词
    public void Jump() {
        char_index = 0;
        word_index++;
    }
}