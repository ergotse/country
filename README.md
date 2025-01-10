[<img src="https://jitpack.io/v/ergotse/country.svg">](https://jitpack.io/#ergotse/country)

# Country

This project includes all countries according to ISO 3166-1 plus UK-countries (ISO 3166-2:GB).  
Sources:   
https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2  
https://en.wikipedia.org/wiki/ISO_3166-2:GB

Country flag files is from wikipedia.

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
Add the following parts to your pom.xml:
```
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```
```
    <dependencies>
        <dependency>
            <groupId>se.ergot</groupId>
            <artifactId>country</artifactId>
            <version>$VERSION</version>
        </dependency>
    </dependencies>
```
Replace $VERSION with your version of choice (e.g. 1.0.4). 