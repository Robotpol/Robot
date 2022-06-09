package robot;

import org.springframework.stereotype.Service;

/**
 * @author Dominik Żebracki
 */
@Service
class GandalfService implements BookProvider{

    private final GandalfBookRepository gandalfBookRepository;
    private final Bookstore bookstore = Bookstore.GANDALF;

    GandalfService(GandalfBookRepository gandalfBookRepository) {
        this.gandalfBookRepository = gandalfBookRepository;
    }

    @Override
    public Books provideBooks() {
        return new Books(GandalfBookMapper.toBook(gandalfBookRepository.findAll()));
    }

    @Override
    public boolean updateBooks() {
        gandalfBookRepository.deleteAll();
        gandalfBookRepository.saveAll(GandalfBookMapper.toGandalfBook(bookstore.getScrapper().call().books()));
        return true;
    }
}
