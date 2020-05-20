/**
 * @author olivverde
 * Universidad del Valle de Guatemala
 * Algoritmos y estructura de datos 
 * 
 * Class's purpose:
 * Stores cities's attributes and get/set methods
 */


import java.util.ArrayList;

public class Cities {
    
    private String origin;
    private String destiny;
    private int distance;

    public Cities(String origin, String destiny, int distance) {
        this.origin = origin;
        this.destiny = destiny;
        this.distance = distance;
    }

    public Cities() {
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int destiny) {
        this.distance = destiny;
    }

    @Override
    public String toString() {
        return "Origin:" + origin + ", destiny: " + destiny + ", distance: " + distance;
    }
    
    /**
     * Creates an array formed of each involve city, cities aren't repeatable
     * @param path
     * @return
     */
    public ArrayList createArray(ArrayList<Cities> path){
        ArrayList<String> cities = new ArrayList<>();
        
        for(Cities c: path){
            if(!cities.contains(c.getOrigin())){
                cities.add(c.getOrigin());
            }
            if(!cities.contains(c.getDestiny())){
                cities.add(c.getDestiny());
            }
        }
        return cities;
    }
}