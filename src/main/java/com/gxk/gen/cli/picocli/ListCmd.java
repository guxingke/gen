package com.gxk.gen.cli.picocli;

import com.gxk.gen.config.Config;
import com.gxk.gen.constant.Constant;
import com.moandjiezana.toml.Toml;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CommandLine.Command(name = "list", aliases = "ls", description = "show available scaffolds")
public class ListCmd implements Runnable {

  @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
  private boolean helpRequested = false;

  @Override
  public void run() {

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
