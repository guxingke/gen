package com.gxk.gen.cli;

import com.gxk.gen.config.Config;
import com.gxk.gen.constant.Constant;
import com.moandjiezana.toml.Toml;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InfoCommand implements Command {

  @Argument(usage = "show scaffold info", metaVar = "<scaffold name>")
  public List<String> args = new ArrayList<>();//

  @Option(name = "--help", aliases = {
      "-h"}, handler = BooleanOptionHandler.class, usage = "find help about this command")
  private boolean help = false;

  /**
   * Executes the command
   */
  @Override
  public void execute() {
    if (help) {
      CmdLineParser parser = new CmdLineParser(this, ParserProperties.defaults().withUsageWidth(120));
      parser.printUsage(System.out);
      return;
    }
    if (args.size() < 1) {
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

    String info = args.get(0);
    Toml config = Config.getInstance().getGlobalConfig();

    List<String> dirs = config.getList("scaffold_dir");
    for (String dir : dirs) {
      File file = Paths.get(dir, info, "config.toml").toFile();
      if (!file.exists()) {
        continue;
      }
      try {
        List<String> lines = Files.readAllLines(Paths.get(dir, info, Constant.GLOBAL_SCAFFOLD_CONFIG_NAME));
        lines.forEach(System.out::println);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
