package ankel.rome.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Binh Tran
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event
{
  public static final String EVENT_TYPE_1 = "event-type-1";
  public static final String EVENT_TYPE_2 = "event-type-2";
  public static final String EVENT_TYPE_3 = "event-type-3";

  private String eventType;
  private String payload;
}
