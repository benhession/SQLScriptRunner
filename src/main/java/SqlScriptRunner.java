import runners.CountTablesInSchema;
import runners.ScriptRunner;

public class SqlScriptRunner {
  public static void main(String[] args) {
    ScriptRunner scriptRunner = new CountTablesInSchema();
    scriptRunner.run();
  }
}
