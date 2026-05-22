package se.ergot.country;

import org.junit.jupiter.api.Test;
import se.ergot.country.data.BelongsToVariant;
import se.ergot.country.data.CountryFlag;
import se.ergot.country.data.CountryInterval;

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
        assertNull(Country.find(null));
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
        assertEquals(Collections.singletonList(Country.GB), Country.DM.getBelongsToAtYear(Year.of(1977)));
        assertEquals(Collections.emptyList(), Country.DM.getBelongsToAtYear(Year.of(1978)));
    }

    @Test
    void testBelongsToAtYear_condominium() {
        // Vanuatu (VU) was a condominium jointly administered by GB and FR until 1980
        assertTrue(Country.VU.belongsToAtYear(Country.GB, Year.of(1975)));
        assertTrue(Country.VU.belongsToAtYear(Country.FR, Year.of(1975)));
        assertFalse(Country.VU.belongsToAtYear(Country.GB, Year.of(1981)));
        final List<Country> rulers = Country.VU.getBelongsToAtYear(Year.of(1975));
        assertEquals(2, rulers.size());
        assertTrue(rulers.contains(Country.GB));
        assertTrue(rulers.contains(Country.FR));
    }

    @Test
    void testGetBelongsToVariantAtYear() {
        assertEquals(BelongsToVariant.PERSONAL_UNION, Country.NO.getBelongsToVariantAtYear(Year.of(1900)));
        assertEquals(BelongsToVariant.CONDOMINIUM, Country.VU.getBelongsToVariantAtYear(Year.of(1975)));
        assertEquals(BelongsToVariant.ASSOCIATED_STATE, Country.CK.getBelongsToVariantAtYear(Year.now()));
        assertEquals(BelongsToVariant.COLONY, Country.DM.getBelongsToVariantAtYear(Year.of(1977)));
        assertNull(Country.SE.getBelongsToVariantAtYear(Year.now()));  // independent
        assertNull(Country.NO.getBelongsToVariantAtYear(Year.now()));  // independent after 1905
        assertNull(Country.AX.getBelongsToVariantAtYear(Year.now()));  // belongsTo but no variant assigned
    }

    @Test
    void testExistsAtYear_belongsTo_returnsTrue() {
        // belongsTo countries still "exist" as territories; only partOf countries are considered non-existent
        assertTrue(Country.DM.existsAtYear(Year.of(1977)));
        assertFalse(Country.EE.existsAtYear(Year.of(1967))); // partOf SU
    }

    @Test
    void testIndependentAtYear_subdivisionOf_returnsFalse() {
        assertFalse(Country.GB_ENG.independentAtYear(Year.of(1977)));
        assertFalse(Country.GB_ENG.independentAtYear(Year.now()));
    }

    @Test
    void testCountryInterval_validationRejectsConflictingTypes() {
        assertThrows(IllegalArgumentException.class, () ->
                new CountryInterval(null, null, null, "RU", Arrays.asList("GB"), null, null));
        assertThrows(IllegalArgumentException.class, () ->
                new CountryInterval(null, null, null, null, Arrays.asList("GB"), null, "GB"));
        assertThrows(IllegalArgumentException.class, () ->
                new CountryInterval(null, null, null, "RU", null, null, "GB"));
    }

    @Test
    void testCountryInterval_validationRejectsVariantWithoutBelongsTo() {
        assertThrows(IllegalArgumentException.class, () ->
                new CountryInterval(null, null, null, null, null, BelongsToVariant.COLONY, null));
        assertThrows(IllegalArgumentException.class, () ->
                new CountryInterval(null, null, null, "RU", null, BelongsToVariant.COLONY, null));
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

    @Test
    void testGetCurrentInterval_nullYear_usesCurrentYear() {
        // SE is currently independent, so null atYear (= Year.now()) should resolve to a non-null interval
        assertNotNull(Country.SE.getCurrentInterval(null));
    }

    @Test
    void testGetCurrentInterval_before1900_returnsNull() {
        assertNull(Country.SE.getCurrentInterval(Year.of(1899)));
        assertNull(Country.SE.getCurrentInterval(Year.of(1800)));
    }

    @Test
    void testGetCurrentInterval_exactly1900_notTreatedAsBefore() {
        // 1900 is the cutoff boundary — isBefore(1900) is false, so interval lookup proceeds
        assertNotNull(Country.SE.getCurrentInterval(Year.of(1900)));
    }

    @Test
    void testGetCurrentInterval_independentInterval() {
        // EE was independent in 1918; interval should have no partOf
        final CountryInterval interval = Country.EE.getCurrentInterval(Year.of(1918));
        assertNotNull(interval);
        assertNull(interval.getPartOf());
    }

    @Test
    void testGetCurrentInterval_partOfInterval() {
        // EE was part of SU in 1967; interval should have partOf set
        final CountryInterval interval = Country.EE.getCurrentInterval(Year.of(1967));
        assertNotNull(interval);
        assertNotNull(interval.getPartOf());
    }

    @Test
    void testGetCurrentInterval_afterAllIntervalsEnded_returnsNull() {
        // OE_X (Austria-Hungary) ceased to exist; no interval covers 1937
        assertNull(Country.OE_X.getCurrentInterval(Year.of(1937)));
    }

    @Test
    void testGetCurrentInterval_openEndedInterval_matchesFutureYear() {
        // SE has an open-ended interval (null end); it should match any year >= its start
        final CountryInterval interval = Country.SE.getCurrentInterval(Year.of(2050));
        assertNotNull(interval);
        assertNull(interval.getEnd());
    }

    @Test
    void testGetCurrentInterval_endIsExclusive() {
        // OE_X existed at 1901 but not at 1937 — confirm the end year is exclusive
        assertNotNull(Country.OE_X.getCurrentInterval(Year.of(1901)));
        assertNull(Country.OE_X.getCurrentInterval(Year.of(1937)));
    }

    @Test
    void testGetCurrentInterval_partOfAtYear() {
        final CountryInterval partOfInterval = Country.EE.getCurrentInterval(Year.of(1907));
        assertNotNull(partOfInterval);
        assertNotNull(partOfInterval.getPartOf());

        final CountryInterval independentInterval = Country.EE.getCurrentInterval(Year.of(1925));
        assertNotNull(independentInterval);
        assertNull(independentInterval.getPartOf());
    }

    @Test
    void testGetCurrentInterval_sortedByStart_returnsEarliestMatch() {
        // There should be at most one matching interval per year; verify no ambiguity for SE
        assertNotNull(Country.SE.getCurrentInterval(Year.of(1950)));
    }

    @Test
    void testPartOfBelongsToSubdivisionOfExists() {
        for (Country country : Country.values()) {
            for (CountryInterval interval : country.getIntervals()) {
                if (interval.getPartOf() != null) {
                    assertNotNull(Country.find(interval.getPartOf()), "Country " + country.getIso() + " is part of " + interval.getPartOf() + " (not found)");
                }
                if (interval.getBelongsTo() != null) {
                    for (String code : interval.getBelongsTo()) {
                        assertNotNull(Country.find(code), "Country " + country.getIso() + " belongsTo " + code + " (not found)");
                    }
                }
                if (interval.getSubdivisionOf() != null) {
                    assertNotNull(Country.find(interval.getSubdivisionOf()), "Country " + country.getIso() + " is subdivision of " + interval.getSubdivisionOf() + " (not found)");
                }
            }
        }
    }
}
