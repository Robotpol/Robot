package robot;

public enum Bookstore {
    GANDALF(new GandalfScrapperJsoup()),
    BONITO(new BonitoScrapperJsoup());

    private final BookstoreScrapper scrapper;

    Bookstore(BookstoreScrapper scrapper) {
        this.scrapper = scrapper;
    }

    public BookstoreScrapper getScrapper() {
        return scrapper;
    }
}
