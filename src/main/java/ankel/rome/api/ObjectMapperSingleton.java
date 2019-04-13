package ankel.rome.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Keep a single instance of ObjectMapper to keep configuration uniform across different layers.
 *
 * Ideally, this should be @Singleton injected, but for execution speed, i leave it as singleton for now.
 *
 * @author Binh Tran
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperSingleton
{
  private static ObjectMapper mapper;

  public static synchronized ObjectMapper getInstance()
  {
    if (mapper == null)
    {
      mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
      mapper.registerModule(new Jdk8Module());
    }

    return mapper;
  }
}
