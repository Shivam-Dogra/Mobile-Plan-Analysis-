package features;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WebCrawler {

	private Set<String> visitedUrls;
	private Queue<String> urlsToVisit;
	private int maxUrlsToVisit;
	private String saveDir;

	public WebCrawler(int maxUrlsToVisit, String saveDir) {
		visitedUrls = new HashSet<String>();
		urlsToVisit = new LinkedList<String>();
		this.maxUrlsToVisit = maxUrlsToVisit;
		this.saveDir = saveDir;
	}

	public void clear() {
		visitedUrls.clear();
		urlsToVisit.clear();
	}
	
	public static void main(String[] args) throws IOException {
        int maxUrlsToVisit = 20;
        String saveDir = "MobileWebCrawlDir";
        Scanner scanner = new Scanner(System.in);
        
        WebCrawler crawler = new WebCrawler(maxUrlsToVisit, saveDir);
        FrequencyCount freqCount = new FrequencyCount();

        String startingUrl;
        do {
        	System.out.print("Enter a starting URL: ");
        	startingUrl = scanner.nextLine();
        	if (!URLValidator.validate(startingUrl)) {
        		System.out.println("Invalid URL. Please try again.");
        	}
        }while(!URLValidator.validate(startingUrl));
        crawler.clear();
        crawler.crawl(startingUrl,saveDir);
        
	}


	public void crawl(String startingUrl, String saveDir) throws IOException {
		searchFrequency.TreeNode root = new searchFrequency.TreeNode();
		 Scanner scanner = new Scanner(System.in);
		 FrequencyCount freqCount = new FrequencyCount();


		File dir = new File(saveDir);
		if (!dir.exists()) {
			dir.mkdir();
		}
		urlsToVisit.add(startingUrl);
		while (!urlsToVisit.isEmpty() && visitedUrls.size() < maxUrlsToVisit) {
			String url = urlsToVisit.poll();
			if (!visitedUrls.contains(url)) {
				visitedUrls.add(url);
				System.out.println("Visiting: " + url);
				String links = HTMLParser.parse(url, saveDir);
				for (String nextUrl : links.split(" ")) {
					if (!visitedUrls.contains(nextUrl)) {
						urlsToVisit.add(nextUrl);
					}
				}
			}
		}
		System.out.println("Website is crawled!");
		
		System.out.print("Enter the keyword for frequency count(comma separted): ");
        String keywords = scanner.nextLine();
        for(String word: keywords.split(",")) {
            root.insert(word);
        }
        
        freqCount.countFrequency(saveDir,keywords);
        
	}

}