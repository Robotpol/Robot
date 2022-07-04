package robot.gandalf;

import org.springframework.stereotype.Service;
import robot.BookProvider;
import robot.Books;

/**
 * @author Dominik Żebracki
 */
@Service
public class Gandalf implements BookProvider {

    private final GandalfService gandalfService;

    public Gandalf(GandalfService gandalfService) {
        this.gandalfService = gandalfService;
    }

    @Override
    public Books provideBooks() {
        return gandalfService.provideBooks();
    }

    @Override
    public Books updateAndProvideBooks() {
        try {
            gandalfService.updateBooks();
        } catch (ScrappingException e) {
            //TODO some information whether database was updated should be returned alongside books.
        }
        return provideBooks();
    }

    @Override
    public String toString() {
       return "GANDALF";
    }
}
