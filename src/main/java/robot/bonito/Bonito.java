package robot.bonito;

import org.springframework.stereotype.Service;
import robot.BookProvider;
import robot.Books;

/**
 * @author Dominik Żebracki
 */
@Service
public class Bonito implements BookProvider {

    private final BonitoService bonitoService;

    Bonito(BonitoService bonitoService) {
        this.bonitoService = bonitoService;
    }

    @Override
    public Books provideBooks() {
        return bonitoService.provideBooks();
    }

    @Override
    public boolean updateBooks() {
        return bonitoService.updateBooks();
    }
}
