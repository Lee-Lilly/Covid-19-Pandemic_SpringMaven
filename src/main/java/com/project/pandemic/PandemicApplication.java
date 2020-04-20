package com.project.pandemic;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.pandemic.domain.*;

@SpringBootApplication
public class PandemicApplication {
	
	private static final Logger log = LoggerFactory.getLogger(PandemicApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(PandemicApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	

	@Bean
	public CommandLineRunner pandemicApp(RestTemplate restTemplate, CountryDataRepository countryDataRepository, TimelineRepository timelineRepository, CountryInfoRepository countryInfoRepository) throws Exception {
		return args -> { //declare: fetched data from various free API services, and all data are from John Hopkins University  
			
			//fetch today's Covid-19 data of all countries from "NOVEL COVID API" (https://corona.lmao.ninja)
			ResponseEntity<CountryData[]> response = restTemplate.getForEntity(
					"https://corona.lmao.ninja/v2/countries", CountryData[].class);
			CountryData[] dataList = response.getBody();
					
			for (CountryData countryData : dataList) {
				
				String country = countryData.getCountry();
				Integer cases = countryData.getCases();
				Integer deaths = countryData.getDeaths();
				Integer critical = countryData.getCritical();
				Integer recovered = countryData.getRecovered();
				Integer active = countryData.getActive();
				Date date = countryData.getDate();
				
				log.info(countryData.toString());	
				
				//save to database
				countryDataRepository.save(new CountryData(country, cases, deaths, critical, recovered, active, date));				
			
			}
			
			//fetch global Covid-19 on timeline from "ABOUT-CORONA.NET" (https://about-corona.net/documentation)
			TimelineWrapper response1 = restTemplate.getForObject("https://corona-api.com/timeline", TimelineWrapper.class); 
			List<Timeline> timelineList = response1.getData();
			
			for(Timeline timeline : timelineList) {
				
				Integer confirmed = timeline.getConfirmed();
				Integer deaths = timeline.getDeaths();
				Integer recovered = timeline.getRecovered();
				Integer active = timeline.getActive();
				Date date = timeline.getDate();
				
				log.info(timeline.toString());
			
				//save to database
				timelineRepository.save(new Timeline(confirmed, deaths, recovered, active, date));			
			}
			
			//fetch country information from "REST COUNTRIES" (https://restcountries.eu/#api-endpoints-code)
			ResponseEntity<CountryInfo[]> response3 = restTemplate.getForEntity(
					"https://restcountries.eu/rest/v2/all", CountryInfo[].class);
			CountryInfo[] countryList = response3.getBody();
			
			for (CountryInfo country: countryList) {
				
				String name = country.getName();
				String alpha2Code = country.getAlpha2Code();
				String alpha3Code = country.getAlpha3Code();
				String region = country.getRegion();
				String subregion = country.getSubregion();
						
				log.info(country.toString());
				
				countryInfoRepository.save(new CountryInfo(name, alpha2Code, alpha3Code, region, subregion));
			}
				
			//fetch country Covid-19 on timeline from "ABOUT-CORONA.NET" (https://about-corona.net/documentation)
			//This is for searching country, not for save to database, will go controller part, need user to feed the ISO-2 code.
			//e.g. https://corona-api.com/countries/{FR}
			CountryTimelineWrapper response2 = restTemplate.getForObject("https://corona-api.com/countries/FR", CountryTimelineWrapper.class); 
			CountryTimeline value = response2.getData();
			String country = value.getName();
			log.info(country);
			
			List<Timeline> timelines = value.getTimeline();				
			
			for(Timeline timeline: timelines) {
				
//				Integer confirmed = timeline.getConfirmed();
//				Integer deaths = timeline.getDeaths();
//				Integer recovered = timeline.getRecovered();
//				Integer active = timeline.getActive();
//				Date date = timeline.getDate();
			
				log.info(timeline.toString());
				
			}
												
		};
	}
}
