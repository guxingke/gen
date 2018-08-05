package com.gxk.gen.cli;

import com.gxk.gen.config.Config;
import com.gxk.gen.constant.Constant;
import com.moandjiezana.toml.Toml;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ListCommand implements Command {

  @Option(name = "--help", aliases = {
      "-h"}, handler = BooleanOptionHandler.class, usage = "find help about this command")
  private boolean help = false;

  /**
   * Executes the command
   */
  @Override
  public void execute() {
    if (help) {
      System.out.println("-------------------------");
      System.out.println("show available scaffold name");
      System.out.println("-------------------------");
      return;
    }

    Toml config = Config.getInstance().getGlobalConfig();
    List<String> dirs = config.getList(Constant.GLOBAL_SCAFFOLD_DIR);

    for (String dir : dirs) {
      System.out.println("----------------");
      System.out.println("show scaffold from : " + dir);
      System.out.println("----------------");
      try {
        Files.list(Paths.get(dir))
            .map(Path::getFileName)
            .map(Path::toString)
            .filter(it -> !it.startsWith(".")) // ignore hidden dir.
            .forEach(System.out::println);
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("\n");
    }
  }
}
