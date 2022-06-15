package robot.bonito;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import robot.Book;
import robot.Books;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Listeners(MockitoTestNGListener.class)
public class BonitoTest {

   @Mock
    private BonitoScrapperJsoup bonitoScrapper;

   @Mock
   private BonitoBookRepository bonitoBookRepository;

   @Test
    void shouldReturnBonitoBooks_whenBooksFoundInRepository() {
       //given
       var expectedBooks = new Books(List.of(new Book("title", "author", BigDecimal.ONE, BigDecimal.TEN, "link")));
       Mockito.when(bonitoScrapper.call()).thenReturn(expectedBooks);
       Mockito.when(bonitoBookRepository.findAll())
               .thenReturn(List.of(new BonitoBook("id", "title", "author", BigDecimal.ONE, BigDecimal.TEN, "link", LocalDateTime.now())));

       var bonito = new Bonito(new BonitoService(bonitoBookRepository, bonitoScrapper));

       //when
       //then
       assertEquals(bonito.updateAndProvideBooks(), expectedBooks);
   }
}
