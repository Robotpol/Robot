package robot.bonito;

import org.springframework.beans.factory.annotation.Qualifier;
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

    private final BonitoBookRepository bonitoBookRepository;
    private final BookstoreScrapper scrapper;

    BonitoService(BonitoBookRepository bookRepository, @Qualifier("bonito-scrapper") BookstoreScrapper scrapper) {
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
