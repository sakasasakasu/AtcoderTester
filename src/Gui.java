import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Gui {
    public static void main(String[] args) {
        JFrame frame = new JFrame("AtcoderTester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);

        //親ヘッダー
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        //子ヘッダー（設定とか）
        JPanel header = new JPanel();
        JTextField contestNum = new JTextField("abc123",10);
        JTextField contestId = new JTextField("a", 3);
        JButton runButton = new JButton("テスト実行");

        header.add(new JLabel("コンテスト:"));
        header.add(contestNum);
        header.add(new JLabel("問題:"));
        header.add(contestId);
        header.add(runButton);

        //子ヘッダー（詳細設定）
        JPanel advancedHeader = new JPanel();
        JTextField limitMemory = new JTextField("1024",10);
        JTextField limitTime = new JTextField("2",3);

        advancedHeader.add(new JLabel("メモリ制限:"));
        advancedHeader.add(limitMemory);
        advancedHeader.add(new JLabel("MiB　　"));
        advancedHeader.add(new JLabel("時間制限:"));
        advancedHeader.add(limitTime);
        advancedHeader.add(new JLabel("s"));
        advancedHeader.setVisible(false);

        JButton toggleButton = new JButton("詳細設定を表示 ▽");
        toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        toggleButton.addActionListener(e -> {
            boolean isVisible = advancedHeader.isVisible();
            advancedHeader.setVisible(!isVisible);
            toggleButton.setText(isVisible ? "詳細設定を表示 ▽" : "詳細設定を閉じる △");
            frame.revalidate();
        });

        //子要素を親にまとめる

        topPanel.add(header);
        topPanel.add(toggleButton);
        topPanel.add(advancedHeader);

        //ボディー（コードを入力する）
        JTextArea codeArea = new JTextArea("//ここにコードを貼り付ける");
        JScrollPane codeScroll = new JScrollPane(codeArea);
        codeArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        //ボトム（実行結果を確認できる！）
        JTextArea resultArea = new JTextArea(10, 30);
        JScrollPane resultScroll = new JScrollPane(resultArea);

        //組み立てる
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(codeScroll, BorderLayout.CENTER);
        frame.add(resultScroll, BorderLayout.SOUTH);

        runButton.addActionListener(e -> {

            String contestsNum = contestNum.getText();
            String contestsId = contestId.getText();
            String limitsMemory = limitMemory.getText();
            String limitsTime = limitTime.getText();

            try {
                String code = codeArea.getText();
                Files.writeString(Path.of("./main.java"),code);
                List<String> samples = Scraper.Scraping(contestsNum, contestsId);
                for (int i = 0; i < samples.size(); i += 2) {
                    String result = Executor.run("./main.java", samples.get(i), samples.get(i+1), limitsMemory, limitsTime);
                    resultArea.append("===入力例" + (i / 2 + 1) + "===" + result + "\n");
                }

            } catch (IOException ignored) {

            }

        });

        frame.setVisible(true);
    }
}
