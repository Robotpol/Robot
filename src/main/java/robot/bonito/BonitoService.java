package robot.bonito;

import org.springframework.stereotype.Service;
import robot.Books;
import robot.Bookstore;

/**
 * @author Dominik Żebracki
 */
@Service
class BonitoService {

    private final BonitoBookRepository bonitoBookRepository;
    private final Bookstore bookstore = Bookstore.BONITO;

    private BonitoService(BonitoBookRepository bookRepository) {
        this.bonitoBookRepository = bookRepository;
    }


    Books provideBooks() {
        return new Books(BonitoBookMapper.toBook(bonitoBookRepository.findAll()));
    }

    boolean updateBooks() {
        bonitoBookRepository.deleteAll();
        bonitoBookRepository.saveAll(BonitoBookMapper.toBonitoBook(bookstore.getScrapper().call().books()));
        return true;
    }
}