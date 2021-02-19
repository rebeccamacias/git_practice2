import java.io.File;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int HOW_OFTEN = 25;

        DynamicMedian<Integer> median = new DynamicMedian();
        int counter = 0;
        File file = new File(args[0]);
        DecimalFormat ft = new DecimalFormat("$###,###,###");
        try (Scanner wordDoc = new Scanner(file)) {
            wordDoc.nextLine();
            while (wordDoc.hasNextLine()) {
                String[] lineArray = wordDoc.nextLine().replaceAll("\\p{Punct}", "").strip().toLowerCase().split("\t");
                int salary = Integer.parseInt(lineArray[0]);
                median.addValue(salary);
                if (++counter % HOW_OFTEN == 0){
                    System.out.println("After " + counter + " salaries, median is: " + ft.format(median.getMedian()));
                }
            }
            System.out.println("After all salaries, median is: " + ft.format(median.getMedian()));
        } catch (java.io.IOException exception) {
            System.out.println("File unable to be read:" + exception);
        }
    }
}
