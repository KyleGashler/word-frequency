import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WordFrequency {

    public static void main(String[] args) {
        ArrayList<String> wordsList = new ArrayList<>();
        HashMap<String, Integer> chunkOfThreeHash = new HashMap();
        Scanner inputDevice = new Scanner(System.in);
        String[] fileList;

        System.out.println("\n *** Please provide one or more text files you would like analyzed separated by a space. ***\n" +
                "The file should already exist in the textFiles folder. eg. theOriginOfSpecies.txt");

        fileList = handleUserInput(inputDevice.nextLine());
        for (String filePath : fileList) {
            Path path = Path.of("src/textFiles/" + filePath);
            // Get contents of a file into an array
            wordsList = textFileToTextOnlyArrayList(path, wordsList);
            // Use an array list to generate a hash map of the array batched into unique 3-word entries
            chunkOfThreeHash = arrayToHashMapOfGroupedThreeWordBatches(wordsList, chunkOfThreeHash);
            // Order the entries and limit to 100
            chunkOfThreeHash = sortHashMapByValue(chunkOfThreeHash);
            // print the top 100 entries
            printHashMapDetail(chunkOfThreeHash, path);
        }
    }

    public static String[] handleUserInput(String input) {
        return input.trim().split("\\s+");
    }

    public static void printHashMapDetail(HashMap<String, Integer> chunkOfThreeHash, Path filePath) {
        System.out.println("*** Here are the top 100 3-word occurrences For the File at path " + filePath + " ***");
        System.out.println(chunkOfThreeHash);
    }

    public static ArrayList<String> textFileToTextOnlyArrayList(Path filePath, ArrayList<String> wordsList) {
        String line;

        try {
            BufferedReader reader = Files.newBufferedReader(filePath);
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("[^a-zA-Z0-9 ]", "");
                String[] words = line.trim().split(" ");
                for (String word : words) {
                    if (word != " " | word != null) {
                        wordsList.add(word.toLowerCase());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println("The File you provided is not in the textFiles folder. Double Check that you spelled it correctly" + e);
        }
        return wordsList;
    }

    public static HashMap<String, Integer> arrayToHashMapOfGroupedThreeWordBatches(ArrayList<String> wordsList, HashMap<String, Integer> chunkOfThreeHash) {
        for (int i = 1; i < wordsList.size() - 1; i++) {
            String groupOfThreeWords = wordsList.get(i - 1) + " " + wordsList.get(i) + " " + wordsList.get(i + 1);
            if (chunkOfThreeHash.containsKey(groupOfThreeWords)) {
                int newCount = (int) chunkOfThreeHash.get(groupOfThreeWords) + 1;
                chunkOfThreeHash.put(groupOfThreeWords, newCount);
            } else {
                chunkOfThreeHash.put(groupOfThreeWords, 1);
            }
        }
        return chunkOfThreeHash;
    }

    public static HashMap<String, Integer> sortHashMapByValue(HashMap<String, Integer> hm) {
        List<Map.Entry<String, Integer>> listDerivedFromHashMap = new LinkedList<>(hm.entrySet());

        Collections.sort(listDerivedFromHashMap, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, Integer> sortedHashMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> listItem : listDerivedFromHashMap.subList(listDerivedFromHashMap.size() - 100, listDerivedFromHashMap.size())) {
            sortedHashMap.put(listItem.getKey(), listItem.getValue());
        }
        return sortedHashMap;
    }
}
