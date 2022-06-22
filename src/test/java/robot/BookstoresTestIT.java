package robot;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class BookstoresTestIT {

    private final BookProvider bookProvider = new DummyProvider();
    private Bookstores bookstores;

    @BeforeMethod
    public void setUp() {
        bookstores = new Bookstores(Set.of(bookProvider));
    }

    @Test
    public void shouldRetrieveBooks() {
        //given
        var expected = new Book("title", "author", BigDecimal.ONE, BigDecimal.TEN, "link");
        //when
        bookstores.updateAll();
        var actual = bookstores.getBooks("TEST-BOOKSTORE").books();
        //then
        assertEquals(actual.get(0), expected);
    }

    static class DummyProvider implements BookProvider {

        @Override
        public Books provideBooks() {
            return new Books(List.of(new Book(
                    "title",
                    "author",
                    BigDecimal.ONE,
                    BigDecimal.TEN,
                    "link")));
        }

        @Override
        public boolean updateBooks() {
            return false;
        }

        @Override
        public Books updateAndProvideBooks() {
            return new Books(List.of(new Book(
                    "title",
                    "author",
                    BigDecimal.ONE,
                    BigDecimal.TEN,
                    "link")));
        }

        @Override
        public String toString() {
            return "TEST-BOOKSTORE";
        }
    }
}
