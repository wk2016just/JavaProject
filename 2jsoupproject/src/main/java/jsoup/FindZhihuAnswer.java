package jsoup;

import java.util.ArrayList;
import java.util.LinkedList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

public class FindZhihuAnswer {

	
	
	static String SendGet(String url) {
		String result = "";// 定义一个字符串用来存储网页内容
		BufferedReader in = null;// 定义一个缓冲字符输入流
		
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {

				result += line;// 遍历抓取到的每一行并将其存储到result里面
			}
		} catch (Exception e) {
			System.out.println("get请求异常" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) { //while抓取完成后
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	static ArrayList<Zhihu> GetRecommendations(String content) {
		ArrayList<Zhihu> results = new ArrayList<Zhihu>();
		Pattern pattern = Pattern.compile("<h2>.+?question_link.+?href=\"(.+?)\".+?</h2>");
		Matcher matcher = pattern.matcher(content);
		Boolean isFind = matcher.find();
		while (isFind) {
			System.out.println(matcher.group(1) + "------------------"); 
			Zhihu zhihuTemp = new Zhihu(matcher.group(1));  //这里进行zhihu答案爬取
			results.add(zhihuTemp);
			isFind = matcher.find();
		//循环爬取	每个回答对应的问题帖子 
		}
		return results;
	}
	


	public static void main(String[] args) throws IOException, ParseException {
		
		
		String url = "https://www.zhihu.com/explore/recommendations";
		String content = FindZhihuAnswer.SendGet(url); //返回知乎收藏页的html字符串
		//System.out.println(content);

		ArrayList<Zhihu> myZhihu = FindZhihuAnswer.GetRecommendations(content);
		
		for(Zhihu a : myZhihu){
			int index = 1;
			for(String b : a.userlink){
				System.out.println("yhoooooooooooooooooooooooooooooooooooo"+b);
				//String ucontent = FindZhihuAnswer.SendGet(); 
				
				
				Document d = Jsoup.connect(b) //设置cookie
		                .timeout(500000) //设置连接超时时间
		                .get(); //使用GET方法访问URL;
				Elements e = d.select("img[class=Avatar Avatar--large UserAvatar-inner]");
				
				if(null == e.first().attr("src")){
					continue;
				}
				String touxiangdizhi = e.first().attr("src");
				
				
				URL touxiangurl = new URL(touxiangdizhi);
				HttpURLConnection httpURLConnection = (HttpURLConnection) touxiangurl.openConnection();
				httpURLConnection.setReadTimeout(5000000);
				InputStream inputStream = httpURLConnection.getInputStream();
				String newImageName = String.valueOf(index) + ".jpg";
				File file = new File("zhihupics");
				if (!file.exists()) {
					file.mkdir();
				}
				System.out.println(file.getAbsolutePath());
				FileOutputStream fos = new FileOutputStream(file.getPath()+ "\\" + newImageName);
				
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0) {
					fos.write(buffer, 0, length);
				}
				index++;
				
				
			}
		}
		
		
		
//		for(Zhihu a : myZhihu){
//			int i =0;
//			System.out.println(a);
//			for(String b : a.answers){
//				//回答不会显示全，会返回 显示全部按钮
//				System.out.println("回答"+ ++i + ":"+b + "\n");
//			}
//		}
	}

}

class Zhihu {
	public String question;
	public String questionDescription;
	public String zhihuUrl;
	public ArrayList<String> answers;
	public ArrayList<String> userlink;
	public Zhihu(String url) {
		
		
		question = "";
		questionDescription = "";
		zhihuUrl = "";
		answers = new ArrayList<String>();
		userlink = new ArrayList<String>();
		if (getRealUrl(url)) {
			System.out.println("正在抓取回答url：" + zhihuUrl);

			String content = FindZhihuAnswer.SendGet(zhihuUrl); //获取问答细节,注意这里是在总页面，不是回答的分页面
			
			//System.out.println("回答页面内容为：" + content);
			Pattern pattern = Pattern.compile("span.+?zm-editable-content.+?>(.+?)</span>");
			//Pattern pattern = Pattern.compile("span.+?zm-editable-content\">(.+?)</span>");
			Matcher matcher = pattern.matcher(content);
		//	System.out.println(content);

			Document doc = Jsoup.parse(content); //jsoup强大的html转化工具！
			//System.out.println(doc); 
			
			if (matcher.find()) {
				question = matcher.group(1); //question title
				
			}

			pattern = Pattern.compile("zh-question-detail.+?<div.+?>(.*?)</div>");
			matcher = pattern.matcher(content);
			if (matcher.find()) {
				questionDescription = matcher.group(1); //question detail
			}

			pattern = Pattern.compile("/answer/content.+?<div.+?>(.*?)</div>");
			matcher = pattern.matcher(content);
			boolean isFind = matcher.find();
			while (isFind) {
				answers.add(matcher.group(1)); //循环question content
				isFind = matcher.find();
			}
			
			pattern = Pattern.compile("href=\"/people/(.+?)\"");
			matcher = pattern.matcher(content);
			boolean isImgFind = matcher.find();
	
			while (isImgFind) {
				String userimg = "https://www.zhihu.com/people/" +matcher.group(1)+  "/answers";
				userlink.add(userimg); //循环用户名
				isImgFind = matcher.find();
				
			}
		}
	}

	public boolean getAll() {
		return true;
	}

	boolean getRealUrl(String url) {

		Pattern pattern = Pattern.compile("question/(.*?)/"); //因为有的是question 有的是question/answer
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			zhihuUrl = "https://www.zhihu.com/question/" + matcher.group(1);
		} else {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "问题：" + question + "\n" + "描述：" + questionDescription + "\n" + "链接"
				+ zhihuUrl + "\n"+"回答数量"  +  answers.size() + "\n";
	}
}
