package com.gxk.gen.cli;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

public class Cli {
  @Option(name = "--version", aliases = {
      "-v" }, handler = BooleanOptionHandler.class, usage = "print the current version")
  public boolean version = false;

  @Option(name = "--help", aliases = {
      "-h" }, handler = BooleanOptionHandler.class, usage = "print help message for the command")
  public boolean help = false;

  @Argument(usage = "Execute sub commands", metaVar = "<commands>", handler = SubCommandHandler.class)
  @SubCommands( {
      @SubCommand(name = "init", impl = InitCommand.class),
      @SubCommand(name = "list", impl = ListCommand.class),
      @SubCommand(name = "info", impl = InfoCommand.class),
  })
  public Command commands;

  public void printHelpMessage() {
    System.out.println("will be print help");
  }

  public void printErrorMessage() {
    System.out.println("un supported command, please check input args\n");
  }
}
