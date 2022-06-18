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
        try {
            bonitoService.updateBooks();
            return true;
        } catch (ScrappingException e) {
            //TODO some more meaningful information should be returned
            // about database update outcome after dividing into microservices
            return false;
        }
    }

    @Override
    public Books updateAndProvideBooks() {
        try {
            bonitoService.updateBooks();
        } catch (ScrappingException e) {
            //TODO some information whether database was updated should be returned alongside books.
        }
        return provideBooks();
    }

    @Override
    public String toString() {
       return "BONITO";
    }
}
