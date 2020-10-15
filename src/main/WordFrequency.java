import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        Scanner inputDevice = new Scanner(System.in);

        System.out.println("Please provide one or more text files you would like analyzed separated by a space. \n" +
                "The file should already exist in the textFiles folder. eg. theOriginOfSpecies.txt");
        handleUserInput(inputDevice.nextLine());
    }

    private static void handleUserInput(String input) {
        String[] fileList;

        fileList = input.trim().split("\\s+");
        for (String filePath : fileList) {
            Path path = Path.of("src/textFiles/" + filePath);
            doSomethingWithTheFile(path);
        }
    }

    private static void doSomethingWithTheFile(Path filePath) {
        try{
            HashMap<String, Integer> chunkOfThreeHash = new HashMap();
            ArrayList<String> wordsList = new ArrayList<>();
            BufferedReader reader = Files.newBufferedReader(filePath);
            String line;

            //read the file into an array list
            while ((line = reader.readLine()) != null){
                line = line.replaceAll("[^a-zA-Z0-9 ]", "");
                String[] words = line.trim().split(" ");
                for(String word: words){
                    wordsList.add(word);
                }
            }

            // pull the array list into a hash table grouped by three word pieces
            System.out.println("word list size: " + wordsList.size());
            for (int i = 1; i < wordsList.size()-1; i++) {
                String groupOfThreeWords = wordsList.get(i-1) + " " + wordsList.get(i) + " " + wordsList.get(i+1);
                if(chunkOfThreeHash.containsKey(groupOfThreeWords)){
                    int newCount = (int)chunkOfThreeHash.get(groupOfThreeWords) + 1;
                    chunkOfThreeHash.put(groupOfThreeWords,  newCount) ;
                }else{
                    chunkOfThreeHash.put(groupOfThreeWords, 1);
                }
            }

            chunkOfThreeHash = sortByValue(chunkOfThreeHash);
            System.out.println(chunkOfThreeHash);

        }catch(FileNotFoundException e){
            System.out.println("file not found!!! " +e);
        }catch (IOException e) {
            System.out.println("I/O Issue!!! " +e);
        }
    }

    private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        List<Map.Entry<String, Integer> > listDerivedFromHashMap = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        Collections.sort(listDerivedFromHashMap, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, Integer> sortedHashMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> listItem : listDerivedFromHashMap.subList(listDerivedFromHashMap.size()-100, listDerivedFromHashMap.size())) {
            sortedHashMap.put(listItem.getKey(), listItem.getValue());
        }
        return sortedHashMap;
    }
}