import java.io.File;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++){
            File file = new File(args[i]);
            System.out.println(file);
            Rivals attacks = new Rivals(file);
            System.out.println();
        }
    }
}
