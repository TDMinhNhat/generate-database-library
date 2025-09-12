# 📦 Generate Database Library

A lightweight Java library to help **generate and initialize database structures** programmatically — useful for rapid development, prototyping, or automating schema setups.

<div style="text-align: center;">

[![Maven Central](https://img.shields.io/maven-central/v/io.github.tdminhnhat/generate-database)](https://central.sonatype.com/artifact/io.github.tdminhnhat/generate-database)
[![javadoc](https://javadoc.io/badge2/io.github.tdminhnhat/generate-database/javadoc.svg)](https://javadoc.io/doc/io.github.tdminhnhat/generate-database)
[![License: MIT](https://img.shields.io/github/license/TDMinhNhat/generate-database-library)](https://github.com/TDMinhNhat/generate-database-library/blob/main/LICENSE)

</div>

# 🛠️ Installation

## With Maven

```xml
<dependency>
  <groupId>io.github.tdminhnhat</groupId>
  <artifactId>generate-database-library</artifactId>
  <version>1.0.0</version>
</dependency>
```

## With Gradle

### Gradle Kotlin
```groovy
implementation("io.github.tdminhnhat:generate-database:1.0.0")
```

### Gradle Groovy Short
```groovy
implementation 'io.github.tdminhnhat:generate-database:1.0.0'
```

### Gradle Groovy Long
```groovy
implementation group: 'io.github.tdminhnhat', name: 'generate-database', version: '1.0.0'
```

# 💡 Usage

## Interact with GUI

Sample the main function to work
```java
public static void main(String[] args) {
    GenerateDatabaseService.showGUI();
}
```

Or you can call show GUI later, put this one somewhere and remember call the function contain this:
```java
GenerateDatabaseService.showGUI();
```

## Interact with Code (Complex)

You have to work with both of them service. Every feature on this library in **_GenerateDatabaseService_** and this one needs some datas to work and you can find it in **_TopicService_**.
Remember all the functions are _static_, so you can call direct with **class name** instead of **initial**.

### TopicService:

1. Get all the default topics. These topics were created by the authors.
```java
String[] listTopics = TopicService().getListDefaultTopics();
```

2. Get all the users who have developed and create their topic in this library.
```java
String[] listUsers = TopicService().getListUsers();
```

3. Get all the topics from the author by point that username.
```java
String[] listTopicsByUser = TopicService().getListTopicsByUser(username);
```

4. Get all the classes which they're relating to work JPA Entity. In this function, the parameter 'username' that you can put **null value** and 'topic' must be existed value.
```java
List<Class<?>> listClasses = TopicService().getListClassWorkJPATopic(username, topic);
```

5. Get list classes information by username and topic.
```java
List<EntityInformation> listEntityInformations = TopicService().getListClassTopic(username, topic);
```

6. Get content class. The content include: class name, fields, functions,... like a source java code.
```java
String content = TopicService().getContentClass(packageName, className);
```

### GenerateDatabaseTopic:

1. Show the UI to the user can interact.
```java
GenerateDatabaseService.showGUI();
```

2. Test connect to database server
```java
//databaseInformation: DatabaseInformation
boolean result = GenerateDatabaseService.testConnection(databaseInformation);
```

3. Generate database, create tables, columns but make sure you have to connect to database successfully else it's throw exception.
```java
//databaseInformation: DatabaseInformation
//classes: List<Class<?>>
EntityManager em = GenerateDatabaseService.generateDatabase(databaseInformation, classes)
```

4. Export the source code java and save into your disk
```java
String message = GenerateDatabaseSerivce.exportClass(pathSave, packageScanning);
```

# 📄 Documentation
📖 **API Documentation**: [View Javadoc](https://javadoc.io/doc/io.github.tdminhnhat/generate-database)

# 💻 Develop library
Want to become a contribution in this project. Read carefully in this [document](DEVELOP_RULE.md).
