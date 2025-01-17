# Country

This project includes all countries according to ISO 3166-1 plus UK-countries (ISO 3166-2:GB).  
Sources:   
https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2  
https://en.wikipedia.org/wiki/ISO_3166-2:GB

Country flag files is from Wikipedia (public domain).

If a translation of a country name can't be found for selected Locale, the english name is used.
One country is marked as native. Default is the country of your default Locale. This may be changed in property file.  

## Settings
Settings can be changed or replaced by adding property-files to your project:
* Translations of country names is located in CountryNames_<language-code>.properties. 
To add translations of country names in for example french, add CountryNames_fr.properties with 
all the translations. See CountryNames_en.properties for examples.
* To add custom properties to countries, make changes i Country.properties. FIFA-codes for countries is added
* To change native country. Add country.native to Country.properties with iso code of desired country (e.g. country.native=SE) 

## Requirements
* JDK 18

## Installation
### With Maven
Add the following parts to your pom.xml (with version number you installed):
```xml
    <dependencies>
        <dependency>
            <groupId>se.ergot</groupId>
            <artifactId>country</artifactId>
            <version>1.0.0</version>
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