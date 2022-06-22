package robot.gandalf;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Dominik Żebracki
 */
interface GandalfBookRepository extends MongoRepository<GandalfBook, String> {
}
