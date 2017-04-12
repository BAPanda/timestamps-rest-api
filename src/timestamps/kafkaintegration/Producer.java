package timestamps.kafkaintegration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import timestamps.models.Statistics;

public class Producer {
	
	private KafkaProducer<BigInteger, String> producer;
	private String topic;
	
	public Producer() throws FileNotFoundException, IOException {
		
		Properties props = new Properties();
		props.load(new FileInputStream(new File("kafkaproducer.properties")));
		
		this.topic = props.getProperty("topic");
		
		props.remove("topic");
		producer = new KafkaProducer<>(props);	
		
		
	}
	
	public void send(Statistics stat){
		
		producer.send(new ProducerRecord<BigInteger, String>(topic, stat.getEntityID(), stat.toString()));
		
	}

}
