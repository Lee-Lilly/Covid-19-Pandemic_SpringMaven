package com.project.pandemic.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.project.pandemic.domain.CountryData;
import com.project.pandemic.domain.CountryDataRepository;
import com.project.pandemic.domain.Timeline;
//import com.project.pandemic.domain.CountryUpdatesRepository;
import com.project.pandemic.domain.TimelineRepository;


@Controller
public class GraphDataController {
	@Autowired
	private CountryDataRepository countryDataRepository;
	
	@Autowired
	private TimelineRepository timelineRepository;
	
//	@Autowired
//	private CountryUpdatesRepository countryUpdatesRepository;
	
	@GetMapping({"/", "/global"})
	public String globalCombined (Model model) {
		
		//Timeline World-wide
		List<Timeline> timelines = timelineRepository.findByOrderByDate();
			
		List<Date> dates = new ArrayList<Date>();				//initialise an empty list for dates				
		List<Integer> activeData = new ArrayList<Integer>();	//initialise an empty list for active			
		List<Integer> deathData = new ArrayList<Integer>();		//initialise an empty list for death
		List<Integer> recoveredData = new ArrayList<Integer>();	//initialise an empty list for recovered
		List<Integer> confirmedData = new ArrayList<Integer>();	//initialise an empty list for confirmed
		
		for (Timeline timeline : timelines) {
			Date date = timeline.getDate();
			Integer active = timeline.getActive();
			Integer deaths = timeline.getDeaths();
			Integer recovered = timeline.getRecovered();
			Integer confirmed = timeline.getConfirmed();
			
			dates.add(date);
			activeData.add(active);
			deathData.add(deaths);
			recoveredData.add(recovered);
			confirmedData.add(confirmed);
		}
		System.out.println(dates);
		System.out.println(activeData);
		System.out.println(deathData);
		System.out.println(recoveredData);
		System.out.println(confirmedData);
		
		model.addAttribute("dates", dates);
		model.addAttribute("active", activeData);
		model.addAttribute("deaths", deathData);
		model.addAttribute("recovered", recoveredData);
		model.addAttribute("confirmed", confirmedData);
		
		//Global total amount - the latest updates
		int last = timelines.size() -1;
		Timeline lastLine = timelines.get(last);
		Integer activeLast = lastLine.getActive();
		Integer deathsLast = lastLine.getDeaths();
		Integer recoveredLast = lastLine.getRecovered();
	
		System.out.println(activeLast);
		System.out.println(deathsLast);
		System.out.println(recoveredLast);
		
		model.addAttribute("totalActive", activeLast);
		model.addAttribute("totalDeath", deathsLast);
		model.addAttribute("totalRecoverd", recoveredLast);
	
		// Continental view
		List<CountryData> europe = countryDataRepository.findByContinent("Europe");
		List<CountryData> asia = countryDataRepository.findByContinent("Asia");
		List<CountryData> africa =countryDataRepository.findByContinent("Africa");
		List<CountryData> oceania = countryDataRepository.findByContinent("Australia/Oceania");
		List<CountryData> north_america =countryDataRepository.findByContinent("North America");
		List<CountryData> south_america =countryDataRepository.findByContinent("South America");
			
		//create a HashMap of country and data for Europe
		HashMap<String, Integer> activeEU = new HashMap<String, Integer>();
		
		for(CountryData country : europe ) {
			String name = country.getCountry();
			Integer active = country.getActive();	
			activeEU.put(name, active);	
		}
		model.addAttribute("europeList", activeEU);
		System.out.println(activeEU);
				
		//create a HashMap of country and data for Asia
		HashMap<String, Integer> activeAsia = new HashMap<String, Integer>();			
							
		for(CountryData country : asia ) {
			String name = country.getCountry();
			Integer active = country.getActive();				
			activeAsia.put(name, active);	
		}		
		model.addAttribute("asiaList", activeAsia);
		System.out.println(activeAsia);		
		
		//create a HashMap of country and data for Africa
		HashMap<String, Integer> activeAfrica = new HashMap<String, Integer>();	
		
		for(CountryData country : africa ) {
			String name = country.getCountry();
			Integer active = country.getActive();			
			activeAfrica.put(name, active);			
		}	
		model.addAttribute("africaList", activeAfrica);
		System.out.println(activeAfrica);
						
		//create a HashMap of country and data for Oceania
		HashMap<String, Integer> activeOceania = new HashMap<String, Integer>();	
				
		for(CountryData country : oceania ) {
			String name = country.getCountry();
			Integer active = country.getActive();			
			activeOceania.put(name, active);		
		}	
		model.addAttribute("oceaniaList", activeOceania);
		System.out.println(activeOceania);
							
		//create a HashMap of country and data for North America
		HashMap<String, Integer> activeNorthAmerica = new HashMap<String, Integer>();	
						
		for(CountryData country : north_america ) {
			String name = country.getCountry();
			Integer active = country.getActive();
			activeNorthAmerica.put(name, active);		
		}
		model.addAttribute("northAmericaList", activeNorthAmerica);		
		System.out.println(activeNorthAmerica);
						
		//create a HashMap object of country and data for South America
		HashMap<String, Integer> activeSouthAmerica = new HashMap<String, Integer>();	
								
		for(CountryData country : south_america ) {
			String name = country.getCountry();
			Integer active = country.getActive();		
			activeSouthAmerica.put(name, active);
		}
		model.addAttribute("southAmericaList", activeSouthAmerica);		
		System.out.println(activeSouthAmerica);
	
		return "global"; 
	}
//	
//	@GetMapping("/Countries/{code}") //search function user input specific country code
//	public String countrySnapshot(Model model) {
//		model.addAttribute("countries", countryUpdatesRepository.findByCode("userinput"));
//		//countrytimeline comes also;
//		return null;
//	
//	}
}
