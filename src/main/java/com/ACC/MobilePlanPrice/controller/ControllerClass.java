package com.ACC.MobilePlanPrice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ACC.MobilePlanPrice.model.MobilePlan;
import com.ACC.MobilePlanPrice.service.MobilePlanService;
import com.ACC.MobilePlanPrice.service.impl.BellMobilePlanServiceImpl;
import com.ACC.MobilePlanPrice.service.impl.RogersMobilePlanServiceImpl;
import com.ACC.MobilePlanPrice.service.impl.FreedomMobilePlanServiceImpl;

@RestController
@RequestMapping("/mobile-plans")

public class ControllerClass {
	
	@Autowired
	private RogersMobilePlanServiceImpl rogersService;
	
	@Autowired
	private BellMobilePlanServiceImpl bellService;
	
	@Autowired
	private FreedomMobilePlanServiceImpl freedomService;
	
	
	@GetMapping("/rogers")
    public ResponseEntity<Object> getRogersMobilePlan() {
		try {
            List<MobilePlan> rogersMobilePlan = rogersService.getMobilePlan();

            if (rogersMobilePlan != null) {
            	 return new ResponseEntity<>(rogersMobilePlan, HttpStatus.OK);
               } 
            else {
                return new ResponseEntity<>("Rogers mobile plan not found",HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return new ResponseEntity<>("An error occurred while fetching the Rogers mobile plan",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
	
	
	@GetMapping("/bell")
    public ResponseEntity<Object> getBellMobilePlan() {
		try {
            List<MobilePlan> bellMobilePlan = bellService.getMobilePlan();

            if (bellMobilePlan != null) {
            	 return new ResponseEntity<>(bellMobilePlan, HttpStatus.OK);
               } 
            else {
                return new ResponseEntity<>("Bell mobile plan not found",HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return new ResponseEntity<>("An error occurred while fetching the Bell mobile plan",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
	
	
	
	@GetMapping("/freedom")
    public ResponseEntity<Object> getVirginPlusMobilePlan() {
		try {
            List<MobilePlan> freedomMobilePlan = freedomService.getMobilePlan();

            if (freedomMobilePlan != null) {
            	 return new ResponseEntity<>(freedomMobilePlan, HttpStatus.OK);
               } 
            else {
                return new ResponseEntity<>("Freedom mobile plan not found",HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return new ResponseEntity<>("An error occurred while fetching the Freedom mobile plan",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/compare-plans")
    public ResponseEntity<Object> compareAllMobilePlans() {
        try {
        	
            List<List<MobilePlan>> plans = new ArrayList<>();
            List<MobilePlan> rogersPlans=rogersService.getMobilePlan();
            List<MobilePlan> bellPlans=rogersService.getMobilePlan();
            List<MobilePlan> virginPlusPlans=rogersService.getMobilePlan();
            plans.add(rogersPlans);
            plans.add(bellPlans);
            plans.add(virginPlusPlans);         

            return new ResponseEntity<>(plans, HttpStatus.OK);
            
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return new ResponseEntity<>( "An error occurred while fetching mobile plans",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}


