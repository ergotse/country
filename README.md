# Country

This project includes all countries from 1900 until now, based on the ISO 3166-1 plus UK-countries (ISO 3166-2:GB).
It also contains some countries that doesn't have any ISO 3166 code. See note below.

Sources:   
https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2  
https://en.wikipedia.org/wiki/ISO_3166-2:GB

Country flag files are from Wikipedia (public domain).

If a translation of a country name can't be found for selected Locale, the english name is used.
One country is marked as native. Default is the country of your default Locale. This may be changed in the Country.properties file.  

## Settings
Settings can be changed or replaced by adding property-files to your project:
* Translations of country names are located in CountryNames_<language-code>.properties in config/countries-directory of your project's resources 
To add translations of country names in, for example, French, add CountryNames_fr.properties in your resources-directory with 
all the translations. See CountryNames_en.properties for examples. **N.B.** Translations are not accumulative. You have to replace the whole file. 
* To add custom properties to countries, add Country.<key>.properties in the config/countries-directory. FIFA-codes (Country.fifa.properties) for countries are added as an example. Changes here are accumulative. 
* To change native country. Add country.native to Country.properties with iso code of desired country (e.g. country.native=SE)

## Requirements
* JDK 8

## Installation
### With Maven
Add the following parts to your pom.xml:
```xml
    <dependencies>
        <dependency>
            <groupId>se.ergot</groupId>
            <artifactId>country</artifactId>
            <version>2.0.0</version>
        </dependency>
    </dependencies>
```
## Usage
All countries are accessed from Country via its iso code.

e.g.:
```java
final Country sweden = Country.SE;
final String name = sweden.getName();  // Name of sweden in default Locale
final String nameEN = sweden.getName(Locale.ENGLISH);  // Name of sweden in other Locale
final String flag = sweden.getFlagSvg();  // Svg content of current flag
```

## Note
The key of all countries is their ISO 3166-1 code.
Unfortunately, some clashes occur. **CS** was the ISO-code for both Czechoslovakia and Serbia & Montenegro. 
To keep them apart, Czechoslovakia is here given the code CSHH. 

Not all countries have had an ISO 3166-1 code (countries that existed before 1976).
To keep them in this listing, they had to be given a code not in ISO 3166-1.  
**N.B.** These codes are **not** ISO 3166-1 codes.
* Ottoman Empire (OE_X)
* Zanzibar (TZ_Z)
* Korea (KR_X)

