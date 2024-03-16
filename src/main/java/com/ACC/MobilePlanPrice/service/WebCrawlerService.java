package com.ACC.MobilePlanPrice.service;

import java.io.IOException;
import java.util.Set;

public interface WebCrawlerService {

    Set<String> crawl(String startingUrl) throws IOException;
}
