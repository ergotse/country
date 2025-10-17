package se.ergot.country.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Year;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public class CountryInterval implements Comparable<CountryInterval> {

    private final Year end;

    private final String partOf;

    private final String belongsTo;

    private final String subdivisionOf;

    private Year start;

    @JsonIgnore
    private Boolean openStart;

    @JsonCreator
    public CountryInterval(
            @JsonProperty("start") Year start,
            @JsonProperty("openStart") Boolean openStart,
            @JsonProperty("end") Year end,
            @JsonProperty("partOf") String partOf,
            @JsonProperty("belongsTo") String belongsTo,
            @JsonProperty("subdivisionOf") String subdivisionOf) {
        validateType(partOf, belongsTo, subdivisionOf);
        this.start = start;
        this.openStart = openStart;
        this.end = end;
        this.partOf = partOf;
        this.belongsTo = belongsTo;
        this.subdivisionOf = subdivisionOf;
    }

    public CountryInterval() {
        this(null, null, null, null, null, null);
    }

    private void validateType(String partOf, String belongsTo, String subdivisionOf) {
        final boolean valid = Stream.of(partOf, belongsTo, subdivisionOf)
                .filter(Objects::nonNull)
                .count() <= 1;
        if (!valid) {
            throw new IllegalArgumentException("Only one of partOf, belongsTo or subdivisionOf can be set");
        }
    }

    public Year getStart() {
        return start;
    }

    public void setStart(Year start) {
        this.start = start;
    }

    public Boolean getOpenStart() {
        return openStart;
    }

    public void setOpenStart(Boolean openStart) {
        this.openStart = openStart;
    }

    public Year getEnd() {
        return end;
    }

    public String getPartOf() {
        return partOf;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public String getSubdivisionOf() {
        return subdivisionOf;
    }

    @Override
    public int compareTo(CountryInterval o) {
        return Comparator
                .comparing(CountryInterval::getStart, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(CountryInterval::getEnd, Comparator.nullsLast(Comparator.naturalOrder()))
                .compare(this, o);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append((start != null && !Boolean.TRUE.equals(openStart)) ? start : "...").append("-").append(end != null ? end : "");
        if (partOf != null) {
            sb.append(" (partOf: ").append(partOf).append(")");
        } else if (belongsTo != null) {
            sb.append(" (belongsTo: ").append(belongsTo).append(")");
        } else if (subdivisionOf != null) {
            sb.append(" (subdivisionOf: ").append(subdivisionOf).append(")");
        } else {
            sb.append(" (independent)");
        }
        return sb.toString();
    }
}
