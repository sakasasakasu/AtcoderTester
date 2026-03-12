import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
    public static List<String> Scraping(String args, String names) throws IOException {
        List<String> samples = new ArrayList<>();
        try {
            Document document = Jsoup.connect("https://atcoder.jp/contests/" + args + "/tasks/" + args + "_" + names).get();
            Elements parts = document.select("div.part");
            System.out.println("https://atcoder.jp/contests/" + args + "/tasks/" + args + "_" + names);

            //タグはあとから付け足されてるみたいなので、HTMLのタグと文字列から取得する。
            for (Element part : parts) {
                Element h3 = part.selectFirst("h3");
                Element pre = part.selectFirst("pre");

                if (h3 != null && pre != null) {
                    String title = h3.text();
                    String content = pre.text();

                    if (title.contains("入力例") || title.contains("出力例")) {
                        samples.add(content);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
        }
        return samples;
    }
}