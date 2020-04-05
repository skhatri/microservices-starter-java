package com.github.starter.app.config;

import com.github.starter.core.exception.ConfigurationException;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.Statement;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import reactor.core.publisher.Mono;

public class JdbcScriptProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcScriptProcessor.class);

    void process(String loadResource, Connection conn) {
        List<Resource> files = loadFiles(loadResource);
        files.stream().forEach(f -> LOGGER.info("sql file {}", f.getFilename()));

        List<ExecutionScript> scripts = files.stream().map(resourceFile -> {
            List<String> statements = tokenise(resourceFile);
            return new ExecutionScript(resourceFile.getFilename(), statements);
        }).collect(Collectors.toList());
        scripts.forEach(executionScript ->
            executionScript.getCommands().stream()
                .forEach(command -> {
                    Statement stmt = conn.createStatement(command);
                    int updated = Mono.from(Mono.from(stmt.execute()).block().getRowsUpdated()).block();
                    LOGGER.info("running {}, updated {} ", command, updated);
                })
        );
    }

    private List<String> tokenise(Resource sqlFile) {
        try {
            Scanner scanner = new Scanner(sqlFile.getInputStream(), StandardCharsets.UTF_8);
            scanner.useDelimiter(";");
            List<String> items = new ArrayList<>();
            while (scanner.hasNext()) {
                String segment = scanner.next();
                if (!segment.trim().isEmpty()) {
                    items.add(segment);
                }
            }
            return items;
        } catch (Exception ex) {
            throw new ConfigurationException(String.format("error tokenising %s", sqlFile.getFilename()), ex);
        }
    }

    private List<Resource> loadFiles(String path) {
        String prefix = "file:";
        Pattern scriptPattern = Pattern.compile("^V([0-9]+)_(.+)sql$");
        try {
            List<Resource> resourceList;
            if (path.startsWith(prefix)) {
                File file = new File(path.substring(prefix.length()));
                resourceList = walkPath(file).stream().map(path1 -> new FileSystemResource(path1.toFile())).collect(Collectors.toList());
            } else {
                PathMatchingResourcePatternResolver pathResourceResolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = pathResourceResolver.getResources(path + "/*.sql");
                resourceList = Arrays.asList(resources);
            }
            return resourceList.stream().filter(p -> scriptPattern.matcher(p.getFilename()).matches())
                .sorted(getResourceComparator(scriptPattern)).collect(Collectors.toList());
        } catch (Exception exp) {
            throw new ConfigurationException("could not load initialise scripts", exp);
        }
    }

    private Comparator<Resource> getResourceComparator(Pattern scriptPattern) {
        return (Resource o1, Resource o2) -> {
            Matcher matchResult1 = scriptPattern.matcher(o1.getFilename());
            Matcher matchResult2 = scriptPattern.matcher(o2.getFilename());
            if (matchResult1.find() && matchResult2.find()) {
                Integer fileNum1 = Integer.parseInt(matchResult1.group(1));
                Integer fileNum2 = Integer.parseInt(matchResult2.group(1));
                return fileNum1.compareTo(fileNum2);
            }
            return 0;
        };
    }

    private List<Path> walkPath(File file) throws IOException {
        try (Stream<Path> stream = Files.walk(file.toPath())) {
            return stream.collect(Collectors.toList());
        }
    }
}
