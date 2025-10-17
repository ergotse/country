package se.ergot.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import io.github.classgraph.ScanResult;
import se.ergot.country.data.CountryData;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class CountryPropertyLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Map<String, Properties> namedPropertiesMap = new ConcurrentHashMap<>();
    private static final Map<String, CountryData> countryDataMap = new HashMap<>();

    static {
        mapper.registerModule(new JavaTimeModule());
        loadAllResources("countries", false);
        loadAllResources("config/countries", true);
    }

    private CountryPropertyLoader() {
    }

    public static Properties getProperties(String key) {
        return namedPropertiesMap.getOrDefault(key, new Properties());
    }

    public static CountryData getCountryData(String iso) {
        return countryDataMap.computeIfAbsent(iso, d -> new CountryData());
    }

    private static void loadAllResources(String dir, boolean isExternal) {
        try (ScanResult scanResult = new ClassGraph().acceptPaths(dir).scan()) {
            for (Resource resource : scanResult.getAllResources()) {
                readCountryResource(resource, resource.getPath(), isExternal);
            }
        } catch (Exception e) {
            throw new CountryInitializationException("Failed to load country JSON", e);
        }
    }

    private static void readCountryResource(Resource resource, String path, boolean isExternal) {
        try {
            if (path.endsWith(".json")) {
                readCountryDataFile(resource, isExternal);
            } else if (path.matches(".*Country(\\.[a-zA-Z_-]+)?\\.properties$")) {
                readPropertiesFile(resource);
            }
        } catch (IOException e) {
            throw new CountryInitializationException("Failed to load file: " + path, e);
        }
    }

    private static void readPropertiesFile(Resource resource) throws IOException {
        final Properties prop = new Properties();
        try (InputStream inputStream = resource.open()) {
            prop.load(inputStream);
        }

        final String propertyFileName = resource.getPath().substring(resource.getPath().lastIndexOf('/') + 1);
        final String key = extractKeyFromFileName(propertyFileName);

        mergeProperties(key, prop);
    }

    private static void mergeProperties(String key, Properties newProps) {
        Properties existing = namedPropertiesMap.computeIfAbsent(key, k -> new Properties());
        for (String name : newProps.stringPropertyNames()) {
            existing.setProperty(name, newProps.getProperty(name));
        }
    }

    private static void readCountryDataFile(Resource resource, boolean isExternal) throws IOException {
        final String filename = resource.getPath();
        final String iso = filename.substring(filename.lastIndexOf('/') + 1, filename.lastIndexOf('.'))
                .toUpperCase();
        final InputStream is = resource.open();
        final CountryData json = mapper.readValue(is, CountryData.class);
        if (isExternal) {
            countryDataMap.put(iso, json);
        } else {
            countryDataMap.putIfAbsent(iso, json);
        }
    }

    private static String extractKeyFromFileName(String fileName) {
        String[] parts = fileName.split("\\.");
        return parts.length > 2 ? parts[1] : "";
    }

}
