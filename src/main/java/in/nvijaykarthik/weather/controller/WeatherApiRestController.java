package in.nvijaykarthik.weather.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.nvijaykarthik.weather.model.Weather;
import in.nvijaykarthik.weather.repository.LocationRepository;
import in.nvijaykarthik.weather.repository.WeatherRepository;

@RestController
public class WeatherApiRestController {

	private final Logger log = LoggerFactory.getLogger(WeatherApiRestController.class);

	@Autowired
	LocationRepository locationRepo;

	@Autowired
	WeatherRepository weatherRepo;

	@RequestMapping(path = "/weather", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Weather>> getAllWeather(@RequestParam(required = false) String date,
			@RequestParam(required = false) Float lat, @RequestParam(required = false) Float lon)
			throws ParseException {
		
		Date dt=null;
		
		if(null!=date) {
		  dt = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		}
		
		if (null != dt && null != lat && null != lon) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
		if (null == date && null == lat && null == lon) {

			List<Weather> weather = weatherRepo.findAll();
			log.info("Find all Weather : {}",weather);
			if (CollectionUtils.isEmpty(weather)) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
			}
			return new ResponseEntity<>(weather, HttpStatus.OK);
		} else if (null != dt) {
			List<Weather> weather = weatherRepo.findByDate(dt);
			log.info("Find By Date Weather : {}",weather);
			if (CollectionUtils.isEmpty(weather)) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(weather, HttpStatus.OK);
		} else if (null != lat && null != lon) {
			List<Weather> weather = weatherRepo.findByLocationLatAndLocationLon(lat, lon);
			log.info("Find By Lat & Lon Weather : {}",weather);
			if (CollectionUtils.isEmpty(weather)) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY);
			}
			return new ResponseEntity<>(weather, HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		
	}

	@RequestMapping(path = "/weather", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Weather> SaveWeather(@RequestBody Weather we) {
		ResponseEntity<Weather> response;
	
		Weather weather = weatherRepo.findOne(we.getId());
		log.info("Save find By id : {}",weather);
		if (null != weather) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			return response;
		}
		we.getLocation().setId(we.getId());
		weather = weatherRepo.save(we);
		response = new ResponseEntity<>(weather, HttpStatus.CREATED);
		return response;
	}

	@RequestMapping(path = "/erase", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> delAllWeather(@RequestParam(required=false) String start, @RequestParam(required=false) String end,
			@RequestParam(required=false) Float lat, @RequestParam(required=false) Float lon) throws ParseException {
		
		
		ResponseEntity<String> response;
		if (null == start && null == end && null == lat && null == lon) {
			weatherRepo.deleteAll();
			response = new ResponseEntity<>("Successfully deleted ", HttpStatus.OK);
			return response;
		} else if (null != start && null != end && null != lat && null != lon) {
			Date sdt = new SimpleDateFormat("yyyy-MM-dd").parse(start);
			Date edt = new SimpleDateFormat("yyyy-MM-dd").parse(end);
			List<Weather> weather=weatherRepo.findByLocationLatAndLocationLon(lat, lon);
			List<Weather> selectedWeather=weather.stream().filter(w->{
										return !sdt.after(w.getDate()) && !edt.before(w.getDate());
										}).collect(Collectors.toList());

			selectedWeather.forEach(w->{
				weatherRepo.delete(w.getId());
			});
			
			response = new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
			return response;
		}

		response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return response;

	}
	
}
