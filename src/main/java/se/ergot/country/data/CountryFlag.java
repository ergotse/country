package se.ergot.country.data;

import java.time.Year;

public class CountryFlag {

    private final Year endYear;

    private final String fileName;

    private final String svgContent;

    public CountryFlag(Year endYear, String fileName, String svgContent) {
        this.endYear = endYear;
        this.fileName = fileName;
        this.svgContent = svgContent;
    }

    public Year getEndYear() {
        return endYear;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSvgContent() {
        return svgContent;
    }

    @Override
    public String toString() {
        return endYear + ": " + fileName;
    }
}
