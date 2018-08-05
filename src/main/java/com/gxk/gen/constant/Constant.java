package com.gxk.gen.constant;

import java.io.File;

public class Constant {
  public static final String USER_HOME_DIR = System.getProperty("user.home");
  public static final String SEPARATOR = File.separator;
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  public static final String GLOBAL_CONFIG_FILENAME = "gen.toml";

  public static final String GLOBAL_SCAFOLD_DIR = "scaffold_dir";
  public static final String GLOBAL_SCAFOLD_CONFIG_NAME = "config.toml";
  public static final String GLOBAL_SCAFOLD_TEMPLATE= "template";
}
