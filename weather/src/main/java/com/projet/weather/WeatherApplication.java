package com.projet.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class 	WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);

	}
	@RestController
	public class CurrentController {

		@Autowired
		RestTemplate restTemplate;

		@RequestMapping(value = "/current/{key}", method = RequestMethod.GET)
		public String getWeather(@PathVariable String key) {
			String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{key}?apikey=tiRwrZIHTvt6t8A7AWQG6k3ugiDnHrTX&language=fr-fr",
					HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
					}, key).getBody();
			return response;
		}

		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}


	}
	}
