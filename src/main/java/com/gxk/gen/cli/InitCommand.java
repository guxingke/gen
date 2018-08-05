package com.gxk.gen.cli;

import com.gxk.gen.biz.GenService;
import com.gxk.gen.biz.Scaffold;
import com.gxk.gen.config.Config;
import com.gxk.gen.tansfer.InlineTransfer;
import com.gxk.gen.tansfer.TplTransfer;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InitCommand implements Command {
  @Argument(usage = "create new at the given path", metaVar = "<args>")
  public List<String> args = new ArrayList<>();

  @Option(name = "--help", aliases = {
      "-h"}, handler = BooleanOptionHandler.class, usage = "find help about this command")
  private boolean help = false;

  /**
   * Executes the command
   */
  @Override
  public void execute() {
    if (help || args.size() != 2) {
      System.out.println("Scaffold out a new directory structure.");
      System.out.println("usage: gen init [scaffold name] [target path] ");
      CmdLineParser parser = new CmdLineParser(this, ParserProperties.defaults().withUsageWidth(120));
      parser.printUsage(System.out);
      return;
    }

    String source = args.get(0);
    String target = args.get(1);

    String scaffoldDir = Config.getInstance().getActiveScaffoldPath(source);
    if (StringUtils.isEmpty(scaffoldDir)) {
      System.out.println("invalid scaffold path");
      return;
    }

    Map<String, Object> context = Config.getInstance().getScaffoldConfig(source).toMap();

    // all template should be provide name.
    String[] strs = target.split("/");
    if (strs.length < 1) {
      System.out.println("invalid target path");
      return;
    }

    String name = strs[strs.length - 1];
    context.put("name", name);
    // check all option value
    Map<String, Object> options = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    context.forEach((key, val) -> {
      if (val instanceof String) {
        System.out.println(String.format("%s : enter to use default [%s]:", key, val));
        String option = scanner.nextLine();
        if (!option.isEmpty()) {
          options.put(key, option);
        }
      }
    });
    options.forEach(context::put);

    Scaffold scaffold = new Scaffold(Paths.get(scaffoldDir, source), context);
    GenService service = GenService.getInstance();

    service.copyAndApply(scaffold, Paths.get(target), new InlineTransfer(), new TplTransfer());

    System.out.println("Successfully created new scaffold.");
  }
}
