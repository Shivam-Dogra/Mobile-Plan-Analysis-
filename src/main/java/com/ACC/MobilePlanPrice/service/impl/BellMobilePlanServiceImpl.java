package com.ACC.MobilePlanPrice.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.ACC.MobilePlanPrice.model.MobilePlan;
import com.ACC.MobilePlanPrice.service.MobilePlanService;

@Service
public class BellMobilePlanServiceImpl implements MobilePlanService {

    private WebDriver driver;
    private WebDriverWait wait;

    

    public void initializeDriver(String url) {
    	driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);
        driver.manage().window().maximize();
    }

    

    @Override
    public List<MobilePlan> getMobilePlan() {
        List<MobilePlan> BellPlanList = new ArrayList<>();

        initializeDriver("https://www.bell.ca/Mobility/Cell_phone_plans/Unlimited-plans");
        
        MobilePlan plan= new MobilePlan();
        
        try {
        	 MobilePlan plan1 = commonPlanDetails("product-id-d0198111-4b79-429b-89b3-30d558f81165", "1", "75", "1", "3");
             BellPlanList.add(plan1);

             MobilePlan plan2 = commonPlanDetails("product-id-8e4f1de1-9b9f-4208-8ea0-564cdb12c686", "2", "100", "1" ,"4");
             BellPlanList.add(plan2);

             MobilePlan plan3 = commonPlanDetails("product-id-cdb1736c-e739-402b-b335-5ee4dc7fe44f", "3", "150", "1", "4");
             BellPlanList.add(plan3);
 
            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return BellPlanList;
    }

    public MobilePlan commonPlanDetails(String productID, String price, String data, String network, String callAndText) {
        MobilePlan p = new MobilePlan();
        p.setProvider("Bell");
        p.setPlanName(getPlanName(productID));
        p.setMonthlyCost(getMonthlyCost(price));
        p.setDataAllowance(getdataAllowance(data));
        p.setNetworkCoverage(extraFeatures(productID, network));
        p.setCallAndTextAllowance(extraFeatures(productID, callAndText));
        return p;
    }


    public String getPlanName(String productID) {
        WebElement planNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@id='" + productID + "']")));
        return planNameElement.getText();
    }

    public String getMonthlyCost(String plan) {
        WebElement monthlyCostElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='big-price hs-price'])[" + plan + "]")));
        String fullText = monthlyCostElement.getText();
        String[] parts = fullText.split("per month");
        String monthlyCost = parts[0].trim();
        return monthlyCost;
    }

    public String getdataAllowance(String data) {
        WebElement planNameElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[normalize-space()='" + data + " GB']")));
        return planNameElement.getText();
    }

    public String extraFeatures(String productID, String planNo) {
        WebElement feature = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@id='" + productID + "']/parent::div/parent::div/following-sibling::div//li["
                        + planNo + "]")));
        String fullText = feature.getText().trim();

        String[] parts = fullText.split("\n");
        String allowance = parts[0].trim();
        return allowance;
    }
}
