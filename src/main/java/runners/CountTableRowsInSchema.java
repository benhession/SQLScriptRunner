package runners;

import org.jdbi.v3.core.Jdbi;
import support.DatabaseSupport;

import java.util.List;

public class CountTableRowsInSchema implements ScriptRunner {
  private final static String DEFAULT_SCHEMA_NAME = "dev";
  private String schemaName = DEFAULT_SCHEMA_NAME;

  @Override
  public void run() {
    run(DEFAULT_SCHEMA_NAME);
  }

  public void run(String schema) {
    schemaName = schema;
    long sourceCount = DatabaseSupport.withSourceDatabase(this::countAllTableRowsInSchema);
    System.out.println("The source database has " + sourceCount + " row(s) in schema " + schema);
    long replicaCount = DatabaseSupport.withReplicaDatabase(this::countAllTableRowsInSchema);
    System.out.println("The replica database has " + replicaCount + " row(s) in schema " + schema);
    assert sourceCount == replicaCount;
  }

  private long countAllTableRowsInSchema(Jdbi jdbi) {
    return jdbi.withHandle(handle -> {
      List<String> tableNames = handle.createQuery(
              "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + schemaName + "'")
          .mapTo(String.class)
          .list();

      long count = 0L;
      for (String tableName : tableNames) {
        count += handle.createQuery("SELECT count(*) FROM `" + schemaName + "`.`" + tableName + "`")
            .mapTo(Long.class)
            .first();
      }
      return count;
    });
  }
}
