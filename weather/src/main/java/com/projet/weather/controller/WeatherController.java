package com.projet.weather.controller;

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

@Api(value = "Ce controller permet de visualiser la météo d'une ville en fonction de son id")
@RestController
public class WeatherController {


        @Autowired
        RestTemplate restTemplate;
        //Permet de récupérer les informations météo d'une ville en fonction de son id
        @ApiOperation(value = "Récupération météo", response = WeatherController.class, tags = "getMeteoIdVille")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "Success"),
                @ApiResponse(code = 401, message = "Unauthorized"),
                @ApiResponse(code = 403, message="Forbidden"),
                @ApiResponse(code = 404, message = "Not Found"),
                @ApiResponse(code = 500, message = "Failure")
        })
        @RequestMapping(value = "/meteoVille/{key}", method = RequestMethod.GET)
        public String getMeteoIdVille(@PathVariable String key) {
            String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{key}?apikey=q80rdb2MDAlzUFIxOE18UaeMAKZwbaA1&language=fr-fr",
                    HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                    }, key).getBody();
            return response;


        }




        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }


}
