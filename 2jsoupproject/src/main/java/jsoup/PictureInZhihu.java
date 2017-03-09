package jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author RunDouble
 * 
 */
public class PictureInZhihu {
		public static HashMap<String, String> getUsersUrlAndprofileUrl(String webUrl) {
		Document document;
		String addressLinkHref;
		String profileLinkSrc = "";
		HashMap<String, String> urlWithProfile = new HashMap<String, String>();
		try {
			document = Jsoup.connect(webUrl).get();
			Elements links = document.select("a[href]");
			for (int i = 0; i < links.size(); i++) {
				addressLinkHref = links.get(i).attr("href");
				try {
					document = Jsoup.connect(addressLinkHref).get();
				} catch (Exception e) {
					i++;
					continue;
				}
				Elements jpgs = document.select("img[src$=.jpg]");
				if (jpgs.size() != 0) {
					profileLinkSrc = jpgs.get(0).attr("src");
				}
				System.out.println(addressLinkHref);
				System.out.println(profileLinkSrc + "\n\n");
				if (!profileLinkSrc.equals("")&& profileLinkSrc.contains("http")) {
					urlWithProfile.put(addressLinkHref, profileLinkSrc);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return urlWithProfile;
	}

	public static void picturePersistence(HashMap<String, String> info) {
		Iterator<Entry<String, String>> it = info.entrySet().iterator();
		int index = 1;
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String tempPicUrl = entry.getValue().toString();
			try {
				URL url = new URL(tempPicUrl);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setReadTimeout(50000);
				InputStream inputStream = httpURLConnection.getInputStream();
				String newImageName = String.valueOf(index) + ".jpg";
				File file = new File("zhihupics");
				if (!file.exists()) {
					file.mkdir();
				}

				FileOutputStream fos = new FileOutputStream(file.getPath()+ "\\" + newImageName);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0) {
					fos.write(buffer, 0, length);
				}
				index++;
			} catch (Exception e) {
				continue;
			}
		}
	}

	/*
	 * just a test
	 */
	public static void main(String[] args) {
		
		PictureInZhihu.picturePersistence(PictureInZhihu.getUsersUrlAndprofileUrl("http://lab.grapeot.me/zhihu/autoface"));

	
	}

}