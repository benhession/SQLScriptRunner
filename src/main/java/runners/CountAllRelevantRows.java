package runners;

import support.DatabaseSupport;

import java.util.List;

public class CountAllRelevantRows implements ScriptRunner {

  @Override
  public void run() {
    List<String> schemas = DatabaseSupport.withSourceDatabase(jdbi ->
        jdbi.withHandle(handle -> handle.createQuery("SELECT DISTINCT TABLE_SCHEMA FROM information_schema.tables "
            + "WHERE table_schema NOT IN ('mysql', 'information_schema', 'performance_schema', 'sys', 'awsdms_control')")
            .mapTo(String.class)
            .list()));
    CountTableRowsInSchema countTableRowsInSchema = new CountTableRowsInSchema();

    for (String schema : schemas) {
      countTableRowsInSchema.run(schema);
    }
  }
}
