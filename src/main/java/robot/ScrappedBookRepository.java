package robot;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author Mariusz Bal
 */
@Repository
interface ScrappedBookRepository extends CrudRepository<ScrappedBook, Long> {

//    Iterable<ScrappedBook> findByBookstoreIgnoreCaseAndTitleIgnoreCaseContainingAndAuthorIgnoreCaseContainingAndPriceLessThanEqualAndPriceGreaterThanEqual(
//            String bookstore, String title, String author, BigDecimal max, BigDecimal min
//    );

    @Query(value = "SELECT * FROM SCRAPPED_BOOK WHERE upper(bookstore)=upper(:bookstore) " +
            "AND upper(title) LIKE upper(:title) " +
            "AND upper(author) LIKE upper(:author) " +
            "AND price BETWEEN :min AND :max", nativeQuery = true)
    Iterable<ScrappedBook> find(String bookstore, String title, String author, BigDecimal max, BigDecimal min);
}
