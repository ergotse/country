package se.ergot.country;

import lombok.Builder;
import lombok.Getter;

import java.time.Year;

/**
 * Represents the origin of a country, including its predecessor and dissolution year.
 *
 * <p>The {@code CountryOrigin} class provides details about a country's history,
 * such as its predecessor country and the year it ceased to exist (if applicable).</p>
 */
@Builder
@Getter
public class CountryOrigin {

    private final Year endYear;

    private final Country from;

}
