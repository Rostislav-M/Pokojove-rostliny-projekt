import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PlantListManager {
   private List<Plant> plantList;

    public PlantListManager() {
        this.plantList = new ArrayList<>();
    }

   public List<Plant> getPlantList() {
        return plantList;
    }

    public void setPlantList(List<Plant> plantList) {
        this.plantList = plantList;
    }

    public void addPlant(Plant plant){
        plantList.add(plant);
    }
    //získání květiny na zadaném indexu
    public Plant getPlantOnIndex(int index) throws PlantException {
        if (index < 0 || index >= plantList.size()) {
            throw new PlantException("Index " + index + " Zadaný index je mimo rozsah!");
        }
        return plantList.get(index);
    }
    //odebrání květiny ze seznamu
    public void removePlant(Plant plant) throws PlantException {
        if (!plantList.contains(plant)) {
            throw new PlantException("rostlina " + plant.getName() + " neexistuje v seznamu");
        }
        plantList.remove(plant);
    }

    public void removePlantOnIndex(int index) throws PlantException {
        if (index < 0 || index >= plantList.size()) {
            throw new PlantException("Index " + index + " Zadaný index je mimo rozsah!");
        }
        plantList.remove(index);
    }
    //získání kopie seznamu květin.
    public List<Plant> getCopyOfList(){
       return new ArrayList<>(plantList);
    }
    //Přidej metodu, která vrátí seznam rostlin, které je třeba zalít.
    public List<Plant> getPlantsRequiringWatering(){
        List<Plant> plantsListReqWatering = new ArrayList<>();

        for (Plant plant: plantList){
            if (plant.getWateringDate().isBefore(LocalDate.now().minusDays(plant.getFrequencyOfWatering())) ||
                plant.getWateringDate().plusDays(plant.getFrequencyOfWatering()).isEqual(LocalDate.now()))  {
                plantsListReqWatering.add(plant);
            }

        }
        return plantsListReqWatering;
    }
    public void printPlantList() throws PlantException {
        if (plantList.isEmpty()) {
            throw new PlantException("Seznam rostlin je prázdný! ");
        }
        for(Plant plant: plantList){
            System.out.println(plant.getWateringInfo());
        }
    }

    public void sortPlants(){
        plantList.sort(null);
    }
    public void sortPlantsByWateringDate(){
        plantList.sort(Comparator.comparing(Plant::getWateringDate));
    }

    public void sortByNameThenByWateringDate(){
        plantList.sort(Comparator.comparing(Plant::getName).thenComparing(Plant::getWateringDate));

    }
    public void loadFromFile(String filename, String delimeter) throws PlantException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            plantList.clear();
            int lineNumber=0;
            while (scanner.hasNextLine()){
                String line= scanner.nextLine();
                lineNumber++;
                plantList.add(parsePlant(line,delimeter,lineNumber));
            }

        } catch (IOException e) {
            throw new PlantException("Soubor: " + filename +
                    " nebyl nalezen " + "\n"+ e.getLocalizedMessage());
        }

    }
    public static Plant parsePlant(String line, String delimeter,int lineNumber) throws PlantException {
        int numberOfParts=5;
        String[] parts = line.split(delimeter);
        if (parts.length !=numberOfParts){
            throw new PlantException("nesprávný počet položek na řádku číslo: " + lineNumber
                    + " Očekávaný počet položek je: " + numberOfParts );
        }
        String name= parts[0];
        if(name.isEmpty()){
            throw new PlantException("jméno rostliny nemůže být prázdné na řádku: " + lineNumber);
        }

        try {
            String notes=parts[1];
            int frequencyOfWatering= Integer.parseInt(parts[2]);
            LocalDate wateringDate= LocalDate.parse(parts[3]);
            LocalDate plantedDate=LocalDate.parse(parts[4]);
            return new Plant(name,notes,plantedDate,wateringDate,frequencyOfWatering);
        } catch (DateTimeParseException e ) {
            throw new PlantException("Chybný formát data! " + "na řádku čislo: "+lineNumber);
        } catch (NumberFormatException e){
            throw new PlantException("Chybný formát frekvence zálivky! " + "na řádku číslo: "+lineNumber);
        }

    }

    public void saveToFile(String filenameToWrite, String delimeter) throws PlantException {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filenameToWrite)))) {
            for (Plant plant : plantList) {
                printWriter.println(
                                plant.getName() + delimeter
                                +plant.getNotes() + delimeter
                                +plant.getFrequencyOfWatering() + delimeter
                                +plant.getWateringDate() + delimeter
                                +plant.getPlantedDate()
                );
            }
        } catch (IOException e) {
            throw new PlantException(" Soubor: " + filenameToWrite + " nebyl nalezen " + "\n" + e.getLocalizedMessage());
        }
    }

}


