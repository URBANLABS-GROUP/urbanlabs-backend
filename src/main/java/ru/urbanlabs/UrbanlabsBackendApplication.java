package ru.urbanlabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings({"HideUtilityClassConstructor", "PMD.UseUtilityClass"})
public class UrbanlabsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrbanlabsBackendApplication.class, args);
    }
}
