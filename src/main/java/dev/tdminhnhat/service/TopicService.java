package dev.tdminhnhat.service;

import jakarta.persistence.Entity;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.*;

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
}
