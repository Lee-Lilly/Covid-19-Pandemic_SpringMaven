package com.project.pandemic;

//import java.util.Date;
//import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import com.project.pandemic.domain.*;

@SpringBootApplication
public class PandemicApplication {
	
//	private static final Logger log = LoggerFactory.getLogger(PandemicApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(PandemicApplication.class, args);
	}

//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.build();
//	}
//	
//
//	@Bean
//	public CommandLineRunner pandemicApp(RestTemplate restTemplate, CountryDataRepository countryDataRepository, CountryUpdatesRepository countryUpdatesRepository) throws Exception {
//		return args -> { 
//			
//			//fetch today's data of all countries through "NOVEL COVID API" (https://corona.lmao.ninja)
//			//Data source: "Worldometers".
//			ResponseEntity<CountryData[]> response = restTemplate.getForEntity(
//					"https://corona.lmao.ninja/v2/countries", CountryData[].class);
//			CountryData[] dataList = response.getBody();
//					
//			for (CountryData countryData : dataList) {
//				
//				String country = countryData.getCountry();
//				String continent = countryData.getContinent();
//				Integer cases = countryData.getCases();
//				Integer deaths = countryData.getDeaths();
//				Integer critical = countryData.getCritical();
//				Integer recovered = countryData.getRecovered();
//				Integer active = countryData.getActive();
//				Integer tests = countryData.getTests();
//				Long updated = countryData.getUpdated();
//				countryData.setDate(updated);
//				Date date = countryData.getDate();
//				
//			//	log.info(countryData.toString());	
//				
//				//save to database
//				countryDataRepository.save(new CountryData(country, continent, cases, deaths, critical, recovered, active, tests, date));				
//			
//			}
//			
////			//fetch global timeline from "ABOUT-CORONA.NET" (https://about-corona.net) //to save running time, fetch the online data.
////			//Data source: "Johns Hopkins CSSE"
////			TimelineWrapper response1 = restTemplate.getForObject("https://corona-api.com/timeline", TimelineWrapper.class); 
////			List<Timeline> timelineList = response1.getData();
////			
////			for(Timeline timeline : timelineList) {
////				
////				Integer confirmed = timeline.getConfirmed();
////				Integer deaths = timeline.getDeaths();
////				Integer recovered = timeline.getRecovered();
////				Integer active = timeline.getActive();
////				Date date = timeline.getDate();
////				
////			//	log.info(timeline.toString());
////			
////				//save to database
////				timelineRepository.save(new Timeline(confirmed, deaths, recovered, active, date));			
////			}
//			
//			//fetch calculated updates for all countries from "ABOUT-CORONA.NET" (https://about-corona.net)
//			CountryUpdatesWrapper response2 = restTemplate.getForObject("https://corona-api.com/countries", CountryUpdatesWrapper.class);
//			List<CountryUpdates> countryUpdateList = response2.getData();
//			
//			for (CountryUpdates countryUpdate: countryUpdateList) {
//				
//				String name = countryUpdate.getName();
//				String code = countryUpdate.getCode();
//				Integer population = countryUpdate.getPopulation();
//				Date date = countryUpdate.getUpdated_at();
//				CountryLatest latestData = countryUpdate.getLatest_data();
//				TodayNews today = countryUpdate.getToday();
//			//	log.info(countryUpdate.toString());
//				
//				//save to database
//				countryUpdatesRepository.save(new CountryUpdates(name, code, population, date, latestData, today));
//				
//			}
//			
//		};
//	}
}
