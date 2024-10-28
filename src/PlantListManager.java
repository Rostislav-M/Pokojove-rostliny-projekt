import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PlantListManager {
   private List<Plant> plantList;

    public List<Plant> getPlantList() {
        return plantList;
    }

    public void setPlantList(List<Plant> plantList) {
        this.plantList = plantList;
    }

    public PlantListManager() {
        this.plantList = new ArrayList<>();
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
            if(plant.getWateringDate().isBefore(LocalDate.now().minusDays(plant.getFrequencyOfWatering()))){
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
            System.out.println(plant.getWaterInfo());
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
                plantList.add(Plant.parsePlant(line,delimeter,lineNumber));
            }

        } catch (IOException e) {
            throw new PlantException("Soubor: " + filename +
                    " nebyl nalezen " + "\n"+ e.getLocalizedMessage());
        }

    }
    public void saveToFile(String filenameToWrite, String delimeter) throws PlantException {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filenameToWrite)))) {
            for (Plant plant : plantList) {
                printWriter.println(plant.toFileString(delimeter));
            }
        } catch (IOException e) {
            throw new PlantException(" Soubor: " + filenameToWrite + " nebyl nalezen " + "\n" + e.getLocalizedMessage());
        }
    }

}


