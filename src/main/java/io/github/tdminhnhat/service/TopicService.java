package io.github.tdminhnhat.service;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.Resource;
import io.github.classgraph.ScanResult;
import io.github.tdminhnhat.entity.EntityInformation;
import io.github.tdminhnhat.exception.ClassNameNotFoundException;
import jakarta.persistence.*;
import lombok.NonNull;
import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.util.*;

public class TopicService {

    private static final String PACKAGE_ENTITY_DEFAULT = "io.github.tdminhnhat.entity.topics";
    private static final String PACKAGE_ENTITY_USERS = "io.github.tdminhnhat.entity.users";
    private static final String PACKAGE_USERNAME = PACKAGE_ENTITY_USERS + ".{username}";
    private static final String PACKAGE_USERNAME_TOPIC = PACKAGE_ENTITY_USERS + ".{username}.{topic}";

    /**
     * Get all the default topics by this library. Not created from the users.
     *
     * @return {@link String}[] - Return an array of the topics name.
     * @since v0.0.1-beta
     */
    public static String[] getListDefaultTopics() {
        return new Reflections(PACKAGE_ENTITY_DEFAULT)
                .get(Scanners.TypesAnnotated.with(Entity.class).asClass())
                .stream()
                .map(Class::getPackageName)
                .map(packageName -> {
                    String topic = packageName.replace(PACKAGE_ENTITY_DEFAULT + ".", "");
                    String[] splitPackage = topic.split("\\.");
                    return splitPackage.length > 0 ? splitPackage[0] : topic;
                })
                .distinct()
                .toList()
                .toArray(new String[]{});
    }

    /**
     * Get all the users name who have supported for writing their topics and share inside this library.
     *
     * @return {@link String}[] - Return an array of the users name
     * @since v0.0.1-beta
     */
    public static String[] getListUsers() {
        return new Reflections(PACKAGE_ENTITY_USERS)
                .get(Scanners.TypesAnnotated.with(Entity.class).asClass())
                .stream()
                .map(Class::getPackageName)
                .map(packageName -> packageName.replace(PACKAGE_ENTITY_USERS + ".", "").split("\\.")[0])
                .distinct()
                .toList()
                .toArray(new String[]{});
    }

    /**
     * Get all the topics by username
     *
     * @param username The name of the user who has provided their topics
     * @return {@link String}[] - Return an array of the topics from the user by username.
     * @since v0.0.1-beta
     */
    public static String[] getListTopicsByUser(String username) {
        return new Reflections(PACKAGE_USERNAME.replace("{username}", username))
                .get(Scanners.TypesAnnotated.with(Entity.class).asClass())
                .stream()
                .map(Class::getPackageName)
                .map(packageName -> {
                    String topic = packageName.replace(PACKAGE_USERNAME.replace("{username}", username) + ".", "");
                    String[] splitPackage = topic.split("\\.");
                    return splitPackage.length > 0 ? splitPackage[0] : topic;
                })
                .distinct()
                .toList()
                .toArray(new String[]{});
    }

    /**
     * Get all the classes inside the topic.
     *
     * @param username The name of the user who has provided their topics. If null, it will get the default topics.
     * @param topic    The name of the topic to get the classes from.
     * @return {@link List} - Return a list of {@link EntityInformation} inside the topic.
     * @since v0.0.1-beta
     */
    public static List<EntityInformation> getListClassTopic(String username, @NonNull String topic) {
        return getListClassWorkJPATopic(username, topic).parallelStream().map(TopicService::mapClassToEntityInformation).toList();
    }

    /**
     * Get all the classes nature inside the topic
     *
     * @param username The name of the user who has provided their topics. If null, it will get the default topics.
     * @param topic    The name of the topic to get the classes from.
     * @return {@link List} - Return a list of {@link Class} inside the topic.
     * @since v0.0.1-beta
     */
    public static List<Class<?>> getListClassWorkJPATopic(String username, String topic) {
        AnnotatedElement[] listAnnotatedElements = new AnnotatedElement[]{
                Entity.class, Embeddable.class, MappedSuperclass.class
        };
        String packageScanning = Objects.isNull(username) ? PACKAGE_ENTITY_DEFAULT + "." + topic : PACKAGE_USERNAME_TOPIC.replace("{username}", username).replace("{topic}", topic);
        return new Reflections(packageScanning).get(Scanners.TypesAnnotated.with(listAnnotatedElements).asClass()).stream().toList();
    }

