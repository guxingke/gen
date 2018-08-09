package com.gxk.gen.tansfer;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.FileTemplateLoader;

public class HandlebarsFactory {
  public static Handlebars createInlineTpl() {
    Handlebars handlebars = new Handlebars();
    regHelpers(handlebars);
    return handlebars;
  }

  public static Handlebars createTplWithLoader(FileTemplateLoader loader) {
    Handlebars handlebars = new Handlebars(loader);
    regHelpers(handlebars);
    return handlebars;
  }

  private static void regHelpers(Handlebars handlebars) {
    handlebars.registerHelper("tpl_buildno",
        (con, options) -> String.format("{{%s_buildno}}", con));
    handlebars.registerHelper("tpl_id",
        (con, options) -> String.format("{{%s_id}}", con));
    handlebars.registerHelper("raw",
        (con, options) -> options.fn.text());
    handlebars.registerHelper("up_first",
        (con, options) -> {
          char[] cs = ((String) con).toCharArray();
          cs[0] -= 32;
          return String.valueOf(cs);
        });
    // java package name , 下划线,中横线去除 , special char remove -> sc_rm
    handlebars.registerHelper("sc_rm",
      (con, options) -> con.toString().replaceAll("-|_", ""));
  }
}
