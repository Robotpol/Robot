package robot.gandalf;

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
class GandalfService {

    private static final String DEFAULT_LINK = "https://www.gandalf.com.pl/promocje/bcb";

    private final GandalfBookRepository gandalfBookRepository;
    private final BookstoreScrapper scrapper;

    @Autowired
    GandalfService(GandalfBookRepository gandalfBookRepository) {
        this.gandalfBookRepository = gandalfBookRepository;
        scrapper = new GandalfScrapperJsoup(DEFAULT_LINK);
    }

    GandalfService(GandalfBookRepository gandalfBookRepository, BookstoreScrapper bookstoreScrapper) {
        this.gandalfBookRepository = gandalfBookRepository;
        this.scrapper = bookstoreScrapper;
    }

    Books provideBooks() {
        return new Books(GandalfBookMapper.toBook(gandalfBookRepository.findAll()));
    }

    void  updateBooks() throws ScrappingException {
        List<Book> books;
        try {
            books = scrapper.call().books();
        } catch (Exception e) {
            //TODO log exception
            System.err.println("An error occurred in a scrapper. No books have been updated.");
            throw new robot.gandalf.ScrappingException("An error occurred in a scrapper. No books have been updated.", e);
        }
        //TODO add some mechanism to distinguish if a book is already in the repo.
        gandalfBookRepository.deleteAll();
        gandalfBookRepository.saveAll(GandalfBookMapper.toGandalfBook(books));
    }
}
