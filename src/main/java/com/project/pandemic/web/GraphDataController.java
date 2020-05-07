package com.project.pandemic.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.pandemic.domain.CountryData;
import com.project.pandemic.domain.CountryDataRepository;
import com.project.pandemic.domain.CountryLatest;
import com.project.pandemic.domain.CountryTimeline;
import com.project.pandemic.domain.CountryTimelineWrapper;
import com.project.pandemic.domain.CountryUpdates;
import com.project.pandemic.domain.CountryUpdatesRepository;
import com.project.pandemic.domain.CountryUpdatesWrapper;
import com.project.pandemic.domain.Timeline;
import com.project.pandemic.domain.TimelineWrapper;
import com.project.pandemic.domain.TodayNews;

@Controller
public class GraphDataController {
	
	@Autowired
	private CountryDataRepository countryDataRepository;

	@Autowired
	private CountryUpdatesRepository countryUpdatesRepository;

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
	DateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping({ "/", "/global" })
	public String global(Model model) {
		// RestTemplate for fetching data
		RestTemplate restTemplate = new RestTemplate();

		// fetch global timeline from "ABOUT-CORONA.NET" (https://about-corona.net)
		TimelineWrapper response1 = restTemplate.getForObject("https://corona-api.com/timeline", TimelineWrapper.class);
		List<Timeline> timelines = response1.getData();

		List<String> dates = new ArrayList<String>(); // initialise a list for dates in String
		List<Integer> activeData = new ArrayList<Integer>(); // initialise a list for active
		List<Integer> deathData = new ArrayList<Integer>(); // initialise a list for death
		List<Integer> recoveredData = new ArrayList<Integer>(); // initialise a list for recovered

		for (Timeline timeline : timelines) {
			Date date = timeline.getDate();
			Integer active = timeline.getActive();
			Integer deaths = timeline.getDeaths();
			Integer recovered = timeline.getRecovered();

			dates.add(df_date.format(date)); // prepare the date format for timeline graph
			activeData.add(active);
			deathData.add(deaths);
			recoveredData.add(recovered);
		}
//		System.out.println(dates);
//		System.out.println(activeData);
//		System.out.println(deathData);
//		System.out.println(recoveredData);
		Collections.reverse(dates);
		model.addAttribute("dates", dates);
		Collections.reverse(activeData);
		model.addAttribute("active", activeData);
		Collections.reverse(deathData);
		model.addAttribute("deaths", deathData);
		Collections.reverse(recoveredData);
		model.addAttribute("recovered", recoveredData);

		// Global total amount - the latest updates
		Timeline lastLine = timelines.get(0);
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
		model.addAttribute("totalConfirmed", confirmed);
		
		return "global";
	}

