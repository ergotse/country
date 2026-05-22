# Country

A Java library that provides country data and flag resources from 1900 until today.  
Includes `ISO 3166-1 countries`, `UK countries (ISO 3166-2:GB)`, and selected `historical countries` without ISO codes.

**Sources:**
- [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)
- [ISO 3166-2:GB](https://en.wikipedia.org/wiki/ISO_3166-2:GB)
- Flag files are from Wikipedia (public domain).

---

## Overview

This library allows you to:
- Access all countries by ISO code.
- Get country names in different locales.
- Retrieve historical country names and intervals.
- Get SVG content of country flags.
- Configure native country and custom properties.

If a translation is not available for a selected `Locale`, the English name is used as a fallback.  
One country is marked as **native** (default: your system locale), configurable via `Country.properties`.

---

## Requirements

- `Java 8` or higher
- `Maven` for dependency management

---

## Installation

### Maven

Add the following to your `pom.xml`:

```xml
<dependency>
    <groupId>se.ergot</groupId>
    <artifactId>country</artifactId>
    <version>2.0.2</version>
</dependency>
```

---

## Settings
### Country Names / Translations

- Location: `src/main/resources/config/countries/`
- File format: `CountryNames_<language-code>.properties`
  **Example** for French: `CountryNames_fr.properties`

`Note:` Translations are *not accumulative* - a new file replaces the previous translations.

---

## Custom Properties

- Location: `src/main/resources/config/countries/`
- File format: `Country.<key>.properties`
  **Example:** Country.fifa.properties for FIFA codes

`Note:` Custom property files are *accumulative* — multiple files can be combined.

---

## Native Country

Set the native country in Country.properties: ```country.native=SE```

---

## Flag Files

Location: `src/main/resources/se/ergot/country/flags/`

File naming convention:
- `<iso>.svg` - current flag
- `<iso>_<endYear>.svg` - historical flags

## Usage

All countries are accessed from the `Country` enum via their ISO code.

Example:
```java
final Country sweden = Country.SE;

// Get country name
String name = sweden.getName();                 // Default locale
String nameEN = sweden.getName(Locale.ENGLISH); // English

// Get flag SVG content
String flagSvg = sweden.getFlagSvg();

// Get all flags
List<CountryFlag> flags = sweden.getAllFlags();

// Check historical independence or relations at a specific year
boolean independent1997 = Country.EE.independentAtYear(Year.of(1997));
boolean partOfRU1907 = Country.EE.partOfAtYear(Country.RU, Year.of(1907));
boolean belongsToGB1977 = Country.DM.belongsToAtYear(Country.GB, Year.of(1977));

// Get all administering countries at a year (supports condominiums with multiple countries)
List<Country> rulers = Country.VU.getBelongsToAtYear(Year.of(1970)); // [GB, FR]

// Get the nature of the dependency relationship
BelongsToVariant variant = Country.NO.getBelongsToVariantAtYear(Year.of(1900)); // PERSONAL_UNION
BelongsToVariant variant = Country.DM.getBelongsToVariantAtYear(Year.of(1977)); // COLONY

// Get country by its iso code
Country congo = Country.find("CD");
String congoName = congo.getName(Locale.ENGLISH);

// Find country by previous ISO code
Country yugoslavia = Country.find("YU");

// Get previous names
Map<Year, String> previousNames = Country.CD.getPreviousNames(Locale.ENGLISH);

// Get the interval record active at a specific year (null if before 1900 or no interval exists)
CountryInterval interval = Country.EE.getCurrentInterval(Year.of(1967)); // interval with partOf = "SU"
CountryInterval current = Country.SE.getCurrentInterval(null);           // uses Year.now()

// Get country properties
String fifaCode = Country.SE.getValue("fifa");
```

## Special ISO Code Cases

Some codes clash or are not standard ISO 3166-1:

| Country               | Code | Notes                                        |
|-----------------------| ---- | -------------------------------------------- |
| Czechoslovakia        | CSHH | ISO code CS clashes with Serbia & Montenegro |
| Ottoman Empire        | OE_X | No ISO 3166-1 code                           |
| Zanzibar              | TZ_Z | No ISO 3166-1 code                           |
| Korean Empire (-1910) | KR_X | No ISO 3166-1 code                           |

## Dependency Relationships (`belongsTo`)

The `belongsTo` field on a country interval describes that the country was under the authority of one or more other countries during that period. It is a list of ISO codes to support cases where multiple powers jointly administered a territory (condominiums).

The optional `variant` field classifies the nature of the relationship:

| Variant | JSON value | Description | Example |
|---|---|---|---|
| `COLONY` | `colony` | Direct colonial administration | Lesotho → GB |
| `PROTECTORATE` | `protectorate` | External power controls foreign affairs/defence; local governance continues | Kuwait → GB |
| `MANDATE` | `mandate` | League of Nations mandate or UN Trust Territory | Iraq → GB |
| `PERSONAL_UNION` | `personalUnion` | Sovereign states sharing a monarch, each with their own government | Norway → SE |
| `CONDOMINIUM` | `condominium` | Joint administration by two or more powers | Vanuatu → GB + FR |
| `ASSOCIATED_STATE` | `associatedState` | Self-governing state in free association | Cook Islands → NZ |
| `OCCUPIED_TERRITORY` | `occupiedTerritory` | De facto administration without recognised sovereignty | Western Sahara → MA |

A `null` variant means no classification has been assigned (e.g. territories with a unique legal status).

---

## CountryInterval

A `CountryInterval` represents a time-bound record describing a country's status during a specific period. Each country has one or more intervals covering its existence from 1900 onward.

| Field | Type | Description |
|---|---|---|
| `start` | `Year` | First year of the interval (inclusive). `null` if the interval predates 1900. |
| `openStart` | `Boolean` | `true` if the interval started before 1900 and the exact start is unknown. |
| `end` | `Year` | First year the interval no longer applies (exclusive). `null` means the interval is still current. |
| `partOf` | `String` | ISO code of the country this was absorbed into (e.g. Estonia → `SU` during Soviet occupation). Mutually exclusive with `belongsTo` and `subdivisionOf`. |
| `belongsTo` | `List<String>` | ISO codes of the administering country or countries. Used for dependencies, colonies, and condominiums. Mutually exclusive with `partOf` and `subdivisionOf`. |
| `variant` | `BelongsToVariant` | Classifies the nature of the `belongsTo` relationship. Only set when `belongsTo` is present. |
| `subdivisionOf` | `String` | ISO code of the parent country when the entity is a subdivision (e.g. England → `GB`). Mutually exclusive with `partOf` and `belongsTo`. |

An interval with none of `partOf`, `belongsTo`, or `subdivisionOf` set represents an **independent** country during that period.

The `end` year is **exclusive**: an interval ending in `1940` covers up to and including `1939`.

---

## License

Licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).  

