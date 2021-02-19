public class Disaster {
    public static void main(String[] args) {
        String[] files  = {"Tester.txt","Tester2.txt", "WesternUS.txt", "BritishIsles.txt", "NortheastUS.txt",
                "CentralEurope.txt", "IberianPeninsula.txt", "SouthernNigeria.txt","SouthernSouthKorea.txt", "NortheastUS2.txt", "SouthernUS.txt"};
//        String[] files = {"BritishIsles.txt"};
        Graph disaster;
        double totalRunTime = 0;

        for (String file:files) {
            disaster = new Graph();
            disaster.makeGraph(file);

            SupplySolution partialSolution = new SupplySolution(disaster.cityCt, disaster);
            SupplySolution testSol = new SupplySolution(partialSolution);

            partialSolution.findSol();

            double startOptimizedTime = System.currentTimeMillis();
            SupplySolution finalSol = testSol.getSupply(0, disaster.cityCt, testSol, partialSolution.supplyNodeCount );
            double endOptimizedTime = System.currentTimeMillis();
            double optimizedTime = (endOptimizedTime - startOptimizedTime)/1000;
            totalRunTime += optimizedTime;
            System.out.println("OPTIMIZED:");
            System.out.println(finalSol.toString());
            System.out.println("GREEDY:");
            System.out.println(partialSolution.toString());

            System.out.println("Total time for Optimized Solution was " + optimizedTime + " seconds.");

        }
        System.out.println();
        System.out.println("Total run time: " + totalRunTime/(60) + " minutes.");
    }
}
