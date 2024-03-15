package com.ACC.MobilePlanPrice.service;

import java.io.IOException;
import java.util.Set;

public interface WebCrawlerService {

    void crawl(String startingUrl) throws IOException;
}
