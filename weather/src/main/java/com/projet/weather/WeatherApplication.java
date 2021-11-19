package com.projet.weather;

import io.swagger.annotations.ApiOperation;
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
		//Permet de récupérer les informations météo d'une ville en fonction de son id
		@ApiOperation(value = "Get details for weather", response = CurrentController.class, tags = "getMeteoIdVille")
		@RequestMapping(value = "/meteoVille/{key}", method = RequestMethod.GET)
		public String getMeteoIdVille(@PathVariable String key) {
			String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{key}?apikey=tiRwrZIHTvt6t8A7AWQG6k3ugiDnHrTX&language=fr-fr",
					HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
					}, key).getBody();
			return response;
		}

		String nomVille;

		//Permet de récupérer les détails d'une ville pour récupérer son code
		@ApiOperation(value = "Recupération detailVille", response = CurrentController.class, tags = "getDetailVille")
		@RequestMapping(value = "/detailVille/{nomVille}", method = RequestMethod.GET)
		public String getCity(@PathVariable String nomVille) {
			String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=tiRwrZIHTvt6t8A7AWQG6k3ugiDnHrTX&q=" + nomVille + "&language=fr-fr",
					HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, nomVille).getBody();
			return response;
		}


		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}


	}
	}
