package ankel.rome.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

/**
 * @author Binh Tran
 */
@Slf4j
public class EventJsonSerde implements Serde<Event>
{
  @Override
  public void configure(Map<String, ?> configs, boolean isKey)
  {
    // no op, prefer pre-configured object mapper
  }

  @Override
  public void close()
  {
    // no op.
  }

  @Override
  public Serializer<Event> serializer()
  {
    return new EventJsonSerializer();
  }

  @Override
  public Deserializer<Event> deserializer()
  {
    return new EventJsonDeserializer();
  }

  public static class EventJsonSerializer implements Serializer<Event>
  {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey)
    {
      // no op
    }

    @Override
    public byte[] serialize(String topic, Event data)
    {
      try
      {
        return ObjectMapperSingleton.getInstance().writeValueAsBytes(data);
      } catch (JsonProcessingException e)
      {
        log.error("Failed to serialize event [{}]", data, e);
        throw new RuntimeException(e);
      }
    }

    @Override
    public void close()
    {
      // no op
    }
  }

  public static class EventJsonDeserializer implements Deserializer<Event>
  {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey)
    {
      // no op
    }

    @Override
    public Event deserialize(String topic, byte[] data)
    {
      try
      {
        return ObjectMapperSingleton.getInstance().readValue(data, Event.class);
      } catch (IOException e)
      {
        log.error("Failed to deserialize event [{}]", data, e);
        throw new RuntimeException(e);
      }
    }

    @Override
    public void close()
    {
      // no op
    }
  }
}
