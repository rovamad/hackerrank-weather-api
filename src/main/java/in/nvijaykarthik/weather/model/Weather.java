package in.nvijaykarthik.weather.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Weather {
    
    @Id
    private Long id;
    
    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL,orphanRemoval = true)
    @MapsId
    private Location location;
    
    @ElementCollection
    private List<Float> temperature;

    public Weather() {
    }

    public Weather(Long id, Date date, Location location, List<Float> temperature) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.temperature = temperature;
    }

    @Override
	public String toString() {
		return "Weather [id=" + id + ", dateRecorded=" + date + ", location=" + location + ", temperature="
				+ temperature + "]";
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Float> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Float> temperature) {
        this.temperature = temperature;
    }
}
