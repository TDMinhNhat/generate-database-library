package dev.tdminhnhat.service;

import dev.tdminhnhat.entity.EntityInformation;
import jakarta.persistence.*;
import lombok.NonNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TopicService {

    /**
     * Get all the default topics by this library. Not created from the users.
     * @return {@link String}[] - Return an array of the topics name.
     */
    public static String[] getListDefaultTopics() {
        return new Reflections("dev.tdminhnhat.entity.topics")
                .get(Scanners.TypesAnnotated.with(Entity.class).asClass())
                .stream()
                .map(Class::getPackageName)
                .map(packageName -> packageName.replace("dev.tdminhnhat.entity.topics.", ""))
                .distinct()
                .toList()
                .toArray(new String[]{});
    }

    /**
     * Get all the users name who have supported for writing their topics and share inside this library.
     * @return {@link String}[] - Return an array of the users name
     */
    public static String[] getListUsers() {
        return new Reflections("dev.tdminhnhat.entity.users")
                .get(Scanners.TypesAnnotated.with(Entity.class).asClass())
                .stream()
                .map(Class::getPackageName)
                .map(packageName -> packageName.replace("dev.tdminhnhat.entity.users.", "").split("\\.")[0])
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
        return new Reflections("dev.tdminhnhat.entity.users")
                .get(Scanners.TypesAnnotated.with(Entity.class).asClass())
                .stream()
                .map(Class::getPackageName)
                .map(packageName -> packageName.replace("dev.tdminhnhat.entity.users." + username + ".", ""))
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
        return getListClassNatureTopic(username, topic).parallelStream().map(TopicService::mapClassToEntityInformation).toList();
    }

    /**
     * Get all the classes nature inside the topic
     * @param username The name of the user who has provided their topics. If null, it will get the default topics.
     * @param topic The name of the topic to get the classes from.
     * @return {@link List} - Return a list of {@link Class} inside the topic.
     */
    public static List<Class<?>> getListClassNatureTopic(String username, String topic) {
        AnnotatedElement[] listAnnotatedElements = new AnnotatedElement[]{
                Entity.class, Embeddable.class, MappedSuperclass.class
        };
        String packageScanning = Objects.isNull(username) ? "dev.tdminhnhat.entity.topics." + topic : "dev.tdminhnhat.entity.users." + username + "." + topic;
        return new Reflections(packageScanning).get(Scanners.TypesAnnotated.with(listAnnotatedElements).asClass()).stream().toList();
    }

    private static boolean checkForeignAnnotation(Field field) {
        return field.getAnnotatedType().isAnnotationPresent(ManyToOne.class) ||
                field.getAnnotatedType().isAnnotationPresent(OneToOne.class);
    }

    private static EntityInformation mapClassToEntityInformation(Class<?> clazzItem) {
        return new EntityInformation(
                clazzItem.getName().split("\\.")[clazzItem.getName().split("\\.").length - 1],
                clazzItem.getSuperclass().getName().split("\\.")[clazzItem.getSuperclass().getName().split("\\.").length - 1],
                clazzItem.getPackageName(),
                Arrays.stream(clazzItem.getDeclaredFields()).count(),
                clazzItem.isAnnotationPresent(Entity.class),
                Arrays.stream(clazzItem.getFields()).filter(TopicService::checkForeignAnnotation)
                        .map(Field::getType)
                        .distinct()
                        .toList()
        );
    }
}