    public static String getPackageNameSelectTopic(String username, String topic) {
       return Objects.isNull(username) ? PACKAGE_ENTITY_DEFAULT + "." + topic : PACKAGE_USERNAME_TOPIC.replace("{username}", username).replace("{topic}", topic);
    }

    public static String getContentClass(String packageName, String className) throws IOException {
        try(ScanResult scanResult = new ClassGraph().enableClassInfo().acceptPackages(packageName).scan()) {
            ClassInfo classInfo = scanResult.getAllClasses().filter(item -> item.getSimpleName().equals(className)).stream().findFirst().orElseThrow(() -> new ClassNameNotFoundException("Can't find the class to scan content"));
            Resource resource = classInfo.getResource();
            InputStream inputStream = resource.open();
            byte[] classBytes = inputStream.readAllBytes();
            return decompileClassBytes(classBytes);
        }
    }

    private static EntityInformation mapClassToEntityInformation(Class<?> clazzItem) {
        return new EntityInformation(
                clazzItem.getName().split("\\.")[clazzItem.getName().split("\\.").length - 1],
                clazzItem.getSuperclass().getName().split("\\.")[clazzItem.getSuperclass().getName().split("\\.").length - 1],
                clazzItem.getPackageName(),
                Arrays.stream(clazzItem.getDeclaredFields()).count(),
                clazzItem.isAnnotationPresent(Entity.class),
                Arrays.stream(clazzItem.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class))
                        .count()
        );
    }

    private static String decompileClassBytes(byte[] classBytes) throws IOException {
        // 1. Save to temp file
        File tempClassFile = File.createTempFile("InMemoryClass", ".class");
        try (FileOutputStream fos = new FileOutputStream(tempClassFile)) {
            fos.write(classBytes);
        }

        // 2. Prepare string builder to capture Java output
        StringBuilder javaOutput = new StringBuilder();

        OutputSinkFactory sinkFactory = new OutputSinkFactory() {
            @Override
            public <T> Sink<T> getSink(SinkType sinkType, SinkClass sinkClass) {
                return (T t) -> {
                    if (sinkType == SinkType.JAVA && sinkClass == SinkClass.STRING) {
                        javaOutput.append(t.toString()).append(System.lineSeparator());
                    }
                };
            }

            @Override
            public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
                if (sinkType == SinkType.JAVA) {
                    return List.of(SinkClass.STRING);
                }
                return Collections.emptyList();
            }
        };

        // 3. Run CFR
        Map<String, String> options = Map.of(
                "comments", "false",
                "silent", "true"
        );

        CfrDriver driver = new CfrDriver.Builder()
                .withOptions(options)
                .withOutputSink(sinkFactory)
                .build();

        driver.analyse(Collections.singletonList(tempClassFile.getAbsolutePath()));

        // 4. Delete temp file
        tempClassFile.delete();

        // 5. Clean and return
        return cleanJavaOutput(javaOutput.toString());
    }

    private static String cleanJavaOutput(String code) {
        StringBuilder cleaned = new StringBuilder();
        String[] lines = code.split("\n");

        boolean inBlockComment = false;

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.startsWith("package ")) continue;
            if (trimmed.startsWith("/*")) {
                inBlockComment = true;
                continue;
            }
            if (trimmed.endsWith("*/")) {
                inBlockComment = false;
                continue;
            }
            if (inBlockComment || trimmed.startsWith("//")) continue;
            if (trimmed.contains("Decompiled with CFR")) continue;

            cleaned.append(line).append(System.lineSeparator());
        }

        return cleaned.toString();
    }
}
