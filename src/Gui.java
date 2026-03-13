import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Gui {
    public static void main(String[] args) {
        JFrame frame = new JFrame("AtcoderTester");
        //これで閉じたら落ちるらしい（ほんまか？）（ほんまや、、、）
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);


        //ヘッダー（設定とか）
        JPanel header = new JPanel();
        JTextField contestNum = new JTextField("abc123",10);
        JTextField contestId = new JTextField("a", 3);
        JButton runButton = new JButton("テスト実行");

        header.add(new JLabel("コンテスト:"));
        header.add(contestNum);
        header.add(new JLabel("問題:"));
        header.add(contestId);
        header.add(runButton);

        //ボディー（コードを入力する）
        JTextArea codeArea = new JTextArea("//ここにコードを貼り付ける");
        JScrollPane codeScroll = new JScrollPane(codeArea);
        codeArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        //ボトム（実行結果を確認できる！）
        JTextArea resultArea = new JTextArea(10, 30);
        JScrollPane resultScroll = new JScrollPane(resultArea);

        //組み立てる
        frame.add(header, BorderLayout.NORTH);
        frame.add(codeScroll, BorderLayout.CENTER);
        frame.add(resultScroll, BorderLayout.SOUTH);

        runButton.addActionListener(e -> {

            String contestsNum = contestNum.getText();
            String contestsId = contestId.getText();

            try {
                String code = codeArea.getText();
                Files.writeString(Path.of("./main.java"),code);
                System.out.println("BuildProcessを実行します");

                List<String> samples = Scraper.Scraping(contestsNum, contestsId);

                for (int i = 0; i < samples.size(); i += 2) {
                    String result = Executor.run("./main.java", samples.get(i), samples.get(i+1));
                    resultArea.append("===入力例" + (i / 2 + 1) + "===" + result + "\n");
                }

            } catch (IOException ignored) {

            }

        });

        frame.setVisible(true);
    }
}
