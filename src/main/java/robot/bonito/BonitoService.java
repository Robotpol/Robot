package robot.bonito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robot.Book;
import robot.Books;
import robot.BookstoreScrapper;

import java.util.List;

/**
 * @author Dominik Żebracki
 */
@Service
class BonitoService {

    private static final String DEFAULT_LINK =
            "https://bonito.pl/kategoria/ksiazki/?results=L3YxL3NlYXJjaC9wcm9kdWN0cy8/Y2F0ZWdvcnk9a3NpYXpraQ==&page=";

    private final BonitoBookRepository bonitoBookRepository;
    private final BookstoreScrapper scrapper;

    @Autowired
    BonitoService(BonitoBookRepository bookRepository) {
        this.bonitoBookRepository = bookRepository;
        this.scrapper = new BonitoScrapperJsoup(DEFAULT_LINK);
    }

    BonitoService(BonitoBookRepository bookRepository, BookstoreScrapper scrapper) {
        this.bonitoBookRepository = bookRepository;
        this.scrapper = scrapper;
    }

    Books provideBooks() {
        return new Books(BonitoBookMapper.toBook(bonitoBookRepository.findAll()));
    }

    void updateBooks() throws ScrappingException {
        List<Book> books;
        try {
            books = scrapper.call().books();
        } catch (Exception e) {
            //TODO log exception
            System.err.println("An error occurred in a scrapper. No books have been updated.");
            throw new ScrappingException("An error occurred in a scrapper. No books have been updated.", e);
        }
        //TODO add some mechanism to distinguish if a book is already in the repo.
        bonitoBookRepository.deleteAll();
        bonitoBookRepository.saveAll(BonitoBookMapper.toBonitoBook(books));
    }
}