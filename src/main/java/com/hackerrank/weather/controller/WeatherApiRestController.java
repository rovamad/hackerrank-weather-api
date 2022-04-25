package com.hackerrank.weather.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RestController;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import com.hackerrank.weather.service.WeatherService;
import com.hackerrank.weather.model.Weather;

@RestController
public class WeatherApiRestController {

  @Autowired
  WeatherService weatherService;

  @ResponseBody
  @PostMapping(value = "/weather")
  public ResponseEntity<Weather> postWeather(@RequestBody Weather weather) throws IOException {
    return weatherService.postWeather(weather);
  }

  @ResponseBody
  @GetMapping(value = "/weather")
  public List<Weather> getAllWeathers() {
      return weatherService.getAllWeathers();
  }

  @GetMapping(value = "/weather/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ResponseEntity<Weather> getSpecificWeather(@PathVariable(name = "id", required = true) Integer id) {
      return  weatherService.getWeather(id);
  }

  @ResponseBody
  @DeleteMapping(value = "/weather/{id}")
  public ResponseEntity<String> deleteWeather(@PathVariable(name = "id", required = true) Integer id) {
    this.weatherService.deleteWeather(id);

    return ResponseEntity.status(HttpStatus.SC_NO_CONTENT).body("DELETED (CODE 204)\n");
  }
}
