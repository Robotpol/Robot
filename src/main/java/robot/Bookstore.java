package robot;

public enum Bookstore {
    GANDALF(new GandalfScrapperJsoup("https://www.gandalf.com.pl/promocje/bcb/")),
    BONITO(new BonitoScrapperJsoup("https://bonito.pl/kategoria/ksiazki/?results=L3YxL3NlYXJjaC9wcm9kdWN0cy8/Y2F0ZWdvcnk9a3NpYXpraQ==&page="));

    private final BookstoreScrapper scrapper;

    Bookstore(BookstoreScrapper scrapper) {
        this.scrapper = scrapper;
    }

    public BookstoreScrapper getScrapper() {
        return scrapper;
    }
}
