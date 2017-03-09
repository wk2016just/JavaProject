package jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestContent {

	public static void main(String[] args) throws IOException{
		String url = "https://www.zhihu.com/people/zhang-hao-20-12-18/answers";
		Document d = Jsoup.connect(url).get();
		System.out.println(d);
	}
}
