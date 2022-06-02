package robot;

import java.util.*;

/**
 * @author Dominik Żebracki
 */
class Scrappers {

    private final Map<String, BookstoreScrapper> scrappers;

    static Scrappers createDefaultCollection() {
        var scrappers = new HashMap<String, BookstoreScrapper>();
        scrappers.put("bonito", new BonitoScrapper());
        scrappers.put("gandalf", new GandalfScrapper());
        return new Scrappers(scrappers);
    }

    Scrappers(Map<String, BookstoreScrapper> scrappers) {
        this.scrappers = scrappers;
    }

    List<BookstoreScrapper> getAll() {
        return scrappers.values().stream().toList();
    }

    List<BookstoreScrapper> get(String ... names) {
        return Arrays.stream(names)
                .map(String::toLowerCase)
                .map(n -> scrappers.getOrDefault(n, null))
                .filter(Objects::nonNull)
                .toList();
    }
}
