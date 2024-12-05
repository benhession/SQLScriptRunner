package support;

import lombok.experimental.UtilityClass;
import org.jdbi.v3.core.Jdbi;

import java.util.function.Function;

@UtilityClass
public class DatabaseSupport {
  private static final String SOURCE_DB_URL = System.getenv("SOURCE_DB_URL");
  private static final String SOURCE_DB_USER = System.getenv("SOURCE_DB_USER");
  private static final String SOURCE_DB_PASSWORD = System.getenv("SOURCE_DB_PASSWORD");
  private static final String REPLICA_DB_URL = System.getenv("REPLICA_DB_URL");
  private static final String REPLICA_DB_USER = System.getenv("REPLICA_DB_USER");
  private static final String REPLICA_PASSWORD = System.getenv("REPLICA_PASSWORD");


  public <T> T withSourceDatabase(Function<Jdbi, T> jdbiConsumer) {
    Jdbi jdbi = Jdbi.create(SOURCE_DB_URL, SOURCE_DB_USER, SOURCE_DB_PASSWORD);
    return jdbiConsumer.apply(jdbi);
    }

    public <T> T withReplicaDatabase(Function<Jdbi, T> jdbiConsumer) {
      Jdbi jdbi = Jdbi.create(REPLICA_DB_URL, REPLICA_DB_USER, REPLICA_PASSWORD);
      return jdbiConsumer.apply(jdbi);
    }
}
