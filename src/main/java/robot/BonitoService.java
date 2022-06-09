package robot;

import org.springframework.stereotype.Service;

/**
 * @author Dominik Żebracki
 */
@Service
class BonitoService implements BookProvider {

    private final BonitoBookRepository bookRepository;
    private final Bookstore bookstore = Bookstore.BONITO;

    private BonitoService(BonitoBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Books provideBooks() {
        return new Books(BonitoBookMapper.toBook(bookRepository.findAll()));
    }

    void updateBooks() {
        bookRepository.saveAll(BonitoBookMapper.toBonitoBook(bookstore.getScrapper().call().books()));
    }
}