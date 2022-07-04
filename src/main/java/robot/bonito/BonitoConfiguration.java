package robot.bonito;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import robot.BookstoreScrapper;

@Configuration
class BonitoConfiguration {
    @Bean("bonito-scrapper")
    BookstoreScrapper bonitoScrapper() {
        return new BonitoScrapperJsoup(
                "https://bonito.pl/kategoria/ksiazki/?results=L3YxL3NlYXJjaC9wcm9kdWN0cy8/Y2F0ZWdvcnk9a3NpYXpraQ==&page="
        );
    }
}
