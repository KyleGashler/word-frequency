import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WordFrequency {

    public static void main(String[] args) {
        WordFrequency wordFrequency = new WordFrequency();
        ArrayList<String> wordsList = new ArrayList<>();
        HashMap<String, Integer> chunkOfThreeHash = new HashMap();
        Scanner inputDevice = new Scanner(System.in);
        String[] fileList;
        Path path;

        System.out.println("\n *** Please provide one or more text files you would like analyzed separated by a space. ***\n" +
                "The Path Provided should be the absolute path on your file system, or it should exist in the textFiles folder in this project \n" +
                "eg. Users/YourUser/repos/word-frequency/src/textFiles/iliad.txt");

        fileList = wordFrequency.handleUserInput(inputDevice.nextLine());
        for (String filePath : fileList) {
            if (filePath.indexOf('/') > 0) {
                path = Path.of(filePath);
            } else {
                path = Path.of("src/textFiles/" + filePath);
            }
            // Get contents of a file into an array
            wordsList = wordFrequency.textFileToTextOnlyArrayList(path, wordsList);
            // Use an array list to generate a hash map of the array batched into unique 3-word entries
            chunkOfThreeHash = wordFrequency.arrayToHashMapOfGroupedThreeWordBatches(wordsList, chunkOfThreeHash);
            // Order the entries and limit to 100
            chunkOfThreeHash = wordFrequency.sortHashMapByValue(chunkOfThreeHash);
            // print the top 100 entries
            wordFrequency.printHashMapDetail(chunkOfThreeHash, path);
        }
    }

    public String[] handleUserInput(String input) {
        return input.trim().split("\\s+");
    } // This could be a problem on an OS that allows spaces in a file name.

    public void printHashMapDetail(HashMap<String, Integer> chunkOfThreeHash, Path filePath) {
        System.out.println("*** Here are the top 100 3-word occurrences For the File at path " + filePath + " ***");
        System.out.println(chunkOfThreeHash);
    }

    public ArrayList<String> textFileToTextOnlyArrayList(Path filePath, ArrayList<String> wordsList) {
        String line;

        try {
            BufferedReader reader = Files.newBufferedReader(filePath);
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", " ").trim();
                String[] words = line.split(" ");
                for (String word : words) {
                    if (word.length() > 0) {
                        wordsList.add(word);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println("The File you provided was not found in your file system. Double Check that you spelled it correctly and " +
                    "and provide the absolute path" + e);
        }
        return wordsList;
    }

    public HashMap<String, Integer> arrayToHashMapOfGroupedThreeWordBatches(ArrayList<String> wordsList, HashMap<String, Integer> chunkOfThreeHash) {
        for (int i = 1; i < wordsList.size() - 1; i++) {
            String groupOfThreeWords = wordsList.get(i - 1) + " " + wordsList.get(i) + " " + wordsList.get(i + 1);
            if (chunkOfThreeHash.containsKey(groupOfThreeWords)) {
                int newCount = chunkOfThreeHash.get(groupOfThreeWords) + 1;
                chunkOfThreeHash.put(groupOfThreeWords, newCount);
            } else {
                chunkOfThreeHash.put(groupOfThreeWords, 1);
            }
        }
        return chunkOfThreeHash;
    }

    public LinkedHashMap<String, Integer> sortHashMapByValue(HashMap<String, Integer> hm) {
        List<Map.Entry<String, Integer>> listDerivedFromHashMap = new ArrayList<>(hm.entrySet());

        Collections.sort(listDerivedFromHashMap, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        LinkedHashMap<String, Integer> sortedHashMap = new LinkedHashMap<String, Integer>();
        //truncating the list down to the top 100 if there are at least 100 records
        if (listDerivedFromHashMap.size() > 100) {
            listDerivedFromHashMap = listDerivedFromHashMap.subList(listDerivedFromHashMap.size() - 100, listDerivedFromHashMap.size());
        }
        for (Map.Entry<String, Integer> listItem : listDerivedFromHashMap) {
            sortedHashMap.put(listItem.getKey(), listItem.getValue());
        }
        return sortedHashMap;
    }
}
