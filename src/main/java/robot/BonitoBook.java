package robot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * @author Dominik Żebracki
 */
@Data
@Document
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
class BonitoBook {

    @Id
    private String id;
    private String title;
    private String author;
    private BigDecimal oldPrice;
    private BigDecimal price;
    private String link;
}
