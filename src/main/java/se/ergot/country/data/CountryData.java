package se.ergot.country.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Value
public class CountryData {

    List<CountryInterval> intervals;

    List<PrevCode> prevCodes;

    List<Year> flagChange;

    @JsonCreator
    public CountryData(@JsonProperty("intervals") List<CountryInterval> intervals,
                       @JsonProperty("prevCodes") List<PrevCode> prevCodes,
                       @JsonProperty("flagChange") List<Year> flagChange
    ) {
        this.intervals = validateAndCreateIntervals(intervals);
        this.prevCodes = prevCodes != null ? prevCodes : new ArrayList<>();
        this.flagChange = flagChange != null ? flagChange : new ArrayList<>();
    }

    public CountryData() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private static List<CountryInterval> validateAndCreateIntervals(List<CountryInterval> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return Collections.singletonList(new CountryInterval(Year.of(1900), true, null, null, null, null));
        }
        ensureIntervalStart(intervals);
        validateIntervals(intervals);
        return intervals;
    }

    private static void validateIntervals(List<CountryInterval> sortedList) {
        for (int i = 0; i < sortedList.size(); i++) {
            if (i != sortedList.size() - 1 && sortedList.get(i).getEnd() == null) {
                throw new IllegalArgumentException("End of interval " + i + " is missing");
            }
            if (sortedList.get(i).getEnd() != null && !sortedList.get(i).getStart().isBefore(sortedList.get(i).getEnd())) {
                throw new IllegalArgumentException("Start of interval " + i + " is after end of interval");
            }
            if (i > 0 && !sortedList.get(i).getStart().equals(sortedList.get(i - 1).getEnd())) {
                throw new IllegalArgumentException("Start of interval " + i + " does not match end of previous interval");
            }
        }
    }

    private static void ensureIntervalStart(List<CountryInterval> sortedList) {
        for (int i = 0; i < sortedList.size(); i++) {
            final CountryInterval interval = sortedList.get(i);
            if (i == 0 && interval.getStart() == null) {
                interval.setStart(Year.of(1900));
                interval.setOpenStart(true);
            } else if (i > 0 && interval.getStart() == null) {
                interval.setStart(sortedList.get(i - 1).getEnd());
            }
        }
    }

    public List<CountryInterval> getIntervals() {
        return intervals;
    }

    public List<PrevCode> getPrevCodes() {
        return prevCodes;
    }

    public List<Year> getFlagChange() {
        return flagChange;
    }
}
