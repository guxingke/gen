package com.gxk.gen.biz;

import com.gxk.gen.constant.Constant;
import com.gxk.gen.tansfer.Transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;
import org.zeroturnaround.exec.listener.ProcessListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class GenService {
  private static final Logger logger = LoggerFactory.getLogger(GenService.class);

  private static GenService me;

  private GenService() {
  }

  public static GenService getInstance() {
    if (me == null) {
      me = new GenService();
    }
    return me;
  }

  public void copyAndApply(Scaffold scaffold, Path destination, Transfer inline, Transfer tpl) {

    Path source = scaffold.path.resolve(Constant.GLOBAL_SCAFFOLD_TEMPLATE);
    Map<String, Object> context = scaffold.config;

    try {
      if (Files.notExists(destination)) {
        Files.createDirectories(destination);
      }

      Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
            throws IOException {
          Path realPath = destination.resolve(source.relativize(dir));
          Path transferPath = Paths.get(inline.apply(context, realPath.toString()).replace('.', '/'));

          if (Files.notExists(transferPath)) {
            Files.createDirectories(transferPath);
          }
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          // dir
          Path dirSourcePath = destination.resolve(source.relativize(file.getParent()));
          Path dirPath = Paths.get(inline.apply(context, dirSourcePath.toString()).replace('.', '/'));
          // file
          String fileName = inline.apply(context, file.getFileName().toString());

          Path path = dirPath.resolve(fileName);

          context.put("__current_dir", file.getParent().toString());

          String output = tpl.apply(context, file.getFileName().toString());

          try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            bw.write(output);
          } catch (IOException ex) {
            logger.error("write file error,basePath:{},fileName:{}", new Object[]{dirPath, fileName}, ex);
          }

          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e) {
      logger.error("copy resources failed:source:{},destination:{}", new Object[]{source, destination}, e);
      return;
    }

    // exec gen_finish.sh and remove it.
    File file = destination.toFile();
    File finishFile = Paths.get(destination.toString(), "gen_finish.sh").toFile();
    if (!(file.exists() && file.isDirectory() && finishFile.exists())) {
      System.out.println("No gen_finish.sh");
      return;
    }

    try {
      String output = new ProcessExecutor()
          .directory(file)
          .command("sh", "gen_finish.sh", file.toString())
          .destroyOnExit()
          .addListener(new ProcessListener() {
            @Override
            public void beforeStart(ProcessExecutor executor) {
              System.out.println("exec sh gen_finish.sh");
            }

            @Override
            public void afterFinish(Process process, ProcessResult result) {
              if (process.exitValue() != 0) {
                System.out.println("something bad");
              }
            }
          })
          .timeout(2, TimeUnit.MINUTES)
          .readOutput(true).execute()
          .outputUTF8();

      System.out.println(output);
    } catch (IOException | InterruptedException |
        TimeoutException e) {
      logger.error("some error ", e);
    }

    finishFile.delete();
  }
}

