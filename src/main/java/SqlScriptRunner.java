import runners.CountAllRelevantRows;
import runners.ScriptRunner;

public class SqlScriptRunner {
  public static void main(String[] args) {
    ScriptRunner scriptRunner = new CountAllRelevantRows();
    scriptRunner.run();
  }
}
