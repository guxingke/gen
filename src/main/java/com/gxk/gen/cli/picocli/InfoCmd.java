package com.gxk.gen.cli.picocli;

import com.gxk.gen.config.Config;
import com.gxk.gen.constant.Constant;
import com.moandjiezana.toml.Toml;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@CommandLine.Command(name = "info", description = "show gen info.")
public class InfoCmd implements Runnable {

  @CommandLine.Parameters(description = "the target scaffold name", defaultValue = "global")
  private String name;

  @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
  private boolean helpRequested = false;

  @Override
  public void run() {
    if (name.equals("global")) {
      try {
        System.out.println("-------------------------");
        System.out.println("show global config");
        System.out.println("-------------------------");
        System.out.println();

        List<String> lines = Files.readAllLines(Config.getInstance().getGlobalConfigPath());
        lines.forEach(System.out::println);

        return;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    Toml config = Config.getInstance().getGlobalConfig();
    List<String> dirs = config.getList("scaffold_dir");
    for (String dir : dirs) {
      File file = Paths.get(dir, name, "config.toml").toFile();
      if (!file.exists()) {
        continue;
      }
      try {
        List<String> lines = Files.readAllLines(Paths.get(dir, name, Constant.GLOBAL_SCAFFOLD_CONFIG_NAME));
        lines.forEach(System.out::println);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
