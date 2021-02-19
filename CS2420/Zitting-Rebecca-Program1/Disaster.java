public class Disaster {
    public static void main(String[] args) {
        String[] files  = {"Tester.txt","Tester2.txt", "WesternUS.txt", "BritishIsles.txt", "NortheastUS.txt", "NortheastUS2.txt",
                "IberianPeninsula.txt", "CentralEurope.txt", "SouthernNigeria.txt", "SouthernSouthKorea.txt", "SouthernUS.txt"};
        Graph disaster;

        for (String file:files) {
            disaster = new Graph();
            disaster.makeGraph(file);

            SupplySolution finalSolution = new SupplySolution(disaster.cityCt, disaster); //sets up arrays, no supplies initially

            finalSolution.findSol();
            System.out.println(finalSolution.toString());
        }
    }
}
