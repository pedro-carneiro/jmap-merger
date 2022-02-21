package com.pcarneiro.jmapmerger;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Consumer;

public class JMapConsumer implements Consumer<String> {

    private final Map<String, HistogramMergeEntry> map;
    private final ArgumentOrdinal ordinal;

    public JMapConsumer(Map<String, HistogramMergeEntry> map, ArgumentOrdinal ordinal) {
        this.map = map;
        this.ordinal = ordinal;
    }

    @Override
    public void accept(String s) {
        StringTokenizer tokenizer = new StringTokenizer(s);
        if (tokenizer.countTokens() >= 4) {
            try {
                tokenizer.nextToken();
                String rawInstances = tokenizer.nextToken();
                int instances = Integer.parseInt(rawInstances);

                String rawBytes = tokenizer.nextToken();
                long bytes = Long.parseLong(rawBytes);

                String objectType = tokenizer.nextToken();

                HistogramMergeEntry value = map.get(objectType);
                if (value == null) {
                    value = new HistogramMergeEntry();
                    value.setObjectType(objectType);

                    if (ordinal == ArgumentOrdinal.FIRST) {
                        value.setInstances(instances);
                        value.setBytes(bytes);
                    } else if (ordinal == ArgumentOrdinal.SECOND) {
                        value.setNextInstances(instances);
                        value.setNextBytes(bytes);
                    }
                } else {
                    value.setNextInstances(instances);
                    value.setNextBytes(bytes);
                }
                map.put(objectType, value);
            } catch (NumberFormatException e) {
                // ignore parse
            }
        }
    }
}
