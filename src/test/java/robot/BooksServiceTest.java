package robot;

import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.util.List;

import static org.testng.Assert.*;

@Listeners(MockitoTestNGListener.class)
public class BooksServiceTest {

    private BooksService underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new BooksService();
    }

    @Test
    public void shouldCacheBooks() {
        // given
        String title = "test";
        String author = "test";
        BigDecimal oldPrice = new BigDecimal(10);
        BigDecimal newPrice = new BigDecimal(1);
        Book book = new Book(title, author, oldPrice, newPrice, "");
        Bookstore bookstore = Bookstore.BONITO;
        // when
        underTest.cacheBooks(bookstore, new Books(List.of(book)));
        Books books = underTest.getBooks(bookstore.name());
        // then
        Book cachedBook = books.books().get(0);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(books.books().size(), 1);
        softAssert.assertEquals(cachedBook.author(), author);
        softAssert.assertEquals(cachedBook.title(), title);
        softAssert.assertEquals(cachedBook.oldPrice(), oldPrice);
        softAssert.assertEquals(cachedBook.price(), newPrice);
        softAssert.assertAll();
    }

}
