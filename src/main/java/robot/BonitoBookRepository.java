package robot;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Dominik Żebracki
 */
public interface BonitoBookRepository extends MongoRepository<BonitoBook, String> {
}
