package robot;

public enum Bookstore {
    GANDALF(new GandalfScrapper()),
    BONITO(new BonitoScrapper());

    private final BookstoreScrapper scrapper;

    Bookstore(BookstoreScrapper scrapper) {
        this.scrapper = scrapper;
    }

    public BookstoreScrapper getScrapper() {
        return scrapper;
    }
}
