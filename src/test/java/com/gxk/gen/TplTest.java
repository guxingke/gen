package com.gxk.gen;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

@Ignore
public class TplTest {

  @Test
  public void testInline() throws Exception {
    Handlebars handlebars = new Handlebars();

    Template template = handlebars.compileInline("\\{{xxx}}");
    String output = template.apply(new HashMap<>());
    Assert.assertEquals("{{xxx}}", output);
  }

  @Test
  public void testInline2() throws Exception {
    Handlebars handlebars = new Handlebars();
    handlebars.registerHelper(
        "tpl_buildno",
        (context, options) -> String.format("{{%s_buildno}}", context)
    );

    Template template = handlebars.compileInline("{{tpl_buildno name}}");

    HashMap<String, Object> context = new HashMap<>();
    context.put("name", "osgw");

    String output = template.apply(context);
    Assert.assertEquals("{{osgw_buildno}}", output);
  }

  @Test
  public void testInline3() throws Exception {
    Handlebars handlebars = new Handlebars();
    handlebars.registerHelper(
        "raw",
        (context, options) -> {
          return options.fn.text();
        }
    );

    Template template = handlebars.compileInline("{{#raw}}{{xxx}}{{/raw}}");

    HashMap<String, Object> context = new HashMap<>();

    String output = template.apply(context);
    Assert.assertEquals("{{xxx}}", output);
  }
}
