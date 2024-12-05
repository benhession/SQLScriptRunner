import runners.CountTableRowsInSchema;
import runners.ScriptRunner;

public class SqlScriptRunner {
  public static void main(String[] args) {
    ScriptRunner scriptRunner = new CountTableRowsInSchema();
    scriptRunner.run();
  }
}
