package se.ergot.country;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("java:S1192")
public enum Country {
    // Countries that no longer exist
    CS(builder().endYear(Year.of(1993))),
    DD(builder().endYear(Year.of(1990))),
    SU(builder().endYear(Year.of(1991))),
    YU(builder().flag(Year.of(1993)).endYear(Year.of(2006))),

    // All countries
    AD,
    AE,
    AF,
    AG,
    AI,
    AL(builder().flag(Year.of(1992))),
    AM(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    AO,
    AQ,
    AR,
    AS,
    AT,
    AU,
    AW,
    AX,
    AZ(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    BA(builder().from(CountryOrigin.builder().endYear(Year.of(1992)).from(YU).build()).flag(Year.of(1998))),
    BB,
    BD,
    BE,
    BF,
    BG(builder().flag(Year.of(1990))),
    BH,
    BI,
    BJ(builder().flag(Year.of(1990))),
    BL,
    BM,
    BN,
    BO,
    BQ,
    BR,
    BS,
    BT,
    BV,
    BW,
    BY(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build()).flag(Year.of(1995))),
    BZ,
    CA,
    CC,
    CD(builder().flag(Year.of(1997)).flag(Year.of(2006))),
    CF,
    CG,
    CH,
    CI,
    CK,
    CL,
    CM,
    CN,
    CO,
    CR,
    CU,
    CV(builder().flag(Year.of(1992))),
    CW,
    CX,
    CY(builder().flag(Year.of(2006))),
    CZ(builder().from(CountryOrigin.builder().endYear(Year.of(1993)).from(CS).build())),
    DE,
    DJ,
    DK,
    DM,
    DO,
    DZ,
    EC,
    EE(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    EG,
    EH,
    ES,
    ET(builder().flag(Year.of(1996)).flag(Year.of(1997))),
    ER(builder().from(CountryOrigin.builder().endYear(Year.of(1993)).from(ET).build())),
    FI,
    FJ,
    FK,
    FM,
    FO,
    FR,
    GA,
    GB,
    GD,
    GE(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build()).flag(Year.of(2004))),
    GF,
    GG,
    GH,
    GI,
    GL,
    GM,
    GN,
    GP,
    GQ,
    GR,
    GS,
    GT,
    GU,
    GW,
    GY,
    HK,
    HM,
    HN,
    HR(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(YU).build())),
    HT,
    HU,
    ID,
    IE,
    IL,
    IM,
    IN,
    IO,
    IQ(builder().flag(Year.of(2008)).flag(Year.of(2004)).flag(Year.of(1991))),
    IR,
    IS,
    IT,
    JE,
    JM,
    JO,
    JP,
    KE,
    KG(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    KH,
    KI,
    KM(builder().flag(Year.of(2001))),
    KN,
    KP,
    KR,
    KW,
    KY,
    KZ(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    LA,
    LB,
    LC,
    LI,
    LK,
    LR,
    LS(builder().flag(Year.of(1987)).flag(Year.of(2006))),
    LT(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    LU,
    LV(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    LY(builder().flag(Year.of(2011))),
    MA,
    MC,
    MD(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    ME(builder().from(CountryOrigin.builder().endYear(Year.of(2006)).from(YU).build())),
    MF,
    MG,
    MH,
    MK(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(YU).build()).flag(Year.of(1995))),
    ML,
    MM(builder().flag(Year.of(2010))),
    MN,
    MO,
    MP,
    MQ,
    MR(builder().flag(Year.of(2017))),
    MS,
    MT,
    MU,
    MV,
    MW(builder().flag(Year.of(2010)).flag(Year.of(2012))),
    MX,
    MY,
    MZ,
    NA,
    NC,
    NE,
    NF,
    NG,
    NI,
    NL,
    NO,
    NP,
    NR,
    NU,
    NZ,
    OM,
    PA,
    PE,
    PF,
    PG,
    PH,
    PK,
    PL,
    PM,
    PN,
    PR,
    PS(builder().from(CountryOrigin.builder().endYear(Year.of(2014)).from(IL).build())),
    PT,
    PW,
    PY(builder().flag(Year.of(2013))),
    QA,
    RE,
    RO(builder().flag(Year.of(1989))),
    RS(builder().from(CountryOrigin.builder().endYear(Year.of(2006)).from(YU).build()).flag(Year.of(2010))),
    RU(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    RW(builder().flag(Year.of(2001))),
    SA,
    SB,
    SC(builder().flag(Year.of(1996))),
    SD,
    SE,
    SG,
    SH,
    SI(builder().from(CountryOrigin.builder().endYear(Year.of(1992)).from(YU).build())),
    SJ,
    SK(builder().from(CountryOrigin.builder().endYear(Year.of(1993)).from(CS).build())),
    SL,
    SM,
    SN,
    SO,
    SR,
    SS(builder().from(CountryOrigin.builder().endYear(Year.of(2011)).from(SD).build())),
    ST,
    SV,
    SX,
    SY(builder().flag(Year.of(2025))),
    SZ,
    TC,
    TD,
    TF,
    TG,
    TH,
    TJ(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    TK(builder().flag(Year.of(2008))),
    TL(builder().from(CountryOrigin.builder().endYear(Year.of(2002)).from(PT).build())),
    TM(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build()).flag(Year.of(1997)).flag(Year.of(2001))),
    TN,
    TO,
    TR,
    TT,
    TV,
    TW,
    TZ,
    UA(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    UG,
    UM,
    US,
    UY,
    UZ(builder().from(CountryOrigin.builder().endYear(Year.of(1991)).from(SU).build())),
    VA,
    VC,
    VE,
    VG,
    VI,
    VN,
    VU,
    WF,
    WS,
    YE,
    YT,
    ZA(builder().flag(Year.of(1994))),
    ZM,
    ZW,
    // Länder som inte ännu erkänts
    XK(builder().from(CountryOrigin.builder().endYear(Year.of(2008)).from(RS).build())),
    // Delar av UK med egna förbund
    GB_ENG,
    GB_NIR,
    GB_SCT,
    GB_WLS,
    ;

    private static final Properties properties = new Properties();

    static {
        final ClassLoader classLoader = Country.class.getClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream("Country.properties");
        assert inputStream != null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            properties.load(br);
        } catch (IOException e) {
            throw new CountryRuntimeException(e);
        }
    }

    private final Year maxYear = Year.of(Year.MAX_VALUE);

    private final Map<Year, String> flags;

    @Getter
    private final Year endYear;

    @Getter
    private final CountryOrigin countryOrigin;

    Country() {
        this.flags = Map.of(maxYear, name().toLowerCase() + ".svg");
        this.endYear = null;
        this.countryOrigin = null;
    }

    Country(Builder builder) {
        this.flags = builder.flags.stream()
                .collect(Collectors.toMap(year -> year, year -> name().toLowerCase() + (year.equals(maxYear) ? "" : ("_" + year)) + ".svg"));
        this.endYear = builder.endYear;
        this.countryOrigin = builder.countryOrigin;
    }

    public static Country[] values(Year atYear) {
        return Arrays.stream(values())
                .filter(c -> c.getEndYear() == null || !c.getEndYear().isBefore(atYear))
                .filter(c -> c.getCountryOrigin() == null || c.countryOrigin.getEndYear().isBefore(atYear))
                .toArray(Country[]::new);
    }

    /**
     * Finds a country based on its ISO code.
     *
     * <p>This method attempts to find a country from the ISO code provided. If the
     * input is null or the ISO code does not match any country, it returns {@code null}.</p>
     *
     * @param iso the ISO code of the country
     * @return the {@link Country} object corresponding to the ISO code, or {@code null} if not found
     */
    public static Country find(String iso) {
        try {
            return Country.valueOf(iso.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves the Country that is configured as native in Country.properties (country.native=XX where XX is country's iso code).
     * If property not found, this method returns the country of the default locale for this instance of the Java Virtual Machine.
     *
     * @return the {@link Country} that is configured as native, or default Locale's country
     */
    public static Country getNative() {
        try {
            return Country.valueOf(properties.getProperty("country.native", Locale.getDefault().getCountry()));
        } catch (Exception e) {
            return null;
        }
    }

    public static Country findBy(String key, String value) {
        for (Map.Entry<Object, Object> property : properties.entrySet()) {
            final String propertyKey = (String) property.getKey();
            final List<String> keyParts = Arrays.stream(propertyKey.split("\\.")).toList();
            if (keyParts.size() > 1) {
                final String keyPartIso = keyParts.get(0);
                final String keyPartKey = String.join(".", keyParts.subList(1, keyParts.size()));
                if (keyPartKey.equals(key)) {
                    final String propertyValue = (String) property.getValue();
                    if (propertyValue.equals(value)) {
                        return Country.valueOf(keyPartIso);
                    }
                }
            }
        }
        return null;
    }

    private static Builder builder() {
        return new Builder();
    }

    private static ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle("CountryNames", locale, new ResourceBundle.Control() {
            @Override
            public List<Locale> getCandidateLocales(String baseName, Locale locale) {
                final List<Locale> list = super.getCandidateLocales(baseName, locale).stream()
                        .filter(l -> !l.getLanguage().isEmpty())
                        .collect(Collectors.toList());
                list.add(Locale.ENGLISH);
                return list;
            }
        });
    }

    /**
     * Retrieves the name of the country in the default locale's language.
     *
     * <p>This method uses the system's default {@link java.util.Locale} to fetch the name
     * of the country. If you need the name in a specific locale, use {@link #getName(Locale)}.</p>
     *
     * @return the localized name of the country
     */
    public String getName() {
        return getName(Locale.getDefault());
    }

    /**
     * Retrieves the name of the country in the specified locale's language.
     *
     * <p>This method fetches the localized name of the country based on the given
     * {@link java.util.Locale}. The name is retrieved from a resource bundle associated with the locale.</p>
     *
     * @param locale the locale for which the country's name should be localized
     * @return the localized name of the country
     * @throws MissingResourceException if the name for the given locale is not found
     */
    public String getName(Locale locale) {
        final var resourceBundle = getResourceBundle(locale);
        return resourceBundle.getString(name() + ".name");
    }

    /**
     * Retrieves the name of the country as it was in a specific year.
     *
     * <p>This method fetches the localized name of the country for the default locale
     * as it existed in the given {@link java.time.Year}. If the country has a
     * parent origin and was not independent during the specified year, the name
     * of the parent country is returned.</p>
     *
     * @param atYear the year for which the country's name is requested
     * @return the name of the country in the default locale, or the name of its parent country
     *         if it was not independent at the specified year
     */
    public String getName(Year atYear) {
        return getName(Locale.getDefault(), atYear);
    }

    /**
     * Retrieves the localized name of the country as it was in a specific year.
     *
     * <p>This method returns the localized name of the country for the given
     * {@link java.util.Locale} and {@link java.time.Year}. If the country has a
     * parent origin and was not independent during the specified year, the name
     * of the parent country is returned. If no localized name is available for
     * the specified year, {@code null} is returned.</p>
     *
     * @param locale the locale in which the country's name should be localized
     * @param atYear the year for which the country's name is requested
     * @return the localized name of the country, or the name of its parent country
     *         if it was not independent at the specified year; {@code null} if no name is found
     */
    public String getName(Locale locale, Year atYear) {
        if (countryOrigin != null && !countryOrigin.getEndYear().isBefore(atYear)) {
            return countryOrigin.getFrom().getName(locale, atYear);
        }
        final Map<Year, String> map = getAllNames(locale);
        return map.entrySet().stream()
                .filter(e -> !e.getKey().isBefore(atYear))
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    public Map<Year, String> getPreviousNames() {
        return getPreviousNames(Locale.getDefault());
    }

    public Map<Year, String> getPreviousNames(Locale locale) {
        return getAllNames(locale).entrySet().stream()
                .filter(e -> !e.getKey().equals(Year.now()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Year, String> getAllNames(Locale locale) {
        final ResourceBundle resourceBundle = getResourceBundle(locale);
        return resourceBundle.keySet().stream()
                .filter(k -> k.startsWith(name() + ".name"))
                .collect(Collectors.toMap(k -> {
                    final var parts = Arrays.stream(k.split("\\.")).toList();
                    final String lastItem = parts.get(parts.size() - 1);
                    return lastItem.equals("name") ? Year.now() : Year.of(Integer.parseInt(lastItem));
                }, resourceBundle::getString));
    }

    /**
     * Retrieves the SVG content for the country's current flag.
     *
     * <p>This method fetches the default flag of the country in SVG format from the resources.
     * If the flag file is not found, a placeholder SVG is returned.</p>
     *
     * @return the SVG content of the country's flag
     */
    public String getFlagSvg() {
        return getFlagSvgContent(getFlagFileName());
    }

    /**
     * Retrieves the SVG content for the country's flag at a specific year.
     *
     * <p>If the country has changed its flag over time, this method returns the flag
     * used in the specified {@link java.time.Year}.</p>
     *
     * @param atYear the year for which the flag is requested
     * @return the SVG content of the country's flag at the specified year
     */
    public String getFlagSvg(Year atYear) {
        return getFlagSvgContent(getFlagFileName(atYear));
    }

    public Map<Year, String> getPreviousFlags() {
        return flags.entrySet().stream()
                .filter(e -> !e.getKey().equals(maxYear))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> getFlagSvgContent(e.getValue())));
    }

    String getFlagFileName() {
        return flags.get(maxYear);
    }

    String getFlagFileName(Year atYear) {
        if (countryOrigin != null && !countryOrigin.getEndYear().isBefore(atYear)) {
            return countryOrigin.getFrom().getFlagFileName(atYear);
        }
        final Year nextYear = flags.keySet().stream().filter(y -> !y.isBefore(atYear)).min(Comparator.naturalOrder()).orElse(maxYear);
        return flags.get(nextYear);
    }

    private String getFlagSvgContent(String name) {
        try {
            final InputStream is = getClass().getResourceAsStream("flags/" + name);
            assert is != null;
            final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return String.join("", reader.lines().toList());
        } catch (Exception e) {
            return "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"10\" height=\"10\"></svg>";
        }
    }

    /**
     * Determines if the country existed in a given year.
     *
     * <p>This method checks whether the country was established and not dissolved
     * in the specified {@link java.time.Year}. If the country has a parent country
     * of origin, it checks that as well.</p>
     *
     * @param atYear the year to check
     * @return {@code true} if the country existed in the given year, otherwise {@code false}
     */
    public boolean existsAtYear(Year atYear) {
        final var isStarted = countryOrigin == null || countryOrigin.getEndYear().isBefore(atYear);
        final var isNotEnded = endYear == null || !endYear.isBefore(atYear);

        return isStarted && isNotEnded;
    }

    /**
     * Retrieves a property value associated with the country.
     *
     * <p>This method looks up a property in the `Country.properties` file.</p>
     *
     * @param name the property name to look up (e.g., "population", "capital")
     * @return the property value as a string, or {@code null} if the property does not exist
     */
    public String getValue(String name) {
        final String key = name() + "." + name;
        return properties.getProperty(key);
    }

    private static class Builder {

        private final Set<Year> flags = new HashSet<>(Set.of(Year.of(Year.MAX_VALUE)));

        private Year endYear;

        private CountryOrigin countryOrigin;

        private Builder flag(Year yearChange) {
            this.flags.add(yearChange);
            return this;
        }

        private Builder endYear(Year endYear) {
            this.endYear = endYear;
            return this;
        }

        public Builder from(CountryOrigin countryOrigin) {
            this.countryOrigin = countryOrigin;
            return this;
        }
    }

    public static class CountryRuntimeException extends RuntimeException {

        public CountryRuntimeException(Exception e) {
            super(e);
        }
    }
}
