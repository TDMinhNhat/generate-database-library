package io.github.tdminhnhat.service;

import io.github.tdminhnhat.entity.EntityInformation;
import jakarta.persistence.*;
import javassist.tools.reflect.Reflection;
import lombok.NonNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.*;
import java.sql.Ref;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class TopicService {

    private static final String PACKAGE_ENTITY_DEFAULT = "io.github.tdminhnhat.entity.topics";
    private static final String PACKAGE_ENTITY_USERS = "io.github.tdminhnhat.entity.users";
    private static final String PACKAGE_USERNAME = PACKAGE_ENTITY_USERS + ".{username}";
    private static final String PACKAGE_USERNAME_TOPIC = PACKAGE_ENTITY_USERS + ".{username}.{topic}";

    /**
     * Get all the default topics by this library. Not created from the users.
     * @return {@link String}[] - Return an array of the topics name.
     */
    public static String[] getListDefaultTopics() {
        return new Reflections(PACKAGE_ENTITY_DEFAULT)
                .get(Scanners.TypesAnnotated.with(Entity.class).asClass())
                .stream()
                .map(Class::getPackageName)
                .map(packageName -> packageName.replace(PACKAGE_ENTITY_DEFAULT + ".", ""))
                .distinct()
                .toList()
                .toArray(new String[]{});
    }

    /**
     * Get all the users name who have supported for writing their topics and share inside this library.
     * @return {@link String}[] - Return an array of the users name
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
     * @param username The name of the user who has provided their topics
     * @return {@link String}[] - Return an array of the topics from the user by username.
     */
    public static String[] getListTopicsByUser(String username) {
        return new Reflections(PACKAGE_ENTITY_USERS)
                .get(Scanners.TypesAnnotated.with(Entity.class).asClass())
                .stream()
                .map(Class::getPackageName)
                .map(packageName -> packageName.replace(PACKAGE_USERNAME.replace("{username}", username) + ".", ""))
                .distinct()
                .toList()
                .toArray(new String[]{});
    }

    /**
     * Get all the classes inside the topic.
     * @param username The name of the user who has provided their topics. If null, it will get the default topics.
     * @param topic The name of the topic to get the classes from.
     * @return {@link List} - Return a list of {@link EntityInformation} inside the topic.
     */
    public static List<EntityInformation> getListClassTopic(String username, @NonNull String topic) {
        return getListClassWorkJPATopic(username, topic).parallelStream().map(TopicService::mapClassToEntityInformation).toList();
    }

    /**
     * Get all the classes nature inside the topic
     * @param username The name of the user who has provided their topics. If null, it will get the default topics.
     * @param topic The name of the topic to get the classes from.
     * @return {@link List} - Return a list of {@link Class} inside the topic.
     */
    public static List<Class<?>> getListClassWorkJPATopic(String username, String topic) {
        AnnotatedElement[] listAnnotatedElements = new AnnotatedElement[]{
                Entity.class, Embeddable.class, MappedSuperclass.class
        };
        String packageScanning = Objects.isNull(username) ? PACKAGE_ENTITY_DEFAULT + "." + topic : PACKAGE_USERNAME_TOPIC.replace("{username}", username).replace("{topic}", topic);
        return new Reflections(packageScanning).get(Scanners.TypesAnnotated.with(listAnnotatedElements).asClass()).stream().toList();
    }

    public static List<Class<?>> getListClassNatureTopic(String username, String topic) {
        return null;
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
}
