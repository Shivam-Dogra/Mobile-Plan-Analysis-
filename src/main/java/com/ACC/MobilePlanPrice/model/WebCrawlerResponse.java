package com.ACC.MobilePlanPrice.model;

import java.util.List;

public class WebCrawlerResponse {
    private List<String> visitedUrls;
    private String message;

    public WebCrawlerResponse(List<String> visitedUrls, String message) {
        this.visitedUrls = visitedUrls;
        this.message = message;
    }

    public List<String> getVisitedUrls() {
        return visitedUrls;
    }

    public void setVisitedUrls(List<String> visitedUrls) {
        this.visitedUrls = visitedUrls;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
