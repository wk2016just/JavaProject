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
		System.out.println(body);//���Կ���ȡ�õ��Ǹ��ڵ㼯�ϣ����ǵ���body�ڵ�
		
		Document baidudoc =Jsoup.connect("https://www.baidu.com/").get();
		String title = baidudoc.title();
	
		System.out.println(title);
		
		Document doc11 =Jsoup.connect("https://www.baidu.com/")
				//.data("query", "Java") //�������
                .cookie("auth", "token") //����cookie
                .timeout(3000) //�������ӳ�ʱʱ��
                .get(); //ʹ��GET��������URL
		
		Element content = baidudoc.head(); //elementΪ����Ԫ�أ���Ϊ��id
		
		for(Node a :content.childNodes()){
			System.out.println(a.toString());
		}
		
		
		Elements links1 = content.getElementsByTag("link"); //elements��Ԫ�ؼ��� ��Ϊ��tagname��a��ǩ
		
		Elements links = baidudoc.getElementsByTag("a"); //elements��Ԫ�ؼ��� ��Ϊ��tagname��a��ǩ
		for (Element link : links) {
		    String linkHref = link.attr("href"); //attr�����б���ͬjs��getAttribute
		   // String linkText = link.text(); //textnode�ڵ�����
		    
			System.out.println(linkHref + "------" );
		}
		


		
		
		String html1 = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
		Document doc1 = Jsoup.parse(html1);//����HTML�ַ�������һ��Documentʵ��
		Element link = doc1.select("a").first();//���ҵ�һ��aԪ��
		
		String text = doc1.body().text();
		System.out.println(text);
		
		String linkOuterH = link.outerHtml(); 
		String linkInnerH = link.html();
		System.out.println(linkOuterH + "----" + linkInnerH);
		
		
		Document doc111 = Jsoup.connect("http://www.open-open.com").get();
		Element link1 = doc111.select("a").first();
		String relHref = link1.attr("href"); // == "/"
		String absHref = link1.attr("abs:href"); // "http://www.open-open.com/"
		System.out.println(absHref);//��Է������˼����������·��
		
	}

}
