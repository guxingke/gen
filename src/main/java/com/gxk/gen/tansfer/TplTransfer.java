package com.gxk.gen.tansfer;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;

import java.io.IOException;
import java.util.Map;

public class TplTransfer implements Transfer {

  @Override
  public String apply(Map<String, Object> context, String input) {
    // context 内置变量, __current_dir
    String dir = ((String) context.get("__current_dir"));

    FileTemplateLoader loader = new FileTemplateLoader(dir, "");
    Handlebars handlebars = HandlebarsFactory.createTplWithLoader(loader);

    try {
      Template compile = handlebars.compile(input);
      return compile.apply(context);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "!!!error!!!";
  }
}
