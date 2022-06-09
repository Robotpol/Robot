package robot.bonito;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Dominik Żebracki
 */
interface BonitoBookRepository extends MongoRepository<BonitoBook, String> {
}
