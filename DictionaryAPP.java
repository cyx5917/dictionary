package dictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DictionaryAPP {

    private static Map<String, String> dictionary = new HashMap<>();

    public static void main(String[] args) {

        // 读取 niujin.txt
        loadDictionaryFromTxt("niujin.txt");

        JFrame frame = new JFrame("查字典（NIUJIN 英汉词典）");
        frame.setSize(500, 330);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("英  文  →  中  文 释 义", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 22));
        frame.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new FlowLayout());
        JTextField inputField = new JTextField(20);
        JButton searchBtn = new JButton("查询");

        panel.add(new JLabel("单词："));
        panel.add(inputField);
        panel.add(searchBtn);
        frame.add(panel, BorderLayout.CENTER);

        JTextArea resultArea = new JTextArea(8, 45);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        resultArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.add(scrollPane, BorderLayout.SOUTH);

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = inputField.getText().trim().toLowerCase();

                if (word.isEmpty()) {
                    resultArea.setText("请输入一个英文单词！");
                    return;
                }

                if (dictionary.containsKey(word)) {
                    resultArea.setText("【" + word + "】 的释义：\n" + dictionary.get(word));
                } else {
                    resultArea.setText("字典中未找到该单词。");
                }
            }
        });

        frame.setVisible(true);
    }

    // 按 TAB 分隔：word[TAB]meaning
    private static void loadDictionaryFromTxt(String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename), "UTF-8"))) {

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) continue;

                // 按 TAB 拆分
                String[] parts = line.split("\t", 2);
                if (parts.length < 2) continue;

                String word = parts[0].trim().toLowerCase();
                String meaning = parts[1].trim();

                dictionary.put(word, meaning);
            }

            System.out.println("已加载 niujin.txt，共 " + dictionary.size() + " 条词条。");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "无法读取词典文件：" + filename + "\n请确认文件存在。",
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

