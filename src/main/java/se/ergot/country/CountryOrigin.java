package se.ergot.country;

import lombok.Builder;
import lombok.Getter;

import java.time.Year;

@Builder
@Getter
public class CountryOrigin {

    private final Year endYear;

    private final Country from;

}
