package se.ergot.country.data;

import java.time.Year;

public class CountryFlag {

    private final Year endYear;

    private final String fileName;

    public CountryFlag(Year endYear, String fileName) {
        this.endYear = endYear;
        this.fileName = fileName;
    }

    public Year getEndYear() {
        return endYear;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return endYear + ": " + fileName;
    }
}
