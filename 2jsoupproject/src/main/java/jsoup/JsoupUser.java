package jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class JsoupUser {

	public static void main(String[] args) throws IOException{
		String html = "<div><p>nihao a</p>";
		Document doc = Jsoup.parseBodyFragment(html);
		Element body = doc.body();
		System.out.println(body);//可以看出取得的是个节点集合，不是单个body节点
		
		Document baidudoc =Jsoup.connect("https://www.baidu.com/").get();
		String title = baidudoc.title();
	
		System.out.println(title);
		
		Document doc11 =Jsoup.connect("https://www.baidu.com/")
				//.data("query", "Java") //请求参数
                .cookie("auth", "token") //设置cookie
                .timeout(3000) //设置连接超时时间
                .get(); //使用GET方法访问URL
		
		Element content = baidudoc.head(); //element为单个元素，因为是id
		
		for(Node a :content.childNodes()){
			System.out.println(a.toString());
		}
		
		
		Elements links1 = content.getElementsByTag("link"); //elements是元素集合 因为是tagname即a标签
		
		Elements links = baidudoc.getElementsByTag("a"); //elements是元素集合 因为是tagname即a标签
		for (Element link : links) {
		    String linkHref = link.attr("href"); //attr属性列表如同js的getAttribute
		   // String linkText = link.text(); //textnode节点内容
		    
			System.out.println(linkHref + "------" );
		}
		


		
		
		String html1 = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
		Document doc1 = Jsoup.parse(html1);//解析HTML字符串返回一个Document实现
		Element link = doc1.select("a").first();//查找第一个a元素
		
		String text = doc1.body().text();
		System.out.println(text);
		
		String linkOuterH = link.outerHtml(); 
		String linkInnerH = link.html();
		System.out.println(linkOuterH + "----" + linkInnerH);
		
		
		Document doc111 = Jsoup.connect("http://www.open-open.com").get();
		Element link1 = doc111.select("a").first();
		String relHref = link1.attr("href"); // == "/"
		String absHref = link1.attr("abs:href"); // "http://www.open-open.com/"
		System.out.println(absHref);//针对服务器端加入服务器的路径
		
	}

}
