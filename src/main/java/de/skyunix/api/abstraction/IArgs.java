package de.skyunix.api.abstraction;

import java.util.List;
import java.util.Set;

public interface IArgs {
    String readSimpleArgs(final String folder, final String table, final String key, int argIndex);

    default String readSimpleArgs(final String folder, final String table, final String key) {
        return readSimpleArgs(folder, table, key, 0);
    }

    void setSimpleArgValue(final String folder, final String table, final String key, final String... value);
    String readColorCodes(final String folder, final String table, final String key, int argIndex);
    List<String> readAllArgsAtIndex(final String folder, final String table, int argIndex);
    int readTableCountKeys(final String folder, final String table);
    Set<String> readTableKeys(final String folder, final String table);
}
