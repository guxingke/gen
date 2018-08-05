package com.gxk.gen.tansfer;

import java.util.Map;

@FunctionalInterface
public interface Transfer {
  String apply(Map<String, Object> context, String input);
}
