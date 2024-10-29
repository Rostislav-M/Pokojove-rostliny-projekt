import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Plant implements Comparable<Plant>{
    private String name;
    private String notes;
    private LocalDate plantedDate;
    private LocalDate wateringDate;
    private int frequencyOfWatering;

    public Plant(String name, String notes, LocalDate plantedDate, LocalDate wateringDate, int frequencyOfWatering) throws PlantException {
        this.setName(name);
        this.notes = notes;
        this.setPlantedDate(plantedDate);
        this.setWateringDate(wateringDate);
        this.setFrequencyOfWatering(frequencyOfWatering);
    }

    public Plant(String name, int frequencyOfWatering) throws PlantException {
        this.setName(name);
        this.notes= "";
        this.plantedDate = LocalDate.now();
        this.wateringDate = LocalDate.now();
        this.setFrequencyOfWatering(frequencyOfWatering);
    }
    public Plant(String name) throws PlantException {
        this.setName(name);
        this.notes= "";
        this.plantedDate = LocalDate.now();
        this.wateringDate = LocalDate.now();
        this.frequencyOfWatering = 7;
    }
    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if(frequencyOfWatering == 0 || frequencyOfWatering < 0){
            throw new PlantException("frekvence zálivky nesmí být 0 nebo záporné číslo, zadal jsi: " + frequencyOfWatering);
        }
        else {
            this.frequencyOfWatering = frequencyOfWatering;
        }
    }
    public void doWateringNow(){
        this.wateringDate = LocalDate.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws PlantException {
        if(name.isEmpty()){
            throw new PlantException("Název rostliny nemůže být prázdný");
        }
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPlantedDate() {
        return plantedDate;
    }

    public void setPlantedDate(LocalDate plantedDate) throws PlantException {
        if (plantedDate.isAfter(LocalDate.now())) {
            throw new PlantException("Datum zasazení rostliny nesmí být v budoucnosti!  Zadali jste datum: " + plantedDate);
        }
       this.plantedDate = plantedDate;
    }

    public LocalDate getWateringDate() {
        return wateringDate;
    }

    public void setWateringDate(LocalDate wateringDate) throws PlantException {
        if(wateringDate.isBefore(plantedDate)){
            throw new PlantException("datum poslední zálivky nesmí být starší než datum zasazení rostliny ");
        }else{
            this.wateringDate = wateringDate;
        }

    }

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public String getWaterInfo(){
        LocalDate nextWatering= wateringDate.plusDays(frequencyOfWatering);
        return "název květiny: " + name + ", datum poslední zálivky: " + wateringDate
                + ", datum doporučené další zálivky: " + nextWatering;
    }

    @Override
    public String toString() {
        return "Plant:" +
                " name: " + name + ", " +
                "notes: " + notes + "," +
                " plantedDate: " + plantedDate +
                " wateringDate: " + wateringDate +
                " frequencyOfWatering: " + frequencyOfWatering;
    }

    @Override
    public int compareTo(Plant otherPlant) {
        return name.compareTo(otherPlant.name);
    }
}
