import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class webSearch{
	public static void googleSearch(String keywords) throws Exception{
		String google = "http://www.google.com/search?q=";
		
		String charset = "UTF-8";
		String userAgent = "Joyce";
		Elements links = Jsoup.connect(google + URLEncoder.encode(keywords, charset)).userAgent(userAgent).get().select("li.g>h3>a");
		
		Element firstLink = links.get(0);
		
		String title = firstLink.text();
		String url = firstLink.absUrl("href");
		url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
		System.out.println("Title: " + title);
		System.out.println("URL: " + url);
		
//       can also iterate all links besides first link
//		for (Element link : links) {
//		    String title = link.text();
//		    String url = link.absUrl("href"); 
//		    // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
//		    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
//
//		    if (url.startsWith("http")) {  // to ensure it is not Ads/news/etc.
//		    	System.out.println("Title: " + title);
//			    System.out.println("URL: " + url);
//		    }
//		}
	
	}
	
	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String keywords;
	
		System.out.println("please input your keywords for search:");
		keywords = sc.nextLine();
		googleSearch(keywords);
		
	}
}