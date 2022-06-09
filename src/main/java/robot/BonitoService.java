package robot;

import org.springframework.stereotype.Service;

/**
 * @author Dominik Żebracki
 */
@Service
class BonitoService implements BookProvider {

    private final BonitoBookRepository bonitoBookRepository;
    private final Bookstore bookstore = Bookstore.BONITO;

    private BonitoService(BonitoBookRepository bookRepository) {
        this.bonitoBookRepository = bookRepository;
    }

    @Override
    public Books provideBooks() {
        return new Books(BonitoBookMapper.toBook(bonitoBookRepository.findAll()));
    }

    @Override
    public boolean updateBooks() {
        bonitoBookRepository.deleteAll();
        bonitoBookRepository.saveAll(BonitoBookMapper.toBonitoBook(bookstore.getScrapper().call().books()));
        System.err.println("Bonito books updated");
        return true;
    }
}