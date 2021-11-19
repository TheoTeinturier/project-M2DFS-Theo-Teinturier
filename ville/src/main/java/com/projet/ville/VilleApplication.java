package com.projet.ville;

import com.google.gson.Gson;
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

import java.util.Map;

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
			String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=q80rdb2MDAlzUFIxOE18UaeMAKZwbaA1&q=" + nomVille + "&language=fr-fr",
					HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, nomVille).getBody();

		// On parse le resultat de la requête pour récupérer le code de la ville
			String json = response;
			json = json.substring(1, json.length() - 1);
			Map jsonJavaRootObject = new Gson().fromJson(json, Map.class);
			//resultat de la ville dans idVille qui devient NomVille
			String idVille = (String) jsonJavaRootObject.get("Key");
			//On utilise l'id de la ville et on lui passe son nom.
			String resultatFinal = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/" + idVille + "?apikey=q80rdb2MDAlzUFIxOE18UaeMAKZwbaA1&language=fr-fr",
					HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, idVille).getBody();

			return resultatFinal;
			//return response;
		}


		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}


	}
}
