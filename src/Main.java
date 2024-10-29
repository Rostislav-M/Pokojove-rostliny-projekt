import java.time.LocalDate;


public class Main {
    private static final String FILENAME="resources/kvetiny.txt";
    private static final String OUTPUT_FILENAME ="resources/kvetiny-output.txt";
    private static final String DELIMETER="\t";

    public static void main(String[] args) {

        try{
            PlantListManager plantListManager = new PlantListManager();

            //Vyzkoušej, že aplikace správně zareportuje chybu při načtení vadných souborů:
            //loadFile(plantListManager,"resources/kvetiny-spatne-datum.txt");
            //loadFile(plantListManager,"resources/kvetiny-spatne-frekvence.txt");

            //Oveřování funkčnosti aplikace:

            //1. Načti seznam květin ze souboru kvetiny.txt.
            loadFile(plantListManager,FILENAME);

            //2. Vypiš na obrazovku informace o zálivce pro všechny květiny ze seznamu.
            System.out.println("bod č.2 Vypiš na obrazovku informace o zálivce pro všechny květiny ze seznamu: ");
            plantListManager.printPlantList();

            //3. Přidej novou květinu do seznamu (údaje si vymysli).
            plantListManager.addPlant(new Plant("Aloe vera", "léčivá rostlina", LocalDate.of(2022,5,1),LocalDate.of(2022, 6,2),7));

            //4. Přidej 10 rostlin s popisem „Tulipán na prodej 1“
            // až „Tulipán na prodej 10“. Zasazeny byly dnes, zality také,
            // frekvence zálivky je 14 dnů.
            for(int i=1; i<=10;i++){
                char letter = (char) ('A' + i-1);
                plantListManager.addPlant(new Plant("Tulipán"+letter,"Tulipán na prodej "+i,LocalDate.now(),LocalDate.now(),14));
                }

            //5.Květinu na třetí pozici odeber ze seznamu (prodali jsme ji).
            System.out.println("\n" + "Prodaná a odebrána rostlina ze seznamu je: " +plantListManager.getPlantOnIndex(2));
            plantListManager.removePlant(plantListManager.getPlantOnIndex(2));

            //6. Ulož seznam květin do nového souboru a prověř, že je jeho obsah odpovídá provedeným změnám.
            saveFile(plantListManager, OUTPUT_FILENAME);

            // 7. Vyzkoušej opětovné načtení vygenerovaného souboru
            loadFile(plantListManager, OUTPUT_FILENAME);
            System.out.println("\n" + "bod č.7 Výpis opětovného načtení vygenerovaného souboru: ");
            plantListManager.printPlantList();

            //8. Vyzkoušej seřazení rostlin ve správci seznamu podle různých kritérií a výpis seřazeného seznamu.
            System.out.println("\n" + "bod č.8 Vyzkoušej seřazení rostlin ve správci seznamu podle různých kritérií a výpis seřazeného seznamu: ");
            plantListManager.sortPlants();
            System.out.println("\n" + "Setřízené podle jména: ");
            plantListManager.printPlantList();

            System.out.println("\n" + "Setřízené podle datumu WateringDate(datumu poslední zálivky): ");
            plantListManager.sortPlantsByWateringDate();
            plantListManager.printPlantList();

            System.out.println("\n" + "Setřízené podle jména a pak podle WateringDate(datumu poslední zálivky): ");
            plantListManager.sortByNameThenByWateringDate();
            plantListManager.printPlantList();

        }catch(PlantException e) {
               System.err.println(e.getMessage());
        }

    }

        private static void saveFile(PlantListManager plantListManager, String filename) throws PlantException {
            plantListManager.saveToFile(filename, DELIMETER);

        }
        private static void loadFile(PlantListManager plantListManager, String filename) throws PlantException {
            plantListManager.loadFromFile(filename,DELIMETER);

        }

}