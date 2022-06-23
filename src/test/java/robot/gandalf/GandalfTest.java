package robot.gandalf;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import robot.Book;
import robot.BookProvider;
import robot.Books;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Listeners(MockitoTestNGListener.class)
public class GandalfTest {

    @Mock
    private GandalfScrapperJsoup gandalfScrapper;

    @Mock
    private GandalfBookRepository gandalfBookRepository;

    @Test
    void shouldReturnGandalfBooks_whenBooksFoundInRepository() {
        //given
        var expected = new Books(List.of(new Book("title", "author", BigDecimal.ONE, BigDecimal.TEN, "link")));
        Mockito.when(gandalfScrapper.call()).thenReturn(expected);
        Mockito.when(gandalfBookRepository.findAll())
                .thenReturn(List.of(new GandalfBook("id", "title", "author", BigDecimal.ONE, BigDecimal.TEN, "link", LocalDateTime.now())));

        BookProvider gandalf = new Gandalf(new GandalfService(gandalfBookRepository, gandalfScrapper));

        //when
        //then
        assertEquals(gandalf.updateAndProvideBooks(), expected);
    }
}
