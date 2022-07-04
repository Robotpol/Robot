package robot.gandalf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import robot.BookstoreScrapper;

@Configuration
class GandalfConfiguration {
    @Bean("gandalf-scrapper")
    BookstoreScrapper gandalfScrapper() {
        return new GandalfScrapperJsoup("https://www.gandalf.com.pl/promocje/bcb");
    }
}
