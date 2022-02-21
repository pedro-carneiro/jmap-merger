package com.pcarneiro.jmapmerger;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.testng.Assert.*;

public class JMapConsumerTest {

    @Test(dataProvider = "parseFails")
    public void verifyParseFails(String s) {
        Map<String, HistogramMergeEntry> entries = new HashMap<>();
        Consumer<String> subject = new JMapConsumer(entries, ArgumentOrdinal.FIRST);
        subject.accept(s);
        assertTrue(entries.isEmpty());
    }

    @Test
    public void verifyParseSucceeds() {
        String objectType = "sun.text.normalizer.NormalizerBase$NFKCMode";
        String s = "7297:             1             16  " + objectType + "  (java.base@13.0.2)";
        Map<String, HistogramMergeEntry> entries = new HashMap<>();
        Consumer<String> subject = new JMapConsumer(entries, ArgumentOrdinal.FIRST);

        subject.accept(s);

        HistogramMergeEntry entry = entries.get(objectType);
        assertNotNull(entry);
        assertEquals(entry.getInstances().intValue(), 1);
        assertEquals(entry.getBytes().longValue(), 16L);
        assertEquals(entry.getObjectType(), objectType);
    }

    @Test
    public void verifyNextParse() {
        String objectType = "sun.text.normalizer.NormalizerBase$NFKCMode";
        Map<String, HistogramMergeEntry> entries = new HashMap<>();
        Consumer<String> subject = new JMapConsumer(entries, ArgumentOrdinal.FIRST);

        subject.accept("7297:             1             16  " + objectType + "  (java.base@13.0.2)");
        subject.accept("7297:             2             32  " + objectType + "  (java.base@13.0.2)");

        HistogramMergeEntry entry = entries.get(objectType);
        assertNotNull(entry);
        assertEquals(entry.getInstances().intValue(), 1);
        assertEquals(entry.getNextInstances().intValue(), 2);
        assertEquals(entry.getBytes().longValue(), 16L);
        assertEquals(entry.getNextBytes().longValue(), 32L);
        assertEquals(entry.getObjectType(), objectType);
    }

    @DataProvider
    public Object[][] parseFails() {
        return new Object[][]{
                {"No dump file specified"},
                {"num     #instances         #bytes  class name (module)"},
                {"-------------------------------------------------------"},
                {"Total        772625       44996384"},
        };
    }
}
