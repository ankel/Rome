package ankel.rome;

import lombok.extern.slf4j.Slf4j;

import ankel.rome.api.Event;
import ankel.rome.kafka.ConsoleConsumer;
import ankel.rome.kafka.Runner;
import ankel.rome.kafka.Producer;

/**
 * @author Binh Tran
 */
@Slf4j
public class Main
{
  public static void main(final String[] args) throws Exception
  {
    final Runner runner = new Runner();
    runner.start();

    Producer producer = new Producer(runner.getBootstrapAddress());
    ConsoleConsumer consoleConsumer = new ConsoleConsumer(runner.getBootstrapAddress());

    for (int i = 0; i < 1000; ++i)
    {
      producer.produce(i, new Event(Event.EVENT_TYPE_1, String.format("Value: %d", i)));
    }

    log.warn("Starting consumer...");

    consoleConsumer.start();

    Thread.sleep(1000);

    consoleConsumer.stop();
    producer.stop();
    runner.stop();
  }
}
