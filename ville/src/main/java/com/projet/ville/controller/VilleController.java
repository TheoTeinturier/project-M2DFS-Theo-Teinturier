package com.projet.ville.controller;

import com.google.gson.Gson;
import com.projet.ville.VilleApplication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Api(value = "Ce controller permet de visualiser la météo d'une ville en fonction de son nom, ainsi que la météo de cette dernière pour le lendemain ou les 5j qui suivent")
@RestController

public class VilleController {
    @Autowired
    RestTemplate restTemplate;
    String nomVille;

    //Permet de récupérer les détails d'une ville pour récupérer son code
    @ApiOperation(value = "Recupération detailVille", response = VilleController.class, tags = "getDetailVille")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message="Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")
    })
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

    //Retour de la météo le jour suivant pour une ville en passant le nom en paramètre

    @ApiOperation(value = "Obtention météo pour demain", response = VilleController.class, tags = "getMeteoDemain")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message="Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")
    })
    @RequestMapping(value = "/getMeteoDemain/{nomVille}", method = RequestMethod.GET)
    public String getMeteoDemain(@PathVariable String nomVille) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=q80rdb2MDAlzUFIxOE18UaeMAKZwbaA1&q=" + nomVille + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, nomVille).getBody();

        String json = response;
        json = json.substring(1, json.length() - 1);
        Map jsonJavaRootObject = new Gson().fromJson(json, Map.class);
        String idVille = (String) jsonJavaRootObject.get("Key");

        String resultatFinal = restTemplate.exchange("http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + idVille + "?apikey=q80rdb2MDAlzUFIxOE18UaeMAKZwbaA1&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, idVille).getBody();

        return resultatFinal;
    }

    //Retour de la météo le jour suivant pour une ville en passant le nom en paramètre

    @ApiOperation(value = "Obtention météo pour 5j", response = VilleController.class, tags = "getMeteo5j")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message="Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")
    })
    @RequestMapping(value = "/getMeteo5j/{nomVille}", method = RequestMethod.GET)
    public String getMeteo5j(@PathVariable String nomVille) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=q80rdb2MDAlzUFIxOE18UaeMAKZwbaA1&q=" + nomVille + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, nomVille).getBody();

        String json = response;
        json = json.substring(1, json.length() - 1);
        Map jsonJavaRootObject = new Gson().fromJson(json, Map.class);
        String idVille = (String) jsonJavaRootObject.get("Key");

        String resultatFinal = restTemplate.exchange("http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + idVille + "?apikey=q80rdb2MDAlzUFIxOE18UaeMAKZwbaA1&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, idVille).getBody();

        return resultatFinal;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
