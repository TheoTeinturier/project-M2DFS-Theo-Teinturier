package com.projet.ville;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class VilleApplication {

	public static void main(String[] args) {
		SpringApplication.run(VilleApplication.class, args);
	}
	@RestController
	public class CurrentController {

		@Autowired
		RestTemplate restTemplate;
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
