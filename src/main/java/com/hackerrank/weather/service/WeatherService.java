package com.hackerrank.weather.service;

import java.util.ArrayList;
import java.util.List;
import com.hackerrank.weather.repository.WeatherRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.hackerrank.weather.model.Weather;
import java.util.Optional;

@Slf4j
@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    public ResponseEntity<Weather> postWeather(Weather weather){
        ResponseEntity<Weather> response;
        weatherRepository.save(weather);
        response = new ResponseEntity<>(weather, HttpStatus.CREATED);
        return response;
    }

    public List<Weather> getAllWeathers(){
      return weatherRepository.findAll();
    }

    public ResponseEntity<Weather> getWeather(Integer id){
      Optional<Weather> value = weatherRepository.findById(id);
      if(value.isPresent()){
          return new ResponseEntity<>(value.get(), HttpStatus.OK);
      } else {
          return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
    }

    public void deleteWeather(Integer id){
      weatherRepository.deleteById(id);
    }
}