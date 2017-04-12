package timestamps.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import timestamps.dao.StatisticsDAO;
import timestamps.models.Statistics;

public class StatisticsDAOImpl implements StatisticsDAO {
	
	InfluxDB influxDB;
	String dbName;
	
	public StatisticsDAOImpl(String url, String user, String password, String dbName){
		this.influxDB = InfluxDBFactory.connect(url, user, password);
		this.dbName = dbName;
	}

	@Override
	public List<Statistics> getForEntity(BigInteger ID) {
		return null;
	}

	@Override
	public void write(Statistics stat) {
		Map<String, Object> tags = initMap(stat);
		
		
		BatchPoints batchPoint = BatchPoints
				.database(dbName)
				.tag("async", "true")
				.retentionPolicy("default")
				.consistency(ConsistencyLevel.ALL)
				.build();
		
        Point point = Point.measurement("entStat").fields(tags).build(); 
        
        batchPoint.point(point);
        
        influxDB.write(batchPoint);
		
	}
	
	private Map<String, Object> initMap(Statistics stat) {
		Map<String, Object> result = new HashMap<>();
		
		result.put("cpu_temperature", stat.getTemperature());
		result.put("ram_load", stat.getrAMLoad());
		result.put("hdd_space", stat.gethDDSpace());
		result.put("entity_id", stat.getEntityID());
		
		return result;
	}

}
