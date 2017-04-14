package timestamps.kafkaintegration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


import timestamps.models.Statistics;

@PropertySource(encoding = "UTF-8", value = {"classpath:kafkaproducer.properties"})
public class Producer {
	
	private KafkaProducer<String, String> producer;
	
	private String topic;
	
	public Producer() throws FileNotFoundException, IOException {
		
		Properties props = new Properties();
		Resource resource = new ClassPathResource("kafkaproducer.properties");
		props.load(resource.getInputStream());
		
		this.topic = props.getProperty("topic");
		
		props.remove("topic");
		producer = new KafkaProducer<>(props);	
		
		
	}
	
	public void send(Statistics stat){
		
		producer.send(new ProducerRecord<String, String>(topic, stat.getEntityID().toString(), stat.toString()));
		producer.close();
		
	}

}
