package in.nvijaykarthik.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.nvijaykarthik.weather.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long>{
}
