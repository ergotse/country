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
    <version>2.0.0</version>
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

// Get country by its iso code
Country congo = Country.find("CD");
String congoName = congo.getName(Locale.ENGLISH);

// Find country by previous ISO code
Country yugoslavia = Country.find("YU");

// Get previous names
Map<Year, String> previousNames = Country.CD.getPreviousNames(Locale.ENGLISH);

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

## License

Licensed under the [GNU General Public License v3.0 (GPL-3.0)](https://www.gnu.org/licenses/gpl-3.0.html).  

