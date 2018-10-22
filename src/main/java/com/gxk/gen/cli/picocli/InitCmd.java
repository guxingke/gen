package com.gxk.gen.cli.picocli;

import com.gxk.gen.biz.GenService;
import com.gxk.gen.biz.Scaffold;
import com.gxk.gen.config.Config;
import com.gxk.gen.tansfer.InlineTransfer;
import com.gxk.gen.tansfer.TplTransfer;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@CommandLine.Command(name = "init", aliases = "n", description = "create new at the given path")
public class InitCmd implements Runnable {

  @CommandLine.Parameters(hidden = true)
  private List<String> args;

  @CommandLine.Parameters(index = "0")
  private String source;

  @CommandLine.Parameters(index = "1")
  private String target;

  @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
  private boolean helpRequested = false;

  @Override
  public void run() {
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
