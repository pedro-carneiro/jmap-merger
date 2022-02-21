package com.pcarneiro.jmapmerger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class JMapMerger {

    public static void main(String[] args) throws IOException {
        if (args == null || args.length != 2) {
            System.err.println("Usage: file1 file2");
            System.exit(1);
        }

        Map<String, HistogramMergeEntry> entries = new HashMap<>();

        readFile(args[0], new JMapConsumer(entries, ArgumentOrdinal.FIRST));
        readFile(args[1], new JMapConsumer(entries, ArgumentOrdinal.SECOND));

        for (HistogramMergeEntry value : entries.values()) {
            System.out.println(
                    value.getObjectType() +
                            "," +
                            toPrint(value.getInstances()) +
                            "," +
                            toPrint(value.getBytes()) +
                            "," +
                            toPrint(value.getNextInstances()) +
                            "," +
                            toPrint(value.getNextBytes())
            );
        }
    }

    private static void readFile(String file, Consumer<String> consumer) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                consumer.accept(line);
            }
        }
    }

    private static String toPrint(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }
}
