package robot.pwn;

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
class PwnService {

    private static final String DEFAULT_LINK =
            "https://ksiegarnia.pwn.pl/promocje?limit=96";

    private final PwnBookRepository pwnBookRepository;
    private final BookstoreScrapper scrapper;

    @Autowired
    PwnService(PwnBookRepository bookRepository) {
        this.pwnBookRepository = bookRepository;
        this.scrapper = new PwnScrapperJsoup(DEFAULT_LINK);
    }

    PwnService(PwnBookRepository bookRepository, BookstoreScrapper scrapper) {
        this.pwnBookRepository = bookRepository;
        this.scrapper = scrapper;
    }

    Books provideBooks() {
        return new Books(PwnBookMapper.toBook(pwnBookRepository.findAll()));
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
        pwnBookRepository.deleteAll();
        pwnBookRepository.saveAll(PwnBookMapper.toPwnBook(books));
    }
}
