package robot.pwn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import robot.BookstoreScrapper;

@Configuration
class PwnConfiguration {
    @Bean("pwn-scrapper")
    BookstoreScrapper pwnScrapper() {
        return new PwnScrapperJsoup(
                "https://ksiegarnia.pwn.pl/promocje?limit=96"
        );
    }
}
