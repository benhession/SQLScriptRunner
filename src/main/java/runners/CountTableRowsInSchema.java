package runners;

import org.jdbi.v3.core.Jdbi;
import support.DatabaseSupport;

import java.util.List;

public class CountTableRowsInSchema implements ScriptRunner {
  private final static String SCHEMA_NAME = "dev";

  @Override
  public void run() {
    long sourceCount = DatabaseSupport.withSourceDatabase(this::countAllTableRowsInSchema);
    System.out.println("The source database has " + sourceCount + " table(s) in schema " + SCHEMA_NAME);
    long replicaCount = DatabaseSupport.withReplicaDatabase(this::countAllTableRowsInSchema);
    System.out.println("The replica database has " + replicaCount + " table(s) in schema " + SCHEMA_NAME);
    assert sourceCount == replicaCount;
  }

  private long countAllTableRowsInSchema(Jdbi jdbi) {
    return jdbi.withHandle(handle -> {
      List<String> tableNames = handle.createQuery(
              "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + SCHEMA_NAME + "'")
          .mapTo(String.class)
          .list();

      long count = 0L;
      for (String tableName : tableNames) {
        count += handle.createQuery("SELECT count(*) FROM `" + SCHEMA_NAME + "`.`" + tableName + "`")
            .mapTo(Long.class)
            .first();
      }
      return count;
    });
  }
}
