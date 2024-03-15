package com.ACC.MobilePlanPrice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ACC.MobilePlanPrice.model.MobilePlan;
import com.ACC.MobilePlanPrice.model.WebCrawlerResponse;
import com.ACC.MobilePlanPrice.service.MobilePlanService;
import com.ACC.MobilePlanPrice.service.WebCrawlerService;
import com.ACC.MobilePlanPrice.service.impl.BellMobilePlanServiceImpl;
import com.ACC.MobilePlanPrice.service.impl.RogersMobilePlanServiceImpl;
import com.ACC.MobilePlanPrice.service.impl.WordCompletionImp;
import com.ACC.MobilePlanPrice.service.impl.searchFrequencyImp;
import com.ACC.MobilePlanPrice.service.impl.searchFrequencyImp.TreeNode;

import features.WebCrawler;

import com.ACC.MobilePlanPrice.service.impl.FreedomMobilePlanServiceImpl;
import com.ACC.MobilePlanPrice.service.impl.FrequencyCountImpl;

@RestController
@RequestMapping("/mobile-plans")

public class ControllerClass {
	
	private final searchFrequencyImp.TreeNode root;
	private final searchFrequencyImp searchFrequency;


    @Autowired
    public ControllerClass(searchFrequencyImp.TreeNode root, searchFrequencyImp searchFrequency) {
        this.root = root;
        this.searchFrequency = searchFrequency;
    }
		
	@Autowired
	private RogersMobilePlanServiceImpl rogersService;
	
	@Autowired
	private BellMobilePlanServiceImpl bellService;
	
	@Autowired
	private FreedomMobilePlanServiceImpl freedomService;
	
	@Autowired
	private WebCrawlerService webCrawlerService;
	
	@Autowired
	private FrequencyCountImpl frequencyCounter;
	
	@Autowired
	private WordCompletionImp wordCompletion;
	
	
	
	@GetMapping("/searchFrequency")
	public ResponseEntity<Object> searchFrequency() {
		try {
	        // Create a list to store word-frequency pairs
	        List<Map<String, Object>> searchFrequencyList = new ArrayList<>();
	        
	        // Collect word-frequency pairs using in-order traversal
	        collectWordFrequencies(root, searchFrequencyList);
	        
	        // Return the list as JSON response
	        Map<String, List<Map<String, Object>>> response = new HashMap<>();
	        response.put("searchFrequency", searchFrequencyList);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	    }
	}

	private void collectWordFrequencies(TreeNode root, List<Map<String, Object>> searchFrequencyList) {
	    if (root != null) {
	        // Traverse left subtree
	        collectWordFrequencies(root.left, searchFrequencyList);
	        
	        // Add word-frequency pair to the list
	        Map<String, Object> wordFrequencyMap = new HashMap<>();
	        wordFrequencyMap.put("word", root.word);
	        wordFrequencyMap.put("frequency", root.frequency);
	        searchFrequencyList.add(wordFrequencyMap);
	        
	        // Traverse right subtree
	        collectWordFrequencies(root.right, searchFrequencyList);
	    }
	}
	
	@GetMapping("/wordcompletion/{userInput}")
	public ResponseEntity<Object> wordCompletion(@PathVariable String userInput) {
	    try {
	        // Get the word completions for the user input
	        List<String> wordCompletions = WordCompletionImp.spellSuggestions(userInput);
	        
	        // Check if the list of word completions is empty
	        if (wordCompletions.isEmpty()) {
	            // Return a generic response indicating no word completions found
	            return ResponseEntity.ok().body("No word completions found for the input: " + userInput);
	        }
	        
	        // Retrieve the last word from the list directly
	        String lastWord = wordCompletions.get(wordCompletions.size() - 1);
	        
	        // Return the last word as JSON response
	        Map<String, String> response = new HashMap<>();
	        response.put("Last Word", lastWord);
	        
	        return ResponseEntity.ok().body(response);
	    } 
	    catch (Exception e) {
	        // Handle other errors
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	    }
	}



	
	@GetMapping("/crawl")
	public ResponseEntity<Object> crawl(@RequestParam String startingUrl) {
	    try {
	    	WebCrawler c = new WebCrawler();
	        Set<String> visitedUrls = c.crawl(startingUrl);
	        
	        
	        // Create the response object with visited URLs and message
	        Map<String, Object> response = new HashMap<>();
	        response.put("visited_urls", visitedUrls);
	        response.put("message", "Website is crawled!");
	        
	        
	        // Return the response as JSON
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (IOException e) {
	        // Log the exception or handle it as needed
	        return new ResponseEntity<>("Error crawling website!", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	 @GetMapping("/frequencyCount/{userInput}")
	    public ResponseEntity<Object> frequencyCount(@PathVariable String userInput) {
	        try {
	        	// Split userInput into individual words
	            String[] words = userInput.split(",");
	            
	            // Insert each word into the root
	            for (String word : words) {
	                root.insert(word.trim().toLowerCase());
	            }
	            Map<String, Integer> wordFrequency = frequencyCounter.countFrequency(userInput);
	            return new ResponseEntity<>(wordFrequency, HttpStatus.OK);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	        }
	    }    
	
	
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
            return new ResponseEntity<>( "An error occurred while fetching mobile plans",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}


