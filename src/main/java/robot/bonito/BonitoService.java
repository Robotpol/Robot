package robot.bonito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robot.Books;
import robot.BookstoreScrapper;

/**
 * @author Dominik Żebracki
 */
@Service
class BonitoService {

    private final BonitoBookRepository bonitoBookRepository;
    private final BookstoreScrapper scrapper;

    @Autowired
    BonitoService(BonitoBookRepository bookRepository) {
        this.bonitoBookRepository = bookRepository;
        this.scrapper = new BonitoScrapper();
    }

    BonitoService(BonitoBookRepository bookRepository, BookstoreScrapper scrapper) {
        this.bonitoBookRepository = bookRepository;
        this.scrapper = scrapper;
    }

    Books provideBooks() {
        return new Books(BonitoBookMapper.toBook(bonitoBookRepository.findAll()));
    }

    boolean updateBooks() {
        bonitoBookRepository.deleteAll();
        bonitoBookRepository.saveAll(BonitoBookMapper.toBonitoBook(scrapper.call().books()));
        return true;
    }
}
