package se.ergot.country;

import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private static final Locale SV = new Locale("sv");

    private static final Locale EN = Locale.ENGLISH;

    @Test
    void testGetCurrentName() {
        Locale.setDefault(SV);
        assertEquals("Sverige", Country.SE.getName());
        assertEquals("Sverige", Country.SE.getName(SV));
        assertEquals("Sweden", Country.SE.getName(EN));

        assertEquals("Kongo-Kinshasa", Country.CD.getName());
        assertEquals("Kongo-Kinshasa", Country.CD.getName(SV));
        assertEquals("Democratic Republic of the Congo", Country.CD.getName(EN));
    }

    @Test
    void testGetNameDefaultsToEnglishForUntranslatedLocale() {
        assertEquals("Sweden", Country.SE.getName(Locale.FRENCH));
        assertEquals("Sweden", Country.SE.getName(Locale.FRENCH, Year.of(2024)));
    }

    @Test
    void testGetNameAt() {
        final var year = Year.of(1997);
        assertEquals("Sverige", Country.SE.getName(year));
        assertEquals("Sverige", Country.SE.getName(SV, year));
        assertEquals("Sweden", Country.SE.getName(EN, year));

        assertEquals("Zaire", Country.CD.getName(year));
        assertEquals("Zaire", Country.CD.getName(SV, year));
        assertEquals("Zaire", Country.CD.getName(EN, year));

        assertEquals("Kongo-Kinshasa", Country.CD.getName(year.plusYears(1)));
        assertEquals("Kongo-Kinshasa", Country.CD.getName(SV, year.plusYears(1)));
        assertEquals("Democratic Republic of the Congo", Country.CD.getName(EN, year.plusYears(1)));
    }

    @Test
    void testGetNameAtWhenCountryHasOrigin() {
        assertEquals("Azerbajdzjan", Country.AZ.getName());
        assertEquals("Azerbajdzjan", Country.AZ.getName(Year.of(1992)));
        assertEquals("Sovjetunionen", Country.AZ.getName(Year.of(1991)));
    }

    @Test
    void testGetCurrentFlagName() {
        assertEquals("se.svg", Country.SE.getFlagFileName());
        assertEquals("cd.svg", Country.CD.getFlagFileName());
    }

    @Test
    void testGetFlagNameAt() {
        final var year = Year.of(1997);
        assertEquals("se.svg", Country.SE.getFlagFileName(year));
        assertEquals("cd_1997.svg", Country.CD.getFlagFileName(year));
        assertEquals("cd_2006.svg", Country.CD.getFlagFileName(year.plusYears(1)));
    }

    @Test
    void testGetCurrentFlag() {
        assertNotNull(Country.SE.getFlagSvg());
        assertNotNull(Country.CD.getFlagSvg());
    }

    @Test
    void testGetFlagAt() {
        final var year = Year.of(1997);
        assertNotNull(Country.SE.getFlagSvg(year));
        assertNotNull(Country.CD.getFlagSvg(year));
        assertNotNull(Country.CD.getFlagSvg(year.plusYears(1)));
    }

    @Test
    void testGetFlagNameWhenCountryHasOrigin() {
        assertEquals("az.svg", Country.AZ.getFlagFileName());
        assertEquals("az.svg", Country.AZ.getFlagFileName(Year.of(1992)));
        assertEquals("su.svg", Country.AZ.getFlagFileName(Year.of(1991)));
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
        assertNull(Country.findBy("fifa", "QQQ"));
    }

    @Test
    void testThatNativeExists() {
        assertNotNull(Country.getNative());
    }

    @Test
    void testValuesAt() {
        assertTrue(Arrays.stream(Country.values(Year.of(1993))).collect(Collectors.toSet()).contains(Country.CS));
        assertFalse(Arrays.stream(Country.values(Year.of(1994))).collect(Collectors.toSet()).contains(Country.CS));

        assertTrue(Arrays.stream(Country.values(Year.of(1992))).collect(Collectors.toSet()).contains(Country.AZ));
        assertFalse(Arrays.stream(Country.values(Year.of(1991))).collect(Collectors.toSet()).contains(Country.AZ));

        assertFalse(Arrays.stream(Country.values(Year.of(1992))).collect(Collectors.toSet()).contains(Country.SU));
        assertTrue(Arrays.stream(Country.values(Year.of(1991))).collect(Collectors.toSet()).contains(Country.SU));
    }

    @Test
    void testExistsAtYear() {
        assertFalse(Country.EE.existsAtYear(Year.of(1991)));
        assertTrue(Country.EE.existsAtYear(Year.of(1992)));

        assertTrue(Country.CS.existsAtYear(Year.of(1993)));
        assertFalse(Country.CS.existsAtYear(Year.of(1994)));
    }
}