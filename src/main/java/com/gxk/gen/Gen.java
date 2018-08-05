package com.gxk.gen;

import com.gxk.gen.cli.Cli;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ParserProperties;

public class Gen {

  public static void main(String[] args) {
    Cli cli = new Cli();
    CmdLineParser parser = new CmdLineParser(cli, ParserProperties.defaults().withUsageWidth(120));

    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      cli.printErrorMessage();
      parser.printUsage(System.out);
    }

    if (cli.help || cli.version || args.length == 0) {
      cli.printHelpMessage();
      parser.printUsage(System.out);
      return;
    }

    cli.commands.execute();
  }
}
