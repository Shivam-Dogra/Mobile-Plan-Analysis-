package com.ACC.MobilePlanPrice.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.ACC.MobilePlanPrice.model.MobilePlan;
import com.ACC.MobilePlanPrice.service.MobilePlanService;


@Service
public class RogersMobilePlanServiceImpl implements MobilePlanService {

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
		
		List<MobilePlan> rogersPlanList= new ArrayList<>();
		// TODO Auto-generated method stub		

		waitInSeconds(5);
		initializeDriver("https://www.rogers.com/plans?icid=R_WIR_CMH_6WMCMZ");		
        waitInSeconds(5);
        
        List<WebElement> totalplans = driver.findElements(By.xpath("//ds-tile//div[contains(@class,'dsa-vertical-tile d-flex')]"));
        
        for(int i=0;i<totalplans.size();i++) {
        	waitInSeconds(3);
        	MobilePlan plan= new MobilePlan();        	
        	JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            // Scroll down by a specific pixel value (e.g., 1000 pixels)
            jsExecutor.executeScript("window.scrollBy(0,1000);");      	
        	
        	waitInSeconds(2);
        	List<WebElement> planName = driver.findElements(By.xpath("//ds-tile//div[contains(@class,'dsa-vertical-tile d-flex')]//p[contains(@class,'heading')]"));
        	
        	waitInSeconds(2);
        	   
        	List<WebElement> monthlyCost=driver.findElements(By.xpath("//ds-tile//div[contains(@class,'dsa-vertical-tile d-flex')]//ds-price//div[@class='ds-price']"));
        	waitInSeconds(2);
        	
        	List<WebElement> dataAllowance= driver.findElements(By.xpath("//ds-tile//div[contains(@class,'dsa-vertical-tile d-flex')]//li//b"));
        	waitInSeconds(2);
        	
        	List<WebElement>networkCoverage=driver.findElements(By.xpath("//ds-tile//div[contains(@class,'dsa-vertical-tile d-flex')]//li[contains(text(),'network')]"));
        	waitInSeconds(2);
        	
        	List<WebElement> callAndTextAllowance=driver.findElements(By.xpath("//ds-tile//div[contains(@class,'dsa-vertical-tile d-flex')]//*[contains(text(),'talk') or contains(text(),'Calling')]"));
        	
        	
        	plan.setDataAllowance(dataAllowance.get(i).getText());
        	plan.setMonthlyCost(monthlyCost.get(i).getAttribute("aria-label"));
        	plan.setPlanName(planName.get(i).getText());
        	plan.setCallAndTextAllowance(callAndTextAllowance.get(i).getText());
        	plan.setNetworkCoverage(networkCoverage.get(i).getText());
        	plan.setProvider("Rogers");

        	rogersPlanList.add(plan);
        }
        
        driver.quit();		
		return rogersPlanList;
	}

}
