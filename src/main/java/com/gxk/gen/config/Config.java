package com.gxk.gen.config;

import com.gxk.gen.constant.Constant;
import com.moandjiezana.toml.Toml;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Config {
  private static Config me;

  private Toml globalConfig;
  private Path globalConfigPath;

  private Config() {
    initGlobalConfig();
  }

  public static synchronized Config getInstance() {
    if (me == null) {
      me = new Config();
    }
    return me;
  }

  private void initGlobalConfig() {
    globalConfigPath = Paths.get(Constant.USER_HOME_DIR, ".gen", "config", Constant.GLOBAL_CONFIG_FILENAME);
    globalConfig = new Toml().read(globalConfigPath.toFile());
  }

  public Path getGlobalConfigPath() {
    return this.globalConfigPath;
  }
  public Toml getGlobalConfig() {
    return globalConfig;
  }

  public Toml getScaffoldConfig(String name) {
    List<String> dirs = globalConfig.getList("scaffold_dir");
    for (String dir : dirs) {
      File file = Paths.get(dir, name, "config.toml").toFile();
      if (!file.exists()) {
        continue;
      }

      return new Toml().read(file);
    }
    return null;
  }

  public String getActiveScaffoldPath(String name) {
    List<String> dirs = globalConfig.getList("scaffold_dir");
    for (String dir : dirs) {
      File file = Paths.get(dir, name, "config.toml").toFile();
      if (!file.exists()) {
        continue;
      }
      return dir;
    }
    return null;
  }
}
