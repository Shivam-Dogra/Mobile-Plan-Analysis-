package com.ACC.MobilePlanPrice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ACC.MobilePlanPrice.service.WebCrawlerService;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private Set<String> visitedUrls;
    private Queue<String> urlsToVisit;
    private int maxUrlsToVisit;
    private String saveDir;

    public WebCrawlerServiceImpl() {
        visitedUrls = new HashSet<>();
        urlsToVisit = new LinkedList<>();
        
    }

    public void crawl(String startingUrl) throws IOException {
    	maxUrlsToVisit = 20;
    	saveDir = "MobileWebCrawlDir";
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
            }
        }
        System.out.println("Website is crawled!");
    }
}
