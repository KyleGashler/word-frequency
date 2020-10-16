import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyTest {
    WordFrequency wordFrequency = new WordFrequency();

    @Test
    void handleUserInput() {
        String[] retValSingle = wordFrequency.handleUserInput("test.txt");
        String[] expectedSingle = {"test.txt"};

        String[] retValMultiple = wordFrequency.handleUserInput("bible.txt iliad.txt");
        String[] expectedMultiple = {"bible.txt", "iliad.txt"};
        // given two files, two entries in an array are returned

        assertArrayEquals(retValSingle, expectedSingle);
        assertArrayEquals(retValMultiple, expectedMultiple);
        // given one file, one entry is returned
    }

    @Test
    void textFileToTextOnlyArrayList() {
        Path testPath = Path.of("/Users/kylegashler/repos/word-frequency/src/textFiles/test.txt");
        ArrayList<String> wordsList = new ArrayList<>();

        ArrayList<String> expectedWordsList = new ArrayList<>();
        expectedWordsList.add("this");
        expectedWordsList.add("is");
        expectedWordsList.add("my");
        expectedWordsList.add("test");
        expectedWordsList.add("file");

        wordsList = wordFrequency.textFileToTextOnlyArrayList(testPath, wordsList);

        assertLinesMatch(expectedWordsList, wordsList);
    }

    @Test
    void arrayToHashMapOfGroupedThreeWordBatches() {
        ArrayList<String> wordsList = new ArrayList<>();
        HashMap<String, Integer> testHashMap = new HashMap();
        HashMap<String, Integer> expected = new HashMap();

        wordsList.add("this");
        wordsList.add("is");
        wordsList.add("my");
        wordsList.add("this");
        wordsList.add("is");
        wordsList.add("my");
        wordsList.add("my");

        expected.put("this is my", 2);
        expected.put("is my this", 1);
        expected.put("my this is", 1);
        expected.put("is my my", 1);

        testHashMap = wordFrequency.arrayToHashMapOfGroupedThreeWordBatches(wordsList, testHashMap);

        assertEquals(expected, testHashMap);
    }

    @Test
    void sortHashMapByValue() {
        HashMap<String, Integer> testHashMap = new HashMap();
        LinkedHashMap<String, Integer> expected = new LinkedHashMap();

        testHashMap.put("is my this", 3);
        testHashMap.put("this is my", 2);
        testHashMap.put("my this is", 1);
        testHashMap.put("is my my", 4);

        expected.put("my this is", 1);
        expected.put("this is my", 2);
        expected.put("is my this", 3);
        expected.put("is my my", 4);

        testHashMap = wordFrequency.sortHashMapByValue(testHashMap);

        //turn hash sets into arrays to compare at each index
        List<Map.Entry<String, Integer>> listDerivedFromTest = new ArrayList<>(testHashMap.entrySet());
        List<Map.Entry<String, Integer>> listDerivedFromexpected = new ArrayList<>(expected.entrySet());

        for (int i = 0; i < listDerivedFromTest.size(); i++) {
            assertEquals(listDerivedFromTest.get(i), listDerivedFromexpected.get(i));
        }

    }
}