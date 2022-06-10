package robot.gandalf;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.testng.MockitoTestNGListener;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import robot.Book;
import robot.Books;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.testng.Assert.assertEquals;

@SuppressWarnings("all")
@Listeners(MockitoTestNGListener.class)
public class GandalfTest {

    @Mock
    private GandalfScrapper gandalfScrapper;

    @Test
    void shouldReturnBonioBooks() {
        //given
        var expected = new Books(List.of(new Book("title", "author", BigDecimal.ONE, BigDecimal.TEN, "link")));
        Mockito.lenient().when(gandalfScrapper.call()).thenReturn(expected);
        var service = new GandalfService(new DummyRepo(), gandalfScrapper);
        var gandalf = new Gandalf(service);
        //when
        assertEquals(gandalf.updateAndProvideBooks(), expected);
    }

    static class DummyRepo implements GandalfBookRepository {
        @Override
        public <S extends GandalfBook> S save(S entity) {
            return null;
        }

        @Override
        public <S extends GandalfBook> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<GandalfBook> findById(String s) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(String s) {
            return false;
        }

        @Override
        public List<GandalfBook> findAll() {
            return List.of(
                    new GandalfBook("id", "title", "author", BigDecimal.ONE, BigDecimal.TEN, "link", LocalDateTime.now()));
        }

        @Override
        public Iterable<GandalfBook> findAllById(Iterable<String> strings) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(String s) {

        }

        @Override
        public void delete(GandalfBook entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends String> strings) {

        }

        @Override
        public void deleteAll(Iterable<? extends GandalfBook> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public List<GandalfBook> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<GandalfBook> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends GandalfBook> S insert(S entity) {
            return null;
        }

        @Override
        public <S extends GandalfBook> List<S> insert(Iterable<S> entities) {
            return null;
        }

        @Override
        public <S extends GandalfBook> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends GandalfBook> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends GandalfBook> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends GandalfBook> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends GandalfBook> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends GandalfBook> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends GandalfBook, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }
    }
}