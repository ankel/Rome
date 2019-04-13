package ankel.rome.kafka;

import ankel.rome.api.Event;
import ankel.rome.api.EventJsonSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;

/**
 * @author Binh Tran
 */
@Slf4j
public class Producer
{
  private final KafkaProducer<Integer, Event> producer;

  public Producer(final String bootstrapServers)
  {

    Properties props = new Properties();
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "rome-example-producer");
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventJsonSerde.EventJsonSerializer.class);

    producer = new KafkaProducer<>(props);
  }

  public void produce(final int key, final Event event)
  {
    producer.send(
        new ProducerRecord<>(
            event.getEventType(),
            key,
            event),
        (metadata, exception) -> {
          if (exception != null)
          {
            log.error("Failed to send event", exception);
          }
        });
  }

  public void stop()
  {
    producer.close();
  }
}
