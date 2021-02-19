import java.io.File;
import java.util.Scanner;

public class Rivals {
    Integer[] opponentsArray;
    UnionFind votersArray;

    public Rivals(File file) {
        try (Scanner report = new Scanner(file)){
            int numOfVoters = Integer.parseInt(report.nextLine().strip());
            votersArray = new UnionFind(numOfVoters);
            opponentsArray = new Integer[numOfVoters];
            while(report.hasNextLine()){ //TODO: remember that the numbers need to be -- when manipulated in array
                String[] attackArray = report.nextLine().replaceAll("\\p{Punct}", "").strip().split(" ");
                if (attackArray[0].equals("")){
                    continue;
                }
                int person1 = Integer.parseInt(attackArray[0]) - 1;
                int person2 = Integer.parseInt(attackArray[attackArray.length - 1]) - 1;
                attack(person1, person2);
            }
        }
        catch(java.io.IOException exception){
            System.out.println("File could not be read!");
        }
        int max = 1;
        for (int i = 0; i < opponentsArray.length; i++){
            if (votersArray.voters[i] < 0){
                System.out.println("Group " + (i + 1) + " has " + (votersArray.voters[i] / (-1)) + " members");
                if (votersArray.voters[i] / (-1) > max){
                    max = (votersArray.voters[i] / (-1));
                }
            }
        }
        System.out.println("Largest group is of size " + max);
    }

    /**
     * @author Rebecca Zitting
     * Method records an attack of person1 on person2,
     * marks they are opponents, and adjusts arrays
     * accordingly.
     * @param person1 attacker
     * @param person2 attackee
     */
    public void attack(int person1, int person2){
        System.out.println("Attack " + (person1 + 1) + " " + (person2 + 1));
        if(opponentsArray[person1] == null && opponentsArray[person2] == null){
            opponentsArray[person1] = person2;
            opponentsArray[person2] = person1;
            return;
        }
        if (opponentsArray[person1] == opponentsArray[person2]){
            System.out.println("Ignored attack " + (person1 + 1) + " " + (person2 +1));
            return;
        }
        if (opponentsArray[person1] == null){
            votersArray.union(opponentsArray[person2], person1);
            opponentsArray[person1] = opponentsArray[votersArray.find(person1)];
            return;
        } else if (opponentsArray[person2] == null) {
            votersArray.union(opponentsArray[person1], person2);
            opponentsArray[person2] = opponentsArray[votersArray.find(person2)];
            return;
        } else {
            votersArray.union(person1, opponentsArray[person2]);
            votersArray.union(person2, opponentsArray[person1]);
            opponentsArray[person1] = opponentsArray[votersArray.find(person1)];
            opponentsArray[person2] = opponentsArray[votersArray.find(person2)];
        }
    }
}
