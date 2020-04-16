package com.project.pandemic;

import java.time.Instant;
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
	public CommandLineRunner pandemicApp(RestTemplate restTemplate, CountryRepository countryRepository, TimelineRepository timelineRepository) throws Exception {
		return args -> {
			
			ResponseEntity<CountryData[]> response = restTemplate.getForEntity(
					"https://corona.lmao.ninja/countries", CountryData[].class);
			CountryData[] countrylist = response.getBody();
					
			for (CountryData countryData : countrylist) {
				
				String country = countryData.getCountry();
				Integer cases = countryData.getCases();
				Integer deaths = countryData.getDeaths();
				Integer critical = countryData.getCritical();
				Integer recovered = countryData.getRecovered();
				Integer active = countryData.getActive();
				Long updated = countryData.getUpdated();
				Instant date = Instant.ofEpochMilli(updated);
				
				log.info(countryData.toString());	
				
				countryRepository.save(new CountryData(country, cases, deaths, critical, recovered, active, date));				
			
			}
			
			TimelineWrapper response1 = restTemplate.getForObject("https://corona-api.com/timeline", TimelineWrapper.class); 
			List<Timeline> data = response1.getData();
			
			for(Timeline timeline : data) {
				
				Integer confirmed = timeline.getConfirmed();
				Integer deaths = timeline.getDeaths();
				Integer recovered = timeline.getRecovered();
				Integer active = timeline.getActive();
				Date date = timeline.getDate();
				
				log.info(timeline.toString());
			
				timelineRepository.save(new Timeline(confirmed, deaths, recovered, active, date));			
			}
			
			HistoryWrapper response2 = restTemplate.getForObject("https://corona-api.com/countries/FR", HistoryWrapper.class); 
			CountryHistory value = response2.getData();
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
