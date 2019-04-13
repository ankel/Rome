package ankel.rome.kafka;

import org.testcontainers.containers.KafkaContainer;

/**
 * @author Binh Tran
 */
public class Runner
{
  private KafkaContainer kafka = new KafkaContainer("5.2.0");

  public void start()
  {
    kafka.start();
  }

  public void stop()
  {
    kafka.stop();
  }

  public String getBootstrapAddress()
  {
    return kafka.getBootstrapServers();
  }
}
