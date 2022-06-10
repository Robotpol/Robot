package robot.bonito;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.testng.MockitoTestNGListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import robot.Book;
import robot.Books;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.testng.Assert.*;

@SuppressWarnings("all")
@Listeners(MockitoTestNGListener.class)
public class BonitoTest {

   @Mock
    private BonitoScrapper bonitoScrapper;

   @Test
    void shouldReturnBonioBooks() {
       //given
       var expected = new Books(List.of(new Book("title", "author", BigDecimal.ONE, BigDecimal.TEN, "link")));
       Mockito.lenient().when(bonitoScrapper.call()).thenReturn(expected);
       var service = new BonitoService(new DummyRepository(), bonitoScrapper);
       var bonito = new Bonito(service);
       //when
       assertEquals(bonito.updateAndProvideBooks(), expected);
   }

   static class DummyRepository implements BonitoBookRepository {

       @Override
       public <S extends BonitoBook> S save(S entity) {
           return null;
       }

       @Override
       public <S extends BonitoBook> List<S> saveAll(Iterable<S> entities) {
           return null;
       }

       @Override
       public Optional<BonitoBook> findById(String s) {
           return Optional.empty();
       }

       @Override
       public boolean existsById(String s) {
           return false;
       }

       @Override
       public List<BonitoBook> findAll() {
           return List.of(
                   new BonitoBook("id", "title", "author", BigDecimal.ONE, BigDecimal.TEN, "link", LocalDateTime.now()));
       }

       @Override
       public Iterable<BonitoBook> findAllById(Iterable<String> strings) {
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
       public void delete(BonitoBook entity) {

       }

       @Override
       public void deleteAllById(Iterable<? extends String> strings) {

       }

       @Override
       public void deleteAll(Iterable<? extends BonitoBook> entities) {

       }

       @Override
       public void deleteAll() {

       }

       @Override
       public List<BonitoBook> findAll(Sort sort) {
           return null;
       }

       @Override
       public Page<BonitoBook> findAll(Pageable pageable) {
           return null;
       }

       @Override
       public <S extends BonitoBook> S insert(S entity) {
           return null;
       }

       @Override
       public <S extends BonitoBook> List<S> insert(Iterable<S> entities) {
           return null;
       }

       @Override
       public <S extends BonitoBook> Optional<S> findOne(Example<S> example) {
           return Optional.empty();
       }

       @Override
       public <S extends BonitoBook> List<S> findAll(Example<S> example) {
           return null;
       }

       @Override
       public <S extends BonitoBook> List<S> findAll(Example<S> example, Sort sort) {
           return null;
       }

       @Override
       public <S extends BonitoBook> Page<S> findAll(Example<S> example, Pageable pageable) {
           return null;
       }

       @Override
       public <S extends BonitoBook> long count(Example<S> example) {
           return 0;
       }

       @Override
       public <S extends BonitoBook> boolean exists(Example<S> example) {
           return false;
       }

       @Override
       public <S extends BonitoBook, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
           return null;
       }
   }
}
