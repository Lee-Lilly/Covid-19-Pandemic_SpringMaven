package com.project.pandemic.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.pandemic.domain.CountryData;
import com.project.pandemic.domain.CountryDataRepository;
import com.project.pandemic.domain.CountryTimeline;
import com.project.pandemic.domain.CountryTimelineWrapper;
import com.project.pandemic.domain.CountryUpdates;
import com.project.pandemic.domain.Timeline;
import com.project.pandemic.domain.CountryUpdatesRepository;
import com.project.pandemic.domain.TimelineRepository;


@Controller
public class GraphDataController {
	@Autowired
	private CountryDataRepository countryDataRepository;
	
	@Autowired
	private TimelineRepository timelineRepository;
	
	@Autowired
	private CountryUpdatesRepository countryUpdatesRepository;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
	DateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping({"/", "/global"})
	public String global(Model model) {
		//Timeline World-wide
		List<Timeline> timelines = timelineRepository.findByOrderByDate();
			
		List<String> dates = new ArrayList<String>();			//initialise a list for dates in String	
		List<Integer> activeData = new ArrayList<Integer>();	//initialise a list for active			
		List<Integer> deathData = new ArrayList<Integer>();		//initialise a list for death
		List<Integer> recoveredData = new ArrayList<Integer>();	//initialise a list for recovered
		
		for (Timeline timeline : timelines) {
			Date date = timeline.getDate();
			Integer active = timeline.getActive();
			Integer deaths = timeline.getDeaths();
			Integer recovered = timeline.getRecovered();
			
			dates.add(df_date.format(date)); //prepare the date format for timeline graph
			activeData.add(active);
			deathData.add(deaths);
			recoveredData.add(recovered);
		}
//		System.out.println(dates);
//		System.out.println(activeData);
//		System.out.println(deathData);
//		System.out.println(recoveredData);
					
		model.addAttribute("dates", dates);
		model.addAttribute("active", activeData);
		model.addAttribute("deaths", deathData);
		model.addAttribute("recovered", recoveredData);
		
		//Global total amount - the latest updates
		int last = timelines.size() -1;
		Timeline lastLine = timelines.get(last);
		Date latestDate = lastLine.getDate();
		Integer activeLast = lastLine.getActive();
		Integer deathsLast = lastLine.getDeaths();
		Integer recoveredLast = lastLine.getRecovered();
		Integer confirmed = lastLine.getConfirmed();
					
//		System.out.println(df.format(latestDate));
//		System.out.println(activeLast);
//		System.out.println(deathsLast);
//		System.out.println(recoveredLast);
		
		model.addAttribute("totalActive", activeLast);
		model.addAttribute("totalDeaths", deathsLast);
		model.addAttribute("totalRecovered", recoveredLast);
		model.addAttribute("latestDate", df.format(latestDate));
		model.addAttribute("totalConfirmed",confirmed);
		
		//Global total amount -tests
		List<CountryData> all = countryDataRepository.findByOrderByCountry();
		int totalTests = 0;
		int totalCases = 0;
		for(CountryData country : all) {
			Integer tests = country.getTests();
			totalTests += tests;
			
			Integer cases = country.getCases();
			totalCases += cases;
		}
		int testsNonCases = totalTests - totalCases;
		model.addAttribute("totalTests", totalTests);
		model.addAttribute("testedNonCase", testsNonCases);
			
		return "global"; 
	}
		
