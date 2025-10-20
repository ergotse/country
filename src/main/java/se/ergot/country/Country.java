package se.ergot.country;

import se.ergot.country.data.CountryData;
import se.ergot.country.data.CountryFlag;
import se.ergot.country.data.CountryInterval;
import se.ergot.country.data.PrevCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public enum Country {

    // Countries with ISO 3166-3
    CSHH, // Using this code for Czechoslovakia to keep uniqueness, CS clashes with Serbia & Montenegro
    DD,
    SU,
    CS,
    AN,
    VD,
    YD,

    // Countries with ISO 3166-1 alpha 2
    AD,
    AE,
    AF,
    AG,
    AL,
    AM,
    AO,
    AR,
    AT,
    AU,
    AZ,
    BA,
    BB,
    BD,
    BE,
    BF,
    BG,
    BH,
    BI,
    BJ,
    BM,
    BN,
    BO,
    BR,
    BS,
    BT,
    BW,
    BY,
    BZ,
    CA,
    CD,
    CF,
    CG,
    CH,
    CI,
    CL,
    CM,
    CN,
    CO,
    CR,
    CU,
    CV,
    CY,
    CZ,
    DE,
    DJ,
    DK,
    DM,
    DO,
    DZ,
    EC,
    EE,
    EG,
    EH,
    ES,
    ET,
    ER,
    FI,
    FJ,
    FM,
    FR,
    GA,
    GB,
    GD,
    GE,
    GH,
    GM,
    GN,
    GP,
    GQ,
    GR,
    GT,
    GW,
    GY,
    HN,
    HR,
    HT,
    HU,
    ID,
    IE,
    IL,
    IN,
    IQ,
    IR,
    IS,
    IT,
    JM,
    JO,
    JP,
    KE,
    KG,
    KH,
    KI,
    KM,
    KN,
    KP,
    KR,
    KW,
    KZ,
    LA,
    LB,
    LC,
    LI,
    LK,
    LR,
    LS,
    LT,
    LU,
    LV,
    LY,
    MA,
    MC,
    MD,
    ME,
    MG,
    MH,
    MK,
    ML,
    MM,
    MN,
    MR,
    MT,
    MU,
    MV,
    MW,
    MX,
    MY,
    MZ,
    NA,
    NE,
    NG,
    NI,
    NL,
    NO,
    NP,
    NR,
    NZ,
    OM,
    PA,
    PE,
    PG,
    PH,
    PK,
    PL,
    PS, // Palestine not fully accepted yet, but is recognized by the majority of the worlds nations (157 last I checked)
    PT,
    PW,
    PY,
    QA,
    RO,
    RS,
    RU,
    RW,
    SA,
    SB,
    SC,
    SD,
    SE,
    SG,
    SI,
    SK,
    SL,
    SM,
    SN,
    SO,
    SR,
    SS,
    ST,
    SV,
    SY,
    SZ,
    TD,
    TG,
    TH,
    TJ,
    TL,
    TM,
    TN,
    TO,
    TR,
    TT,
    TV,
    TW,
    TZ,
    UA,
    UG,
    US,
    UY,
    UZ,
    VA,
    VC,
    VE,
    VN,
    VU,
    WS,
    YE,
    ZA,
    ZM,
    ZW,

    // Countries with ISO 3166-1 alpha 2, but not independent
    AQ,
    XK,

    // British Overseas Territories
    AI,
    IO,
    VG,
    KY,
    FK,
    GI,
    MS,
    PN,
    SH,
    GS,
    TC,
    GG,
    IM,
    JE,

    // US outlying areas
    AS,
    GU,
    MP,
    PR,
    UM,
    VI,

    // French dependencies
    BL,
    GF,
    MF,
    MQ,
    NC,
    PF,
    PM,
    RE,
    TF,
    WF,
    YT,

    // Belonging to Netherlands
    AW,
    CW,
    SX,
    BQ,

    // Belonging to Australia
    CC,
    CX,
    HM,
    NF,

    // Belonging to China
    HK,
    MO,

    // Belonging to Denmark
    FO,
    GL,

    // Belonging to New Zealand
    TK,
    NU,
    CK,

    // Belonging to Norway
    SJ,
    BV,

    // Belonging to Finland
    AX,

    // Countries of UK
    GB_ENG,
    GB_NIR,
    GB_SCT,
    GB_WLS,

    // Countries with no ISO 3166-code (existed before 1976), made up codes by me
    OE_X, // Ottoman Empire
    TZ_Z, // Zanzibar
    KR_X, // Korea (-1910)
    ;

    private final String iso = name();

    private final List<CountryFlag> flags = new ArrayList<>();

    private final CountryData countryData;

    Country() {
        this.countryData = CountryPropertyLoader.getCountryData(name());
    }

    public static Country[] values(Year atYear) {
        return Arrays.stream(values()).filter(c -> c.existsAtYear(atYear)).toArray(Country[]::new);
    }

    public static Country find(String iso) {
        if (iso != null) {
            for (Country country : values()) {
                if (country.getIso().equalsIgnoreCase(iso) ||
                        country.getPrevCodes().stream()
                                .map(pc -> pc.getIso().toLowerCase())
                                .collect(Collectors.toSet())
                                .contains(iso.toLowerCase())) {
                    return country;
                }
            }
        }
        return null;
    }

    public static Country getNative() {
        return Country.valueOf(CountryPropertyLoader.getProperties("").getProperty("country.native", Locale.getDefault().getCountry()));
    }

    public static Country findBy(String key, String value) {
        final Properties properties = CountryPropertyLoader.getProperties(key);
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (entry.getValue().equals(value)) {
                return Country.valueOf((String) entry.getKey());
            }
        }
        return null;
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

    private static Properties getProperties(String key) {
        return CountryPropertyLoader.getProperties(key);
    }

    public String getIso() {
        return iso;
    }

    List<PrevCode> getPrevCodes() {
        return countryData.getPrevCodes();
    }

    List<CountryInterval> getIntervals() {
        final List<CountryInterval> intervals = countryData.getIntervals();
        if (intervals.isEmpty()) {
            return Collections.singletonList(new CountryInterval());
        }
        return intervals;
    }

    public String getName() {
        return getName(Locale.getDefault());
    }

    public String getName(Locale locale) {
        return getName(locale, Year.now());
    }

    public String getName(Year atYear) {
        return getName(Locale.getDefault(), atYear);
    }

    public String getName(Locale locale, Year atYear) {
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
                    final List<String> parts = Arrays.stream(k.split("\\.")).collect(Collectors.toList());
                    final String lastItem = parts.get(parts.size() - 1);
                    return lastItem.equals("name") ? Year.now() : Year.of(Integer.parseInt(lastItem));
                }, resourceBundle::getString));
    }

    public String getFlagSvg() {
        return getFlagSvg(null);
    }

    public String getFlagSvg(Year atYear) {
        return getFlagSvgContent(getFlagFileName(atYear));
    }

    public List<CountryFlag> getAllFlags() {
        if (flags.isEmpty()) {
            flags.addAll(countryData.getFlagChange().stream()
                    .map(year -> {
                        final String fileName = iso.toLowerCase() + "_" + year + ".svg";
                        return new CountryFlag(year, fileName, getFlagSvgContent(fileName));
                    })
                    .sorted(Comparator.comparing(CountryFlag::getEndYear))
                    .collect(Collectors.toList()));
            final String currentFileName = iso.toLowerCase() + ".svg";
            flags.add(new CountryFlag(null, currentFileName, getFlagSvgContent(currentFileName)));
        }
        return flags;
    }

    String getFlagFileName() {
        return getFlagFileName(null);
    }

    String getFlagFileName(Year atYear) {
        final Optional<CountryFlag> foundFlag = getAllFlags().stream()
                .filter(f -> (atYear == null && f.getEndYear() == null) ||
                        (atYear != null && (f.getEndYear() == null || !f.getEndYear().isBefore(atYear)))
                )
                .findFirst();
        if (!foundFlag.isPresent()) {
            throw new CountryRuntimeException("No flag for " + name() + " at year " + atYear + " found");
        }
        return foundFlag.get().getFileName();
    }

    String getFlagSvgContent(String name) {
        final InputStream is = getClass().getResourceAsStream("flags/" + name);
        if (is == null) {
            throw new IllegalStateException("Flag resource not found: " + name);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            return "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"10\" height=\"10\"></svg>";
        }
    }

    public String getValue(String key) {
        return getProperties(key).getProperty(name());
    }

    public boolean independentAtYear(Year atYear) {
        final CountryInterval currentInterval = getCurrentInterval(atYear);
        if (currentInterval == null) {
            return false;
        }
        return currentInterval.getPartOf() == null &&
                currentInterval.getBelongsTo() == null &&
                currentInterval.getSubdivisionOf() == null;
    }

    public boolean partOfAtYear(Country country, Year atYear) {
        final CountryInterval currentInterval = getCurrentInterval(atYear);
        if (currentInterval == null) {
            return false;
        }
        return currentInterval.getPartOf() != null && Country.valueOf(currentInterval.getPartOf()) == country;
    }

    public boolean belongsToAtYear(Country country, Year atYear) {
        final CountryInterval currentInterval = getCurrentInterval(atYear);
        if (currentInterval == null) {
            return false;
        }
        return currentInterval.getBelongsTo() != null && Country.valueOf(currentInterval.getBelongsTo()) == country;
    }

    public boolean subdivisionOfAtYear(Country country, Year atYear) {
        final CountryInterval currentInterval = getCurrentInterval(atYear);
        if (currentInterval == null) {
            return false;
        }
        return currentInterval.getSubdivisionOf() != null && Country.valueOf(currentInterval.getSubdivisionOf()) == country;
    }

    public boolean existsAtYear(Year atYear) {
        final CountryInterval currentInterval = getCurrentInterval(atYear);
        return currentInterval != null && currentInterval.getPartOf() == null;
    }

    private CountryInterval getCurrentInterval(Year atYear) {
        final Year year = atYear != null ? atYear : Year.now();
        if (year.isBefore(Year.of(1900))) {
            return null;
        }
        final Optional<CountryInterval> currentIntervalOpt = countryData.getIntervals().stream()
                .sorted()
                .filter(i -> !i.getStart().isAfter(year) && (i.getEnd() == null || i.getEnd().isAfter(year)))
                .findFirst();
        return currentIntervalOpt.orElse(null);
    }
}
