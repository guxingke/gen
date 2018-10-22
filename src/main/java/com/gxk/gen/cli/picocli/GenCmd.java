package com.gxk.gen.cli.picocli;


import picocli.CommandLine;

@CommandLine.Command(
  name = "gen",
  version = "0.0.1",
  mixinStandardHelpOptions = true,
  subcommands = {
    CommandLine.HelpCommand.class,
    InfoCmd.class,
    InitCmd.class,
    ListCmd.class,
  })
public class GenCmd implements Runnable {

  @Override
  public void run() {
    CommandLine.run(new GenCmd(), "-h");
  }

  public static void run(String[] args) {
    CommandLine.run(new GenCmd(), args);
  }
}
