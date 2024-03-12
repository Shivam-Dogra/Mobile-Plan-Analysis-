package com.ACC.MobilePlanPrice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import com.ACC.MobilePlanPrice.model.MobilePlan;
import com.ACC.MobilePlanPrice.service.MobilePlanService;

@Service
public class FreedomMobilePlanServiceImpl implements MobilePlanService {

	private WebDriver driver;

    public void initializeDriver(String url) {
    	
    	driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }
    
    private static void waitInSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);  // Convert seconds to milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	@Override
	public List<MobilePlan> getMobilePlan() {
		// TODO Auto-generated method stub
		
		List<MobilePlan> virginPlusPlanList= new ArrayList<>();
		waitInSeconds(5);
		initializeDriver("https://shop.freedommobile.ca/en-CA/plans?isByopPlanFirstFlow=true");		
        waitInSeconds(5);
        
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Scroll down by a specific pixel value (e.g., 800 pixels)
        jsExecutor.executeScript("window.scrollBy(0,1200);");      	
    	
        List<WebElement> totalplans = driver.findElements(By.xpath("//li[@data-testid='planComponent']//p[@data-testid='plan-eyebrow']"));
        
        for(int i=0;i<totalplans.size();i++) {
        	waitInSeconds(3);
        	MobilePlan plan= new MobilePlan();
        	//5443
        	////div[contains(@class,'tb attributeList')]//div[@class='tc planIcon']//img[contains(@alt,'5g') or contains(@alt,'4g') or contains(@alt,'3g')]
        	
        	waitInSeconds(2);
        	List<WebElement> planNameA = driver.findElements(By.xpath("//li[@data-testid='planComponent']//p[@data-testid='plan-eyebrow']"));
        	List<WebElement> planNameB = driver.findElements(By.xpath("//li[@data-testid='planComponent']//p[@data-testid='plan-title']"));
        	       	
        	waitInSeconds(2);
        	   
        	List<WebElement> monthlyCost=driver.findElements(By.xpath("//li[@data-testid='planComponent']//span[@data-testid='plan-monthly-price']"));
        	waitInSeconds(2);
        	
        	List<WebElement> dataAllowance= driver.findElements(By.xpath("//p[@data-testid='plan-title']"));
        	waitInSeconds(2);
        	
        	List<WebElement>networkCoverage=driver.findElements(By.xpath("//p[@data-testid='plan-tail-text']"));
        	waitInSeconds(2);
        	
        	List<WebElement> callAndTextAllowance=driver.findElements(By.xpath("//div[@data-testid='data-talk-text-description']//p[contains(text(),'talk')]"));
        	
        	plan.setPlanName(planNameA.get(i).getText()+" "+planNameB.get(i).getText());
        	plan.setMonthlyCost(monthlyCost.get(i).getText());
        	plan.setDataAllowance(dataAllowance.get(i).getText());
        	//if (networkCoverage.get(i).getText() == null || networkCoverage.get(i).getText().isEmpty()) 
        	if(i==4)
        		plan.setNetworkCoverage("5G");
        	else
        		plan.setNetworkCoverage(networkCoverage.get(i).getText());
        	
        	plan.setCallAndTextAllowance(callAndTextAllowance.get(i).getText());       	
        	plan.setProvider("Freedom");

        	virginPlusPlanList.add(plan);
        }
        
        driver.quit();		
		return virginPlusPlanList;
        
	}

}
