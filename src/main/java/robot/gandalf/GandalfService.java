package robot.gandalf;

import org.springframework.stereotype.Service;
import robot.Books;
import robot.BookstoreScrapper;

/**
 * @author Dominik Żebracki
 */
@Service
class GandalfService {

    private final GandalfBookRepository gandalfBookRepository;
    private final BookstoreScrapper scrapper = new GandalfScrapper();

    GandalfService(GandalfBookRepository gandalfBookRepository) {
        this.gandalfBookRepository = gandalfBookRepository;
    }

    Books provideBooks() {
        return new Books(GandalfBookMapper.toBook(gandalfBookRepository.findAll()));
    }

    boolean updateBooks() {
        gandalfBookRepository.deleteAll();
        gandalfBookRepository.saveAll(GandalfBookMapper.toGandalfBook(scrapper.call().books()));
        return true;
    }
}
