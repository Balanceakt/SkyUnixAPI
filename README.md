# SkyUnix Data API

Die SkyUnix Data Management API ist eine Java-Bibliothek, die entwickelt wurde, um die effiziente Verwaltung und Speicherung von Daten für das SkyUnix.de-Netzwerk zu erleichtern. Diese API ersetzt herkömmliche MySQL-Datenbanken und Standardkonfigurationsdateien durch benutzerdefinierte Lösungen und bietet Entwicklern eine unkomplizierte Schnittstelle für Aufgaben im Bereich Datenmanagement.

# Installation
## Gradle
````
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.Balanceakt:DBCenter:Tag'
}
````
## Maven
````
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.Balanceakt</groupId>
    <artifactId>SkyUnixAPIBungeecord</artifactId>
<<<<<<< HEAD
    <version>1.3.5</version>
=======
    <version>1.3.5</version>
>>>>>>> 9a58d8b17b474d899173e03dc912de5263dcbb6a
</dependency>
````

# Java Beispielcode:

## SkyUnixAPI Instance
Static method to return the singleton instance of the class:
````java
SkyUnixAPI.getInstance()
````

## File operations
```java
// Create an instance of SkyUnixHandleDelete for simple file deletion operations
SkyUnixHandleDelete simpleDelete = SkyUnixAPI.getInstance().deleteInstance();
```
```java
// Create an instance of SkyUnixHandleLocation for simple file location operations
SkyUnixHandleLocation simpleLocation = SkyUnixAPI.getInstance().locationInstance();
```
```java
// Create an instance of SkyUnixHandleArgs for simple file argument handling operations
SkyUnixHandleArgs simpleReadArgs = SkyUnixAPI.getInstance().argsInstance();
```
```java
// Create an instance of SkyUnixHandleUpdate for simple file update operations
SkyUnixHandleUpdate simplUpdateArgs = SkyUnixAPI.getInstance().updateInstance();
```
```java
// Create an instance of SkyUnixHandleNullCheck for simple null check operations
SkyUnixHandleNullCheck simpleNullCheck = SkyUnixAPI.getInstance().nullCheckInstance();
```
```java
// Create an instance of SkyUnixHandlePlaceholder for simple placeholder operations
SkyUnixHandlePlaceholder simplePlaceHolder = SkyUnixAPI.getInstance().placeholderInstance();
```

-----------------

# General Doku

## SkyUnixHandleArgs

**readSimpleArgs:** This method reads a simple value from a specified table and key in a file. You can also specify an index to retrieve a specific value from a comma-separated list of values.

**setSimpleArgsValue:** Here you can assign a list of values to a key and store this list as comma-separated values in the file.

**readColorCodes:** This method reads color codes from a file and converts them by replacing the '&' character with the '§' character.

**readTableCountKeys:** Counts the number of stored keys in a table.

**readTableKeys:** *no description*

**readAllArgsAtIndex:** Reads all values from all keys in a table and returns a list of values at a specific index.

## SkyUnixHandleDelete

**deleteArgValue:** This method deletes a specific value at the given index associated with a key in a table within a file. It loads the existing properties, removes the value at the specified index from the list of values associated with the key, and updates the file with the modified properties.

**deleteKey:** This method removes an entire key and its associated values from a table within a file. It loads the existing properties, removes the specified key, and updates the file with the modified properties.

**deleteTable:** This method deletes an entire table (file) from the specified folder. It checks if the file exists and deletes it if possible.

**deleteFolder:** This method recursively deletes a folder and all its contents. It first checks if the folder exists and if it is a directory, then proceeds to delete all its contents before deleting the folder itself.

## SkyUnixHandleLocation

**saveLocation:** This method saves a location object to a file under a specified folder and table. It stores the world name, coordinates, yaw, and pitch values associated with a given key in the properties file.

**loadLocation:** This method loads a location object from a file based on the specified folder, table, and key. It retrieves the world name, coordinates, yaw, and pitch values from the properties file and constructs a Location object.

**getWorldForLocation:** This method retrieves the World object associated with a location stored in a file based on the specified folder, table, and key. It returns the World object based on the world name stored in the properties file.

**getCoordinates:** This method retrieves the coordinates (x, y, z) associated with a location stored in a file based on the specified folder, table, and key. It returns an array of doubles containing the x, y, and z coordinates.

## SkyUnixNullCheck

**isFolderExists:** This method checks if a specified folder exists in the directory structure. It returns true if the folder exists and is a directory; otherwise, it returns false.

**isTableExists:** This method checks if a specified table (file) exists within a folder in the directory structure. It returns true if both the folder and the table exist; otherwise, it returns false.

**isKeyExists:** This method checks if a specified key exists within a table (file) in the directory structure. It loads the properties from the file and checks if the key exists. It returns true if the key exists; otherwise, it returns false.

**isValueExists:** This method checks if a specified value exists at a given index within a key's list of values in a table (file) within the directory structure. It loads the properties from the file, splits the values associated with the key, and checks if the value at the specified index exists. It returns true if the value exists; otherwise, it returns false.

**isValueExistsInTable:** This method checks if a specified value exists within any key-value pair in a table (file) within the directory structure. It loads the properties from the file and iterates through each key-value pair, checking if the value contains the specified value to check. It returns true if the value exists in any key-value pair; otherwise, it returns false.

## SkyUnixHandlePlaceholder

**readMessageWithPlaceholders:** This method reads a message with placeholders from a specified key in a table within a file. It allows you to specify an index to retrieve a specific message from a comma-separated list of messages. It then replaces the placeholders in the retrieved message with the provided values and returns the resulting message. If the folder or table does not exist, or if the key is not found, it returns null.

**replacePlaceholders:** This private helper method is used internally by readMessageWithPlaceholders to replace placeholders in a message with provided values. It iterates through the provided replacements array, which contains pairs of placeholders and their corresponding values, and replaces each placeholder in the message with its corresponding value.

## SkyUnixHandleUpdate

**replaceArgValue:** This method replaces a value at a specified index within a list of values associated with a key in a table file. It loads the properties from the file and retrieves the list of values associated with the specified key. If the key exists, it replaces the value at the specified index with the provided new value. If the key doesn't exist or the index is out of range, appropriate error messages are printed. Finally, it stores the updated properties back to the file.

## SkyUnixHandleWorldBlock

**findBlockKeyByLocation:** *no description*

**getAllBlockLocations:** *no description*

**setBlockByLocation:** *no description*

**saveBlock:** *no description*

**getBlockDirections:** *no description*

**getBlockLocations:** *no description*

**loadBlocks:** *no description*

**getBlockDataList:** *no description*

**getBlockDataList:** *no description*

**deleteBlocks:** *no description*
