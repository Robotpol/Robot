package robot.pwn;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Dominik Żebracki
 */
interface PwnBookRepository extends MongoRepository<PwnBook, String> {
}
