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
	    <version>1.0.6</version>
	</dependency>
