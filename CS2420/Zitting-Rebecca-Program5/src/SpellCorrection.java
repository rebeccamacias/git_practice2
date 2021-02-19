import java.io.File;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

public class SpellCorrection {
    public static void main(String[] args) {
        File file = new File("dictionary.txt");
        ArrayList<String> dictionary = new ArrayList<>();
        try (Scanner wordDoc = new Scanner(file)) {
            while (wordDoc.hasNextLine()) {
                String word = wordDoc.nextLine().toLowerCase();
                dictionary.add(word);
            }
        }
        catch(java.io.IOException exception){
            System.out.println("File unable to be read:" + exception);
        }
        CuckooHash<String> dictionaryHash = makeHash(dictionary);
        for (String called : args){
            File paragraph = new File(called);

            System.out.println("File: " + file + " Misspelled words:");
            spellCorrection(dictionaryHash, paragraph, dictionary);
            System.out.println("The dictionary statistics were: ");
            System.out.println("table 1: (" + dictionaryHash.occupiedCt1 + "/" + dictionaryHash.firstArraySize + ") = " + dictionaryHash.occupiedCt1 / (dictionaryHash.firstArraySize * 1.0));
            System.out.println("table 2: (" + dictionaryHash.occupiedCt2 + "/" + dictionaryHash.secondArraySize + ") = " + dictionaryHash.occupiedCt1 / (dictionaryHash.secondArraySize * 1.0));
            System.out.println();
        }
    }

    public static CuckooHash<String> makeHash(ArrayList<String> dictionary) {
        CuckooHash<String> dictionaryHash = new CuckooHash<>();
        for (int i = 0; i < dictionary.size(); i++){
            dictionaryHash.insert(dictionary.get(i));
        }
        return dictionaryHash;
    }

    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        //System.out.print( "minDistance " + word1 + " " + word2 + ": ");
        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int delete= dp[i][j + 1] + 1;
                    int insert = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }
//        System.out.println(dp[len1][len2]);
        return dp[len1][len2];
    }

    public static void spellCorrection(CuckooHash<String> dictionaryHash, File file, ArrayList<String> dictionary){
        ArrayList<String> misspelledWords = new ArrayList<>();
        CuckooHash<WordFreq> misHash = new CuckooHash<>();
        int misspelledWordsCt = 0;
        try (Scanner checked = new Scanner(file)){
            String[] checkedWords;
            while(checked.hasNextLine()){
                checkedWords = checked.nextLine().replaceAll("\\p{Punct}", "").strip().toLowerCase().split(" ");
                for (String word : checkedWords) {
                    if (word.equals("")){
                        continue;
                    }
                    if (!dictionaryHash.contains(word)){
                        WordFreq addWord = new WordFreq(word);
                        if (misHash.contains(addWord)){ //if the node is in the tree
                            misHash.find(addWord).addFrequency();
                        } else {
                            misHash.insert(addWord);
                            misspelledWordsCt++;
                            System.out.println("Misspelled Word: " + word + ":");
                        }
                        if (!misspelledWords.contains(word)){
                            misspelledWords.add(word);
                        }
                        for (String dictWord : dictionary){
                            int dist = minDistance(word, dictWord);
                            if (dist <= 2){
                                System.out.print(dictWord + "(" + dist + ")" + " ");
                            }
                        }
                        System.out.println();
                    }
                }
            }
        }
        catch(java.io.IOException exception){
            System.out.println("File could not be read");
        }
        System.out.println(misHash.toString(misspelledWordsCt));
        System.out.println("insertCount: " + misHash.numOfInserts + " probe count + " + misHash.insertsCalled + " probes/insert " + (misHash.numOfInserts / misHash.insertsCalled) + " rehash count " + misHash.rehashCt);
    }
}
