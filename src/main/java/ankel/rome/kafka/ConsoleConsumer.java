package ankel.rome.kafka;

import ankel.rome.api.Event;
import ankel.rome.api.EventJsonSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

/**
 * @author Binh Tran
 */
@Slf4j
public class ConsoleConsumer
{
  private final KStream<Integer, Event> kstream1;
  private final KafkaStreams streams;

  public ConsoleConsumer(final String bootstrapServers)
  {
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "rome-console-consumer");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, EventJsonSerde.class);

    StreamsBuilder builder = new StreamsBuilder();

    kstream1 = builder.stream(Event.EVENT_TYPE_1);

    kstream1.foreach(consolePrint(Event.EVENT_TYPE_1));

    streams = new KafkaStreams(builder.build(), props);
  }

  public void start()
  {
    streams.start();
  }

  public void stop()
  {
    streams.close();
  }

  private ForeachAction<? super Integer, ? super Event> consolePrint(String topic)
  {
    final String template = String.format("[Topic: %s]", topic);

    return (k, v) -> log.info(String.format("%s\t\t%4d-%s", template, k, v));
  }
}
