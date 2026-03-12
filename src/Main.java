import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        //配列を受け取る。空なら返す。
        if (args.length >= 3) {
            List<String> samples = Scraper.Scraping(args[1], args[2]);
            for (int i = 0; i < samples.size(); i += 2) {
                BuildProcess.run(args[0], samples.get(i), samples.get(i+1));
            }
        } else {
            System.out.println("引数が不正です。");
        }
    }
}