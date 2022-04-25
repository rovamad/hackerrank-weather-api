package in.nvijaykarthik.weather.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.nvijaykarthik.weather.model.Weather;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Long>{
    
    List<Weather> findByDate(Date date);
    List<Weather> findByLocationLatAndLocationLon(Float lat,Float lon);

    @Transactional
    @Modifying
    @Query("Delete from Weather w where w.date>=:start and w.date<=:end and w.location.lat=:lat and w.location.lon=:lon")
    void deleteByCriteria(@Param(value = "start") Date start, @Param(value = "end") Date end,  @Param(value = "lat") Float lat,  @Param(value = "lon") Float lon);
    
}
