package com.gxk.gen.biz;

import java.nio.file.Path;
import java.util.Map;

public class Scaffold {

  public final Path path;
  public final Map<String, Object> config;

  public Scaffold(Path path, Map<String, Object> config) {
    this.path = path;
    this.config = config;
  }
}
