package robot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Mariusz Bal
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class ScrappedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String bookstore;
    private String title;
    private String author;
    private BigDecimal oldPrice;
    private BigDecimal price;
    private String link;
}
