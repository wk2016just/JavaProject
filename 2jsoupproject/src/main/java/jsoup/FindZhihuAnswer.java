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
		String result = "";// ����һ���ַ��������洢��ҳ����
		BufferedReader in = null;// ����һ�������ַ�������
		
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {

				result += line;// ����ץȡ����ÿһ�в�����洢��result����
			}
		} catch (Exception e) {
			System.out.println("get�����쳣" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) { //whileץȡ��ɺ�
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
			Zhihu zhihuTemp = new Zhihu(matcher.group(1));  //�������zhihu����ȡ
			results.add(zhihuTemp);
			isFind = matcher.find();
		//ѭ����ȡ	ÿ���ش��Ӧ���������� 
		}
		return results;
	}
	


	public static void main(String[] args) throws IOException, ParseException {
		
		
		String url = "https://www.zhihu.com/explore/recommendations";
		String content = FindZhihuAnswer.SendGet(url); //����֪���ղ�ҳ��html�ַ���
		//System.out.println(content);

		ArrayList<Zhihu> myZhihu = FindZhihuAnswer.GetRecommendations(content);
		
		for(Zhihu a : myZhihu){
			int index = 1;
			for(String b : a.userlink){
				System.out.println("yhoooooooooooooooooooooooooooooooooooo"+b);
				//String ucontent = FindZhihuAnswer.SendGet(); 
				
				
				Document d = Jsoup.connect(b) //����cookie
		                .timeout(500000) //�������ӳ�ʱʱ��
		                .get(); //ʹ��GET��������URL;
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
//				//�ش𲻻���ʾȫ���᷵�� ��ʾȫ����ť
//				System.out.println("�ش�"+ ++i + ":"+b + "\n");
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
			System.out.println("����ץȡ�ش�url��" + zhihuUrl);

			String content = FindZhihuAnswer.SendGet(zhihuUrl); //��ȡ�ʴ�ϸ��,ע������������ҳ�棬���ǻش�ķ�ҳ��
			
			//System.out.println("�ش�ҳ������Ϊ��" + content);
			Pattern pattern = Pattern.compile("span.+?zm-editable-content.+?>(.+?)</span>");
			//Pattern pattern = Pattern.compile("span.+?zm-editable-content\">(.+?)</span>");
			Matcher matcher = pattern.matcher(content);
		//	System.out.println(content);

			Document doc = Jsoup.parse(content); //jsoupǿ���htmlת�����ߣ�
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
				answers.add(matcher.group(1)); //ѭ��question content
				isFind = matcher.find();
			}
			
			pattern = Pattern.compile("href=\"/people/(.+?)\"");
			matcher = pattern.matcher(content);
			boolean isImgFind = matcher.find();
	
			while (isImgFind) {
				String userimg = "https://www.zhihu.com/people/" +matcher.group(1)+  "/answers";
				userlink.add(userimg); //ѭ���û���
				isImgFind = matcher.find();
				
			}
		}
	}

	public boolean getAll() {
		return true;
	}

	boolean getRealUrl(String url) {

		Pattern pattern = Pattern.compile("question/(.*?)/"); //��Ϊ�е���question �е���question/answer
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
		return "���⣺" + question + "\n" + "������" + questionDescription + "\n" + "����"
				+ zhihuUrl + "\n"+"�ش�����"  +  answers.size() + "\n";
	}
}
