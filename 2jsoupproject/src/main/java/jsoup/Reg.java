package jsoup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg {

	public static void main(String[] args){
		String html1 = "nihao 123 s=question_link href=\"wonita\"ss dsaj nihaohao";
		Pattern urlPattern1 = Pattern.compile("nihao.+?question_link.+?href=\"(.+?)\".+?nihaohao");
		
		String html = "nihao 123 s=question_link 22 question_linkhref=\"wonita\"ss dsaj nihaohao";
		Pattern urlPattern = Pattern.compile("nihao.+question_link?.+?href=\"(.+?)\".+?nihaohao");
	    Matcher urlMatcher = urlPattern.matcher(html);
	    boolean isFind = urlMatcher.find();
	    System.out.println(urlMatcher.group(0));
	    System.out.println(urlMatcher.group(1));
	    
	    String test = "aabab";
	    Pattern urlPattern2 = Pattern.compile("a.+?b");
	    Matcher urlMatcher2 = urlPattern2.matcher(test);
	    boolean isFind2 = urlMatcher2.find();
	    System.out.println(urlMatcher2.group(0));
	    
	   
	
	}
}