	@GetMapping({"/continent"}) // continent view
	public String continent(Model model) {

		RestTemplate restTemplate = new RestTemplate();
			    
		ResponseEntity<CountryData[]> response = restTemplate.exchange(
				"https://corona.lmao.ninja/v2/countries", HttpMethod.GET, null, CountryData[].class);
		CountryData[] dataList = response.getBody();
		HttpStatus statusCode = response.getStatusCode();
		System.out.println(statusCode);
		
		countryDataRepository.deleteAll();		
		for (CountryData countryData : dataList) {
			
			String country = countryData.getCountry();
			String continent = countryData.getContinent();
			Integer cases = countryData.getCases();
			Integer deaths = countryData.getDeaths();
			Integer critical = countryData.getCritical();
			Integer recovered = countryData.getRecovered();
			Integer active = countryData.getActive();
			Integer tests = countryData.getTests();
			Long updated = countryData.getUpdated();
			countryData.setDate(updated);
			Date date = countryData.getDate();
				
			//save to database
			countryDataRepository.save(new CountryData(country, continent, cases, deaths, critical, recovered, active, tests, date));						
		}
		
		List<CountryData> europe = countryDataRepository.findByContinent("Europe");
		List<CountryData> asia = countryDataRepository.findByContinent("Asia");
		List<CountryData> africa = countryDataRepository.findByContinent("Africa");
		List<CountryData> oceania = countryDataRepository.findByContinent("Australia/Oceania");
		List<CountryData> north_america = countryDataRepository.findByContinent("North America");
		List<CountryData> south_america =countryDataRepository.findByContinent("South America");
				
		// create a Map of country and data for Europe
		Map<String, Integer> casesEU = new HashMap<String, Integer>();

		// create aggregated sum
		int sumActiveEU = 0;
		int sumDeathsEU = 0;
		int sumRecoveredEU = 0;
		int sumTestsEU = 0;

		for (CountryData country : europe) {
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
	//	System.out.println(casesEU);

		// create a Map of country and data for Asia
		Map<String, Integer> casesAsia = new HashMap<String, Integer>();

		// create aggregated sum
		int sumActiveAsia = 0;
		int sumDeathsAsia = 0;
		int sumRecoveredAsia = 0;
		int sumTestsAsia = 0;

		for (CountryData country : asia) {
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

		// create a Map of country and data for Africa
		Map<String, Integer> casesAfrica = new HashMap<String, Integer>();

		// create aggregated sum
		int sumActiveAfrica = 0;
		int sumDeathsAfrica = 0;
		int sumRecoveredAfrica = 0;
		int sumTestsAfrica = 0;

		for (CountryData country : africa) {
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

		// create a Map of country and data for Oceania
		Map<String, Integer> casesOceania = new HashMap<String, Integer>();

		// create aggregated sum
		int sumActiveOceania = 0;
		int sumDeathsOceania = 0;
		int sumRecoveredOceania = 0;
		int sumTestsOceania = 0;

		for (CountryData country : oceania) {
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

		// create a Map of country and data for North America
		Map<String, Integer> casesNorthAmerica = new HashMap<String, Integer>();

		// create aggregated sum
		int sumActiveNorthAmerica = 0;
		int sumDeathsNorthAmerica = 0;
		int sumRecoveredNorthAmerica = 0;
		int sumTestsNorthAmerica = 0;

		for (CountryData country : north_america) {
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

		// create a Map object of country and data for South America
		Map<String, Integer> casesSouthAmerica = new HashMap<String, Integer>();

		// create aggregated sum
		int sumActiveSouthAmerica = 0;
		int sumDeathsSouthAmerica = 0;
		int sumRecoveredSouthAmerica = 0;
		int sumTestsSouthAmerica = 0;

		for (CountryData country : south_america) {
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

		// extract GWT time of updating
		CountryData country = countryDataRepository.findByCountry("UK");
		model.addAttribute("UpdatedAt", df.format(country.getDate()));

		List<Integer> activeContinent = Arrays.asList(sumActiveEU, sumActiveAsia, sumActiveAfrica, sumActiveOceania,
				sumActiveNorthAmerica, sumActiveSouthAmerica);
		model.addAttribute("active", activeContinent);

		List<Integer> deathsContinent = Arrays.asList(sumDeathsEU, sumDeathsAsia, sumDeathsAfrica, sumDeathsOceania,
				sumDeathsNorthAmerica, sumDeathsSouthAmerica);
		model.addAttribute("deaths", deathsContinent);

		List<Integer> recoveredContinent = Arrays.asList(sumRecoveredEU, sumRecoveredAsia, sumRecoveredAfrica,
				sumRecoveredOceania, sumRecoveredNorthAmerica, sumRecoveredSouthAmerica);
		model.addAttribute("recovered", recoveredContinent);

		List<Integer> testedContinent = Arrays.asList(sumTestsEU, sumTestsAsia, sumTestsAfrica, sumTestsOceania,
				sumTestsNorthAmerica, sumTestsSouthAmerica);
		model.addAttribute("tested", testedContinent);
	
		return "continent";
	}

	@GetMapping("/countries") // All countries stats
	public String countrySnapshot(Model model, String keyword) {
		RestTemplate restTemplate = new RestTemplate();

		CountryUpdatesWrapper response = restTemplate.getForObject("https://corona-api.com/countries",
				CountryUpdatesWrapper.class);
		List<CountryUpdates> countryUpdateList = response.getData();
		
		countryUpdatesRepository.deleteAll();
		for (CountryUpdates countryUpdate : countryUpdateList) {

			String name = countryUpdate.getName();
			String code = countryUpdate.getCode();
			Integer population = countryUpdate.getPopulation();
			Date date = countryUpdate.getUpdated_at();
			CountryLatest latestData = countryUpdate.getLatest_data();
			TodayNews today = countryUpdate.getToday();
			// log.info(countryUpdate.toString());

			// save to database
			countryUpdatesRepository.save(new CountryUpdates(name, code, population, date, latestData, today));

		}

		// extract GWT time of updating
		CountryUpdates country = countryUpdatesRepository.findByName("UK");
		model.addAttribute("UpdatedAt", df.format(country.getUpdated_at()));

		// get updates from all countries
		if (keyword != null) {
			model.addAttribute("countries", countryUpdatesRepository.findByKeyword(keyword));
		} else {
			model.addAttribute("countries", countryUpdatesRepository.findByOrderByName());
		}
		return "countries";
	}

	@GetMapping(value = { "/search/{code}" }) // country timeline & totals
	public String forceReturn(@PathVariable("code") String code, Model model, RedirectAttributes redirectAttributes) {
		
		final String link = "https://corona-api.com/countries/" + code;

		RestTemplate restTemplate = new RestTemplate();

		// fetch country timeline from "ABOUT-CORONA.NET"
		// (https://corona-api.com/countries/{code})
		CountryTimelineWrapper response3 = restTemplate.getForObject(link, CountryTimelineWrapper.class);
		CountryTimeline value = response3.getData();
		
		String country = value.getName();
		model.addAttribute("countryName", country);
		
		List<Timeline> timelines = value.getTimeline();

		if (timelines.isEmpty() == false) {
			List<String> dates = new ArrayList<String>(); // initialise a list for dates in String
			List<Integer> activeData = new ArrayList<Integer>(); // initialise a list for active
			List<Integer> deathData = new ArrayList<Integer>(); // initialise a list for death
			List<Integer> recoveredData = new ArrayList<Integer>(); // initialise a list for recovered

			for (Timeline timeline : timelines) {
				Integer deaths = timeline.getDeaths();
				Integer recovered = timeline.getRecovered();
				Integer active = timeline.getActive();
				Date date = timeline.getDate();

				// System.out.println(timeline.toString());

				dates.add(df_date.format(date)); // prepare the date format for timeline graph
				activeData.add(active);
				deathData.add(deaths);
				recoveredData.add(recovered);
			}
			Collections.reverse(dates);
			model.addAttribute("dates", dates);
			Collections.reverse(activeData);
			model.addAttribute("active", activeData);
			Collections.reverse(deathData);
			model.addAttribute("deaths", deathData);
			Collections.reverse(recoveredData);
			model.addAttribute("recovered", recoveredData);
			
			// Global total amount - the latest updates
			Timeline lastLine = timelines.get(0);
			Date latestDate = lastLine.getDate();
			Integer activeLast = lastLine.getActive();
			Integer deathsLast = lastLine.getDeaths();
			Integer recoveredLast = lastLine.getRecovered();
			Integer confirmed = lastLine.getConfirmed();

//					System.out.println(df.format(latestDate));
//					System.out.println(activeLast);
//					System.out.println(deathsLast);
//					System.out.println(recoveredLast);
			
				model.addAttribute("totalActive", activeLast);
				model.addAttribute("totalDeaths", deathsLast);
				model.addAttribute("totalRecovered", recoveredLast);
				model.addAttribute("latestDate", df.format(latestDate));
				model.addAttribute("totalConfirmed", confirmed);		
			
		} else {
			// System.out.println("There is no timeline indicated for country: "+ country);
			// alert message sent to country view
			redirectAttributes.addFlashAttribute("message", "There is no valid data for " + country);
			return "redirect:../countries";
		}
		
		return "country";
	}

}
