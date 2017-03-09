package jsoup;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Weather {
    
    public  Document getDocument (String url){
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Weather t = new Weather();
        Document doc = t.getDocument("http://www.weather.com.cn/html/weather/101280101.shtml");
        // 获取目标HTML代码
        Elements elements1 = doc.select("li[class=sky skyid lv3 on]");
        // 今天
        Elements elements2 = elements1.select("h1");
        String today = elements2.get(0).text(); //get(0)针对elements，而children和child针对是element，用children之后获得此element所有子元素
        System.out.println(today);
        // 是否有雨
        Elements elements4 = elements1.select("[class=wea]");
        String rain = elements4.get(0).text();
        System.out.println(rain);
        // 高的温度
        Elements elements5 = elements1.select("span");
        String highTemperature = elements5.get(0).text()+"°C";
        System.out.println(highTemperature);
        // 低的温度
        Elements elementsffs = elements1.select("[class=tem]");
        String lowTemperature = elementsffs.select("i").text();
        System.out.println(lowTemperature);
        // 风力
        Elements elements6 = elements1.select("[class=win]");
        String wind = elements6.select("i").text();
        System.out.println(wind);
    }
}