	@GetMapping("/continent") //continent view
	public String continent(Model model) {
		List<CountryData> europe = countryDataRepository.findByContinent("Europe");
		List<CountryData> asia = countryDataRepository.findByContinent("Asia");
		List<CountryData> africa =countryDataRepository.findByContinent("Africa");
		List<CountryData> oceania = countryDataRepository.findByContinent("Australia/Oceania");
		List<CountryData> north_america =countryDataRepository.findByContinent("North America");
		List<CountryData> south_america =countryDataRepository.findByContinent("South America");
			
		//extract update time from GWT
		CountryData UK_data = countryDataRepository.findByCountry("UK");	
		model.addAttribute("UpdatedAt", df.format(UK_data.getDate()));
		
		//create a Map of country and data for Europe
		Map<String, Integer> casesEU = new HashMap<String, Integer>();
		
		//create aggregated sum 
		int sumActiveEU = 0;
		int sumDeathsEU = 0;
		int	sumRecoveredEU = 0;
		int sumTestsEU =0;
		
		for(CountryData country : europe ) {
			String name = country.getCountry();
			Integer cases = country.getCases();	
			casesEU.put(name, cases);	
			
			int active = country.getActive();
			sumActiveEU += active;
			
			int deaths = country.getDeaths();
			sumDeathsEU += deaths;
			
			int recovered = country.getRecovered();
			sumRecoveredEU += recovered;	
			
			int tested = country.getTests();
			sumTestsEU += tested;
		}
		model.addAttribute("europeList", casesEU);
//		System.out.println(casesEU);
				
		//create a Map of country and data for Asia
		Map<String, Integer> casesAsia = new HashMap<String, Integer>();			
		
		//create aggregated sum 
		int sumActiveAsia = 0;
		int sumDeathsAsia = 0;
		int	sumRecoveredAsia = 0;
		int sumTestsAsia =0;
		
		for(CountryData country : asia ) {
			String name = country.getCountry();
			Integer cases = country.getCases();				
			casesAsia.put(name, cases);	
			
			int active = country.getActive();
			sumActiveAsia += active;
			
			int deaths = country.getDeaths();
			sumDeathsAsia += deaths;
			
			int recovered = country.getRecovered();
			sumRecoveredAsia += recovered;	
			
			int tested = country.getTests();
			sumTestsAsia += tested;
		}		
		model.addAttribute("asiaList", casesAsia);
//		System.out.println(casesAsia);		
		
		//create a Map of country and data for Africa
		Map<String, Integer> casesAfrica = new HashMap<String, Integer>();	
		
		//create aggregated sum 
		int sumActiveAfrica = 0;
		int sumDeathsAfrica= 0;
		int	sumRecoveredAfrica = 0;
		int sumTestsAfrica =0;
		
		for(CountryData country : africa ) {
			String name = country.getCountry();
			Integer cases = country.getCases();			
			casesAfrica.put(name, cases);	
			
			int active = country.getActive();
			sumActiveAfrica += active;
			
			int deaths = country.getDeaths();
			sumDeathsAfrica += deaths;
			
			int recovered = country.getRecovered();
			sumRecoveredAfrica += recovered;	
			
			int tested = country.getTests();
			sumTestsAfrica += tested;
			
		}	
		model.addAttribute("africaList", casesAfrica);
//		System.out.println(casesAfrica);
						
		//create a Map of country and data for Oceania
		Map<String, Integer> casesOceania = new HashMap<String, Integer>();	
		
		//create aggregated sum 
		int sumActiveOceania = 0;
		int sumDeathsOceania= 0;
		int	sumRecoveredOceania = 0;
		int sumTestsOceania =0;
		
		for(CountryData country : oceania ) {
			String name = country.getCountry();
			Integer cases = country.getCases();			
			casesOceania.put(name, cases);	
			
			int active = country.getActive();
			sumActiveOceania += active;
				
			int deaths = country.getDeaths();
			sumDeathsOceania += deaths;
			
			int recovered = country.getRecovered();
			sumRecoveredOceania += recovered;	
			
			int tested = country.getTests();
			sumTestsOceania += tested;
			
		}	
		model.addAttribute("oceaniaList", casesOceania);
//		System.out.println(casesOceania);
							
		//create a Map of country and data for North America
		Map<String, Integer> casesNorthAmerica = new HashMap<String, Integer>();	
		
		//create aggregated sum 
		int sumActiveNorthAmerica = 0;
		int sumDeathsNorthAmerica= 0;
		int	sumRecoveredNorthAmerica= 0;
		int sumTestsNorthAmerica =0;
		
		for(CountryData country : north_america ) {
			String name = country.getCountry();
			Integer cases = country.getCases();
			casesNorthAmerica.put(name, cases);	
			
			int active = country.getActive();
			sumActiveNorthAmerica += active;
				
			int deaths = country.getDeaths();
			sumDeathsNorthAmerica += deaths;
			
			int recovered = country.getRecovered();
			sumRecoveredNorthAmerica += recovered;
			
			int tested = country.getTests();
			sumTestsNorthAmerica += tested;
		}
		model.addAttribute("northAmericaList", casesNorthAmerica);	
//		System.out.println(casesNorthAmerica);
						
		//create a Map object of country and data for South America
		Map<String, Integer> casesSouthAmerica = new HashMap<String, Integer>();	
		
		//create aggregated sum 
		int sumActiveSouthAmerica = 0;
		int sumDeathsSouthAmerica= 0;
		int	sumRecoveredSouthAmerica= 0;
		int sumTestsSouthAmerica =0;
		
		for(CountryData country : south_america ) {
			String name = country.getCountry();
			Integer cases = country.getCases();		
			casesSouthAmerica.put(name, cases);
			
			int active = country.getActive();
			sumActiveSouthAmerica += active;
				
			int deaths = country.getDeaths();
			sumDeathsSouthAmerica += deaths;
			
			int recovered = country.getRecovered();
			sumRecoveredSouthAmerica += recovered;
			
			int tested = country.getTests();
			sumTestsSouthAmerica += tested;
		}
		model.addAttribute("southAmericaList", casesSouthAmerica);	
//		System.out.println(casesSouthAmerica);
	
		List<Integer> activeContinent = Arrays.asList(sumActiveEU, 
				sumActiveAsia, sumActiveAfrica, sumActiveOceania, 
				sumActiveNorthAmerica, sumActiveSouthAmerica);
		model.addAttribute("active", activeContinent);
		
		List<Integer> deathsContinent = Arrays.asList(sumDeathsEU, 
				sumDeathsAsia, sumDeathsAfrica, sumDeathsOceania, 
				sumDeathsNorthAmerica, sumDeathsSouthAmerica);
		model.addAttribute("deaths", deathsContinent);
		
		List<Integer> recoveredContinent = Arrays.asList(sumRecoveredEU, 
				sumRecoveredAsia, sumRecoveredAfrica, sumRecoveredOceania, 
				sumRecoveredNorthAmerica, sumRecoveredSouthAmerica);
		model.addAttribute("recovered", recoveredContinent);
		
		List<Integer> testedContinent = Arrays.asList(sumTestsEU, 
				sumTestsAsia, sumTestsAfrica, sumTestsOceania, 
				sumTestsNorthAmerica, sumTestsSouthAmerica);
		model.addAttribute("tested", testedContinent);
		
		return "continent"; 
	}
	
