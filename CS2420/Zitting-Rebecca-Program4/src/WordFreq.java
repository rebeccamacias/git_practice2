public class WordFreq implements Comparable<WordFreq>{
    private String word;
    private int frequency = 1;

    public WordFreq(String testerWord){
        this.word = testerWord;
    }

    public int getFrequency(){
        return frequency;
    }
    public String getWord(){
        return word;
    }
    public String toString(){
        return (":" + word + ": " + frequency);
    }

    @Override
    public int compareTo(WordFreq wFObj) { return this.word.compareTo(wFObj.word); }
    public void addFrequency(){
        this.frequency++;
    }
}
