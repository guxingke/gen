package com.gxk.gen.tansfer;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.util.Map;

public class InlineTransfer implements Transfer{

  @Override
  public String apply(Map<String, Object> context, String input) {
    Handlebars handlebars = HandlebarsFactory.createInlineTpl();

    try {
      Template compile = handlebars.compileInline(input);
      return compile.apply(context);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "!!!error!!!";
  }
}
