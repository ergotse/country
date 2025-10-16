package se.ergot.country.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Year;

public class PrevCode {

    String iso;

    Year start;

    Year end;

    @JsonCreator
    public PrevCode(@JsonProperty("iso") String iso,
                    @JsonProperty("start") Year start,
                    @JsonProperty("end") Year end) {
        this.iso = iso;
        this.start = start;
        this.end = end;
    }

    public String getIso() {
        return iso;
    }

    public Year getStart() {
        return start;
    }

    public Year getEnd() {
        return end;
    }
}
