package com.gxk.gen.config;

import com.moandjiezana.toml.Toml;
import org.junit.Ignore;

import static org.junit.Assert.*;

@Ignore
public class ConfigTest {

  @org.junit.Test
  public void getGlobalConfig() throws Exception {
    Toml config = Config.getInstance().getGlobalConfig();

    assertNotNull(config);
    assertNotNull(config.getString("scaffold_dir"));
  }
}