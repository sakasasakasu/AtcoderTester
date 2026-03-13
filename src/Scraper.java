import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
    public static List<String> Scraping(String args, String names) {
        List<String> samples = new ArrayList<>();
        try {
            Document document;
            document = Jsoup.connect("https://atcoder.jp/contests/" + args + "/tasks/" + args + "_" + names).get();
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
            System.out.println("スクレイプドアンサーイズ:" + samples);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return samples;
    }
}