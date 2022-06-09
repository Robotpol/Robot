package robot.gandalf;

import org.springframework.stereotype.Service;
import robot.Books;
import robot.Bookstore;

/**
 * @author Dominik Żebracki
 */
@Service
class GandalfService {

    private final GandalfBookRepository gandalfBookRepository;
    private final Bookstore bookstore = Bookstore.GANDALF;

    GandalfService(GandalfBookRepository gandalfBookRepository) {
        this.gandalfBookRepository = gandalfBookRepository;
    }

    Books provideBooks() {
        return new Books(GandalfBookMapper.toBook(gandalfBookRepository.findAll()));
    }

    boolean updateBooks() {
        gandalfBookRepository.deleteAll();
        gandalfBookRepository.saveAll(GandalfBookMapper.toGandalfBook(bookstore.getScrapper().call().books()));
        return true;
    }
}
