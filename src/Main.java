/**
 * @author olivverde
 * Universidad del Valle de Guatemala
 * Algoritmos y estructura de datos 
 * 
 * Class's purpose:
 * Executes the whole program
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    
    public static void main(String[] args) throws IOException {
        
    	//Classes's ambassadors
        File archivo = new File ("guategrafo.txt");
        FloydAlgorithm fA = new FloydAlgorithm();
        ArrayList<Cities> path = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();
        Cities city = new Cities();
        
        //Reading file
        FileReader fR = new FileReader(archivo);
        BufferedReader br1 = new BufferedReader(fR);
        String line = "";
        Scanner scanner = new Scanner(fR);
        
        //Attributes
        String originCity;
        String destinyCity;
        int distance;
        int selection;        
        long matrix[][];
        int flag = 0;
        
        //Cutting info
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            //Origin City
            originCity = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf(" ") + 1, line.length());
            //Destiny City
            destinyCity = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf(" ") + 1, line.length());
            //Distance among both cities
            distance = Integer.parseInt(line.substring(0, line.length()));
            
            flag++;
            //Creating a Cities object
            path.add(new Cities(originCity, destinyCity, distance));
            
            fR.close();
            br1.close();
        }
        //Reads user's option
        Scanner sc = new Scanner(System.in);
        int option = 0;
        
        OUTER: //Menu
        while (true) {
        	System.out.println("------------------------------------------------------");
        	System.out.println("------GUATEMALAN COVID-19 EMERGENCY GPS CENTER--------");
        	System.out.println("------------------------------------------------------");
            System.out.println("\n1. Path among cities.");
            System.out.println("2. Graphs's city center.");
            System.out.println("3. Modify the graph.");
            System.out.println("4. Show matrix.");
            System.out.println("5. Exit");
            selection = sc.nextInt();
            
            //Menu's option
            switch (selection) {
            
            	//Option 1
            	//Using an origin and destiny city, can be found out the shortest path among them.
                case 1:
                    System.out.println("-------Submit origin city-------");
                    String o = sc.nextLine();
                    o = sc.nextLine();
                    System.out.println("-------Submit destiny city-------");
                    String d = sc.nextLine();
                    
                    cities.clear();
                    //Creates a general city array
                    cities = city.createArray(path);
                    //Generate a Matrix based on the city array & paths
                    matrix = fA.generateMatrix(cities, path);
                    
                    
                    //If the path exists, then...
                    if(fA.verify(o, d, cities)){
                        //Floyd's Algorithm implementation
                        System.out.println(fA.FloydAlgorithm(matrix, cities, o, d));
                    }else{
                        System.out.println("Path hasn't been found.");
                    }
                    break;
                    
                    
                    
                //Option 2
                case 2:
                    cities.clear();
                    cities = city.createArray(path);
                    matrix = fA.generateMatrix(cities, path);
                    fA.graphCenter(matrix);
                    //Shows the center of the graph
                    System.out.println("Operations center is located in: " + cities.get(fA.graphCenter(matrix)));
                    
                    break;
                    
                //Allow the modification of the graph
                case 3:
                    String newDestiny = "";
                    System.out.println("-------Road's issues-------");
                    System.out.println("-------Establish a new bond amoung cities-------");
                    newDestiny = sc.next();
                    
                    if("a".equals(newDestiny.toLowerCase())){
                        System.out.println("-------Submit origin city-------");
                        String ori = sc.next();
                        System.out.println("-------Submit destiny city-------");
                        String dest = sc.next();
                        
                        boolean background = false;
                        for(Cities c: path){
                            if((c.getOrigin().equals(ori)) && (c.getDestiny().equals(dest))){
                                path.remove(c);
                                background = true;
                                System.out.println("-------Road's Interruption has been set successfully-------");
                                break;
                            }
                        }
                        if(background == false){
                            System.out.println("-------Cities's path hasn't been found-------");
                        }
                    }else if("b".equals(newDestiny.toLowerCase())){
                        System.out.println("-------Submit new origin-------");
                        String ori = sc.next();
                        System.out.println("-------Submit new destiny-------");
                        String dest = sc.next();
                        System.out.println("-Submit distance among \n" + ori + " and " + dest+"-");
                        int dist = sc.nextInt();
                        
                        path.add(new Cities(ori, dest, dist));
                        System.out.println("Cities's path has been\nsubmitted successfully.");
                    }
                    
                    break;
                case 4:
                    cities.clear();
                    cities = city.createArray(path);
                    matrix = fA.generateMatrix(cities, path);
                    System.out.println("Adyacency matrix: ");
                    for(String s: cities){
                        System.out.print(s + " ");
                    }
                    System.out.println("");
                    System.out.println(fA.showMatrix(matrix));
                    break;
                case 5:
                    break OUTER;
                default:
                    break;
            }
            
            
            System.out.println("--------------------------------------------------------");
        }
    }
}