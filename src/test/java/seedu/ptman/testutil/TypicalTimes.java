package seedu.ptman.testutil;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * A utility class containing a list of LocalDateTime objects to be used in tests.
 */
public class TypicalTimes {

    public static final LocalDateTime TEN_MAR_TWELVE_AM =
            LocalDateTime.of(2018, Month.MARCH, 10, 0, 0);
    public static final LocalDateTime TEN_MAR_EIGHT_AM =
            LocalDateTime.of(2018, Month.MARCH, 10, 8, 0);
    public static final LocalDateTime TEN_MAR_ONE_PM =
            LocalDateTime.of(2018, Month.MARCH, 10, 13, 0);
    public static final LocalDateTime TEN_MAR_ELEVEN_PM =
            LocalDateTime.of(2018, Month.MARCH, 11, 23, 0);
    public static final LocalDateTime ELEVEN_MAR_TWELVE_AM =
            LocalDateTime.of(2018, Month.MARCH, 11, 0, 0);
    public static final LocalDateTime TWELVE_MAR_ELEVEN_PM =
            LocalDateTime.of(2018, Month.MARCH, 12, 23, 0);
    public static final LocalDateTime TWELVE_APR_ELEVEN_PM =
            LocalDateTime.of(2018, Month.APRIL, 12, 23, 0);

}
