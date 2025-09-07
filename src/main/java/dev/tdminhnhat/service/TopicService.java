package dev.tdminhnhat.service;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.NonNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.*;
import java.util.ArrayList;
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

    public static void getListClassTopic(String username, @NonNull String topic) {
        AnnotatedElement[] listAnnotatedElements = new AnnotatedElement[]{
                Entity.class, Embeddable.class, MappedSuperclass.class
        };
        if(Objects.isNull(username)) {
            List<Class<?>> listClasses = new Reflections("dev.tdminhnhat.entity.topics." + topic)
                    .get(Scanners.TypesAnnotated.with(listAnnotatedElements).asClass())
                    .stream()
                    .toList();
        } else {
            List<Class<?>> listClasses = new Reflections("dev.tdminhnhat.entity.users" + username + "." + topic)
                    .get(Scanners.TypesAnnotated.with(listAnnotatedElements).asClass())
                    .stream()
                    .toList();
        }
    }
}
