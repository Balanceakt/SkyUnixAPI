# SkyUnix Data API

Die SkyUnix Data Management API ist eine Java-Bibliothek, die entwickelt wurde, um die effiziente Verwaltung und Speicherung von Daten für das SkyUnix.de-Netzwerk zu erleichtern. Diese API ersetzt herkömmliche MySQL-Datenbanken und Standardkonfigurationsdateien durch benutzerdefinierte Lösungen und bietet Entwicklern eine unkomplizierte Schnittstelle für Aufgaben im Bereich Datenmanagement.

## Java Beispielcode:

```java
        SkyUnixHandleDelete simpleDelete = new SkyUnixHandleDelete();
        SkyUnixHandleLocation simpleLocation = new SkyUnixHandleLocation();
        SkyUnixHandleArgs simpleReadArgs = new SkyUnixHandleArgs();
        SkyUnixHandleUpdate simplUpdateArgs = new SkyUnixHandleUpdate();
        SkyUnixHandleNullCheck simpleNullCheck = new SkyUnixHandleNullCheck();
        SkyUnixHandlePlaceholder simplePlaceHolder = new SkyUnixHandlePlaceholder();


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


<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

	<dependency>
	    <groupId>com.github.Balanceakt</groupId>
	    <artifactId>SkyUnixAPIBungeecord</artifactId>
	    <version>1.2.8</version>
	</dependency>

readSimpleArgs: This method reads a simple value from a specified table and key in a file. You can also specify an index to retrieve a specific value from a comma-separated list of values.

setSimpleArgValue: This method allows you to assign a simple value to a key in a table and save it in the corresponding file.

setSimpleArgsValues: Similar to setSimpleArgValue, but here you can assign a list of values to a key and store this list as comma-separated values in the file.

readColorCodes: This method reads color codes from a file and converts them by replacing the '&' character with the '§' character.

readAllArgsAtIndex: Reads all values from all keys in a table and returns a list of values at a specific index.

readTableCountKeys: Counts the number of stored keys in a table.

deleteArgValue: This method deletes a specific value at the given index associated with a key in a table within a file. It loads the existing properties, removes the value at the specified index from the list of values associated with the key, and updates the file with the modified properties.

deleteKey: This method removes an entire key and its associated values from a table within a file. It loads the existing properties, removes the specified key, and updates the file with the modified properties.

deleteTable: This method deletes an entire table (file) from the specified folder. It checks if the file exists and deletes it if possible.

deleteFolder: This method recursively deletes a folder and all its contents. It first checks if the folder exists and if it is a directory, then proceeds to delete all its contents before deleting the folder itself.

saveLocation: This method saves a location object to a file under a specified folder and table. It stores the world name, coordinates, yaw, and pitch values associated with a given key in the properties file.

loadLocation: This method loads a location object from a file based on the specified folder, table, and key. It retrieves the world name, coordinates, yaw, and pitch values from the properties file and constructs a Location object.

getWorldForLocation: This method retrieves the World object associated with a location stored in a file based on the specified folder, table, and key. It returns the World object based on the world name stored in the properties file.

getCoordinates: This method retrieves the coordinates (x, y, z) associated with a location stored in a file based on the specified folder, table, and key. It returns an array of doubles containing the x, y, and z coordinates.

isFolderExists: This method checks if a specified folder exists in the directory structure. It returns true if the folder exists and is a directory; otherwise, it returns false.

isTableExists: This method checks if a specified table (file) exists within a folder in the directory structure. It returns true if both the folder and the table exist; otherwise, it returns false.

isKeyExists: This method checks if a specified key exists within a table (file) in the directory structure. It loads the properties from the file and checks if the key exists. It returns true if the key exists; otherwise, it returns false.

isValueExists: This method checks if a specified value exists at a given index within a key's list of values in a table (file) within the directory structure. It loads the properties from the file, splits the values associated with the key, and checks if the value at the specified index exists. It returns true if the value exists; otherwise, it returns false.

isValueExistsInTable: This method checks if a specified value exists within any key-value pair in a table (file) within the directory structure. It loads the properties from the file and iterates through each key-value pair, checking if the value contains the specified value to check. It returns true if the value exists in any key-value pair; otherwise, it returns false.

readMessageWithPlaceholders: This method reads a message with placeholders from a specified key in a table within a file. It allows you to specify an index to retrieve a specific message from a comma-separated list of messages. It then replaces the placeholders in the retrieved message with the provided values and returns the resulting message. If the folder or table does not exist, or if the key is not found, it returns null.

replacePlaceholders: This private helper method is used internally by readMessageWithPlaceholders to replace placeholders in a message with provided values. It iterates through the provided replacements array, which contains pairs of placeholders and their corresponding values, and replaces each placeholder in the message with its corresponding value.

replaceArgValue: This method replaces a value at a specified index within a list of values associated with a key in a table file. It loads the properties from the file and retrieves the list of values associated with the specified key. If the key exists, it replaces the value at the specified index with the provided new value. If the key doesn't exist or the index is out of range, appropriate error messages are printed. Finally, it stores the updated properties back to the file.