	@GetMapping("/countries") //All countries stats
	public String countrySnapshot(Model model, String keyword) {
		//extract GWT time of updating
		CountryUpdates country = countryUpdatesRepository.findByName("UK");
		model.addAttribute("UpdatedAt", df.format(country.getUpdated_at()));
		
		//get updates from all countries		
		if(keyword != null) {
				model.addAttribute("countries", countryUpdatesRepository.findByKeyword(keyword));
		}
		else {		
			model.addAttribute("countries", countryUpdatesRepository.findByOrderByName());
		}	
		return "countries";
	}
	
	 @GetMapping(value = {"/search/{code}"}) //country timeline & totals
	 public String forceReturn(@PathVariable("code") String code, Model model, RedirectAttributes redirectAttributes) {
	
		 	//extract country name from code
			CountryUpdates country = countryUpdatesRepository.findByCode(code);
			String name = country.getName();			
			model.addAttribute("countryName", name + " All Cases");	
			model.addAttribute("confirmedPerMillion", country.getLatest_data().getCalculated().getCases_per_million_population());
			
			int population = country.getPopulation();	
			System.out.println(population);
			
			//extract totals
			CountryData data = countryDataRepository.findByCountry(name);
			if (data != null && population > 0) {
				Date latestDate = data.getDate();
				Integer activeTotal = data.getActive() ;
				Integer deathsTotal = data.getDeaths();
				Integer recoveredTotal = data.getRecovered();
				Integer confirmedTotal = data.getCases();
				Integer testsTotal = data.getTests();
	
				int testNonCases = testsTotal - confirmedTotal;
				
//				System.out.println(df.format(latestDate));
//				System.out.println(activeTotal);
//				System.out.println(deathsTotal);
//				System.out.println(recoveredTotal);
				System.out.println(testsTotal);
				
				model.addAttribute("totalActive", activeTotal);
				model.addAttribute("totalDeaths", deathsTotal);
				model.addAttribute("totalRecovered", recoveredTotal);
				model.addAttribute("latestDate", df.format(latestDate));
				model.addAttribute("totalConfirmed",confirmedTotal);
				if(testsTotal > 0) {
					model.addAttribute("totalTests", "Total Tests: " + testsTotal);
				}else {
					model.addAttribute("totalTests", "No tests reported");
				}
				
				model.addAttribute("testedNonCase", testNonCases);					
				float testsPerMillion = testsTotal/(population/100000);
				System.out.println(testsPerMillion);
				model.addAttribute("testsPerMillion", testsPerMillion);
				
			}
			else {
				CountryUpdates data2 = countryUpdatesRepository.findByName(name);
				if(data2 != null) {
					Integer confirmed = data2.getLatest_data().getConfirmed();
					if (confirmed > 0) {
						//alert message sent to countries table
						redirectAttributes.addFlashAttribute("message", "No detailed data for " + name + ". Total Cases: " + confirmed);
						return "redirect:../countries";						
					}else {
						//alert message sends to user session bookstore
						redirectAttributes.addFlashAttribute("message", "No valid data for: " + name);
						return "redirect:../countries";						
					}
				}
			}
		 
			final String link = "https://corona-api.com/countries/"+ code;
			 
			RestTemplate restTemplate = new RestTemplate();
			 
			 //fetch country timeline from "ABOUT-CORONA.NET" (https://corona-api.com/countries/{code})
			CountryTimelineWrapper response3 = restTemplate.getForObject(link, CountryTimelineWrapper.class); 			
			CountryTimeline value = response3.getData();
			List<Timeline> timelines = value.getTimeline();		
			
			if(timelines.isEmpty() == false) {
				List<String> dates = new ArrayList<String>();			//initialise a list for dates in String	
				List<Integer> activeData = new ArrayList<Integer>();	//initialise a list for active			
				List<Integer> deathData = new ArrayList<Integer>();		//initialise a list for death
				List<Integer> recoveredData = new ArrayList<Integer>();	//initialise a list for recovered		
								
				for(Timeline timeline: timelines) {			
					Integer deaths = timeline.getDeaths();
					Integer recovered = timeline.getRecovered();
					Integer active = timeline.getActive();
					Date date = timeline.getDate();
						
		//			System.out.println(timeline.toString());					
		
					dates.add(df_date.format(date)); //prepare the date format for timeline graph
					activeData.add(active);
					deathData.add(deaths);
					recoveredData.add(recovered);
				}
				model.addAttribute("dates", dates);
				model.addAttribute("active", activeData);
				model.addAttribute("deaths", deathData);
				model.addAttribute("recovered", recoveredData);
			}
			else {
				System.out.println("There is no timeline indicated for this country.");
				//alert message sent to country view
				model.addAttribute("message", "There is no timeline indicated for this country");
			}
						
		return "view"; 	
	 }
	 
}
