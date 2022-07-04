package robot.pwn;

import org.springframework.stereotype.Service;
import robot.BookProvider;
import robot.Books;

/**
 * @author Dominik Żebracki
 */
@Service
class Pwn implements BookProvider {

    private final PwnService pwnService;

    Pwn(PwnService pwnService) {
        this.pwnService = pwnService;
    }

    @Override
    public Books provideBooks() {
        return pwnService.provideBooks();
    }

    @Override
    public Books updateAndProvideBooks() {
        try {
            pwnService.updateBooks();
        } catch (ScrappingException e) {
            //TODO some information whether database was updated should be returned alongside books.
        }
        return provideBooks();
    }

    @Override
    public String toString() {
        return "PWN";
    }
}
