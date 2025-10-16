package se.ergot.country;

import org.junit.jupiter.api.Test;
import se.ergot.country.data.CountryFlag;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private static final Locale SV = new Locale("sv");

    private static final Locale EN = Locale.ENGLISH;

    @Test
    void testFind() {
        assertEquals(Country.SE, Country.find("SE"));
        assertEquals(Country.SE, Country.find("se"));
        assertEquals(Country.CS, Country.find("CS"));
        assertEquals(Country.CS, Country.find("YU"));
        assertNull(Country.find("XX"));
        assertThrows(IllegalArgumentException.class, () -> Country.find(null));
    }

    @Test
    void testExistsAtYear() {
        assertTrue(Arrays.stream(Country.values()).noneMatch(c -> c.existsAtYear(Year.of(1899))));

        assertTrue(Country.SE.existsAtYear(Year.of(1926)));
        assertTrue(Country.OE_X.existsAtYear(Year.of(1901)));
        assertFalse(Country.OE_X.existsAtYear(Year.of(1937)));
    }

    @Test
    void testIndependentAtYear() {
        assertTrue(Country.SE.independentAtYear(Year.now()));

        assertFalse(Country.EE.independentAtYear(Year.of(1907)));
        assertTrue(Country.EE.independentAtYear(Year.of(1918)));
        assertTrue(Country.EE.independentAtYear(Year.of(1937)));
        assertFalse(Country.EE.independentAtYear(Year.of(1940)));
        assertFalse(Country.EE.independentAtYear(Year.of(1967)));
        assertTrue(Country.EE.independentAtYear(Year.of(1997)));

        assertFalse(Country.CSHH.independentAtYear(Year.of(1907)));
        assertTrue(Country.CSHH.independentAtYear(Year.of(1937)));
        assertFalse(Country.CSHH.independentAtYear(Year.of(1997)));
    }

    @Test
    void testPartOfAtYear() {
        assertTrue(Country.EE.partOfAtYear(Country.RU, Year.of(1907)));
        assertFalse(Country.EE.partOfAtYear(Country.SU, Year.of(1918)));
        assertTrue(Country.EE.partOfAtYear(Country.SU, Year.of(1940)));
    }

    @Test
    void testBelongsToAtYear() {
        assertTrue(Country.DM.belongsToAtYear(Country.GB, Year.of(1977)));
        assertFalse(Country.DM.belongsToAtYear(Country.GB, Year.of(1978)));
    }

    @Test
    void testSubdivisionOfAtYear() {
        assertTrue(Country.GB_ENG.subdivisionOfAtYear(Country.GB, Year.of(1977)));
        assertFalse(Country.DE.subdivisionOfAtYear(Country.FR, Year.of(1977)));
    }

    @Test
    void testGetNative_returnsNonNull() {
        assertNotNull(Country.getNative());
    }

    @Test
    void testGetFifaCode() {
        assertEquals("SWE", Country.SE.getValue("fifa"));
        assertEquals("ENG", Country.GB_ENG.getValue("fifa"));
        assertNull(Country.SU.getValue("fifa"));
    }

    @Test
    void testFindByFifaCode() {
        assertEquals(Country.SE, Country.findBy("fifa", "SWE"));
        assertNull(Country.findBy("fifa", "XXX"));
    }

    @Test
    void testGetValue_returnsNullForUnknownKey() {
        assertNull(Country.SE.getValue("xxx"));
    }

    @Test
    void testGetName() {
        Locale.setDefault(SV);
        assertEquals("Sverige", Country.SE.getName());
        assertEquals("Sverige", Country.SE.getName(SV));
        assertEquals("Sweden", Country.SE.getName(EN));

        assertEquals("Kongo-Kinshasa", Country.CD.getName());
        assertEquals("Kongo-Kinshasa", Country.CD.getName(SV));
        assertEquals("Democratic Republic of the Congo", Country.CD.getName(EN));

        assertEquals("Belarus", Country.BY.getName());
    }

    @Test
    void testGetNameDefaultsToEnglishForUntranslatedLocale() {
        assertEquals("Sweden", Country.SE.getName(Locale.FRENCH));
        assertEquals("Sweden", Country.SE.getName(Locale.FRENCH, Year.of(2024)));
    }

    @Test
    void testGetNameAt() {
        Locale.setDefault(SV);
        final Year year = Year.of(1997);
        assertEquals("Sverige", Country.SE.getName(year));
        assertEquals("Sverige", Country.SE.getName(SV, year));
        assertEquals("Sweden", Country.SE.getName(EN, year));

        assertEquals("Zaire", Country.CD.getName(year));
        assertEquals("Zaire", Country.CD.getName(SV, year));
        assertEquals("Zaire", Country.CD.getName(EN, year));

        assertEquals("Kongo-Kinshasa", Country.CD.getName(year.plusYears(1)));
        assertEquals("Kongo-Kinshasa", Country.CD.getName(SV, year.plusYears(1)));
        assertEquals("Democratic Republic of the Congo", Country.CD.getName(EN, year.plusYears(1)));

        assertEquals("Belarus", Country.BY.getName());
        assertEquals("Vitryssland", Country.BY.getName(Year.of(2010)));

        assertEquals("Estland", Country.EE.getName(Year.of(1912)));
        assertEquals("Estland", Country.EE.getName(Year.of(1932)));
        assertEquals("Estland", Country.EE.getName(Year.of(1942)));
        assertEquals("Estland", Country.EE.getName(Year.of(1992)));
    }

    @Test
    void testGetFlags() {
        final List<CountryFlag> afList = Country.AF.getAllFlags();
        assertEquals(9, afList.size());
        assertEquals(new HashSet<>(Arrays.asList(1973, 1978, 1980, 1987, 1992, 1996, 2001, 2021)), afList.stream()
                .map(CountryFlag::getEndYear)
                .filter(Objects::nonNull)
                .map(Year::getValue)
                .collect(Collectors.toSet()));
        assertEquals(1, Country.DK.getAllFlags().size());
    }

    @Test
    void testAllFlagFilesExists() {
        final Set<String> errors = new HashSet<>();
        for (Country country : Country.values()) {
            final List<Year> years = country.getAllFlags().stream().map(CountryFlag::getEndYear).collect(Collectors.toList());
            for (Year year : years) {
                final String fileName = country.getIso().toLowerCase() + (year != null ? ("_" + year) : "") + ".svg";
                try {
                    country.getFlagSvgContent(fileName);
                } catch (AssertionError e) {
                    errors.add(fileName);
                }
            }
        }
        assertTrue(errors.isEmpty(), "Flag files missing: " + errors);
    }

    @Test
    void testGetFlagSvgContent_throwsException_whenNotFound() {
        assertThrows(IllegalStateException.class, () -> {
            Country.SE.getFlagSvgContent("xx");
        });
    }

    @Test
    void testGetFlagSvgContent_noException_whenFound() {
        assertDoesNotThrow(() -> Country.SE.getFlagSvgContent("se.svg"));
    }

    @Test
    void testGetFlagSvgContent_throwsException_whenNull() {
        assertThrows(IllegalStateException.class, () -> {
            Country.SE.getFlagSvgContent(null);
        });
    }

    @Test
    void testGetFlagFileName() {
        assertEquals("se.svg", Country.SE.getFlagFileName());
        assertEquals("se.svg", Country.SE.getFlagFileName(Year.of(1997)));
        assertEquals("se_1905.svg", Country.SE.getFlagFileName(Year.of(1886)));

        assertEquals("af.svg", Country.AF.getFlagFileName());
        assertEquals("af_2021.svg", Country.AF.getFlagFileName(Year.of(2018)));
        assertEquals("af_2021.svg", Country.AF.getFlagFileName(Year.of(2021)));
        assertEquals("af.svg", Country.AF.getFlagFileName(Year.of(2022)));
        assertEquals("af_1987.svg", Country.AF.getFlagFileName(Year.of(1986)));
        assertEquals("af_1973.svg", Country.AF.getFlagFileName(Year.of(1886)));
    }

    @Test
    void testGetFlagSvg() {
        assertNotNull(Country.SE.getFlagSvg());
        assertNotNull(Country.SE.getFlagSvg(Year.of(1997)));
        assertNotNull(Country.AF.getFlagSvg(Year.of(2018)));
    }
}
