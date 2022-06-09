package robot.bonito;

import org.springframework.stereotype.Service;
import robot.Books;
import robot.BookstoreScrapper;

/**
 * @author Dominik Żebracki
 */
@Service
class BonitoService {

    private final BonitoBookRepository bonitoBookRepository;
    private final BookstoreScrapper scrapper = new BonitoScrapper();

    private BonitoService(BonitoBookRepository bookRepository) {
        this.bonitoBookRepository = bookRepository;
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