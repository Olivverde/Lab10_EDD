/**
 * @author olivverde
 * Universidad del Valle de Guatemala
 * Algoritmos y estructura de datos 
 * 
 * Class's purpose:
 * Stores Floyd's algorithm's methods
 */



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FloydAlgorithm {

	//Class ambassador
    public FloydAlgorithm() {
        
    }
    
    /**
     * Executes Floyd's Algorithm
     * @param matrix
     * @param city
     * @param origin
     * @param destiny
     * @return
     */
    public String FloydAlgorithm(long[][] matrix, ArrayList<String> city, String origin, String destiny){
    	//Establishes matrix's size
        int vertex = matrix.length;
        long adjacencyMatrix[][] = matrix;	
        
        //Delimits the search
        int condition_1 = city.indexOf(origin);
        int condition_2 = city.indexOf(destiny);
        // n x n main path matrix
        String paths[][] = new String[vertex][vertex];
        // n x n secondary path matrix
        String secondaryPaths[][] = new String[vertex][vertex];
        String routeDistance = "";
        String pathss = "";
        
        int i, j, k;
        float temp1, temp2, temp3, temp4, min;
        
        //Instantiates the path matrix 
        for(i = 0; i < vertex; i++){
            for(j = 0; j < vertex; j++){
                paths[i][j] = "";
                secondaryPaths[i][j] = "";
            }
        }
        
        for (k = 0; k < vertex; k++) {
            for (i = 0; i < vertex; i++) {
                for (j = 0; j < vertex; j++) {
                    temp1 = adjacencyMatrix[i][j];
                    temp2 = adjacencyMatrix[i][k];
                    temp3 = adjacencyMatrix[k][j];
                    temp4 = temp2 + temp3;
                    
                    min = Math.min(temp1, temp4);
                    if(temp1 != temp4){
                        if(min == temp4){
                            routeDistance = "";
                            secondaryPaths[i][j] = k + "";
                            paths[i][j] = roadDistance(i, k, secondaryPaths, routeDistance) + (k + 1);                            
                        }
                    }
                    adjacencyMatrix[i][j] = (long) min;
                }
            }
        }
        
        ArrayList<Integer> road = new ArrayList<>();
        
        for (i = 0; i < vertex; i++) {
            for (j = 0; j < vertex; j++) {
                if(adjacencyMatrix[i][j] != 999999999){
                    if(i != j){
                        if(paths[i][j].equals("") && (j == condition_2) && (i == condition_1)){
                            pathss += "From " + city.get(i) + " To " + city.get(j) + " The route must be: " + city.get(i) + ", " + city.get(j) + "\n";
                        }else if(!paths[i][j].equals("") && (j == condition_2) && (i == condition_1)){
                            String others = paths[i][j];
                            if(!others.contains(",")){
                                road.add(Integer.parseInt(others));
                            }
                            while(others.contains(",")){
                                String walk = others.substring(0, others.indexOf(","));
                                others = others.substring(others.indexOf(",") + 2);
                                road.add(Integer.parseInt(walk));
                                if(!others.contains(",")){
                                    road.add(Integer.parseInt(others));
                                }
                            }
                            
                            String c = "";
                            for(Integer in: road){
                                
                                c += city.get(in - 1) + ", ";
                            }
                            pathss += "----------------------------------------------\n"+"From " + city.get(i) + " To " + city.get(j) + " The route must be:\n " + city.get(i) + ", " + c + city.get(j) +"\n----------------------------------------------\n"+"\n";
                        }
                    }
                }
            }
        }
        return "\n" + pathss;
    }
    
    
    /**
     * Show the road's distance among cities
     * @param i
     * @param k
     * @param secondaryPath
     * @param routeDistance
     * @return
     */
    public String roadDistance(int i, int k, String[][] secondaryPath, String routeDistance){
        if(secondaryPath[i][k].equals("")){
            return "";
        }else{
            routeDistance += roadDistance(i, Integer.parseInt(secondaryPath[i][k]), secondaryPath, routeDistance) + (Integer.parseInt(secondaryPath[i][k]) + 1) + ", ";
            return routeDistance;
        }
    }
    
    
    
    /**
     * Generates a matrix based on all available cities and its possible paths. 
     * @param cities
     * @param paths
     * @return
     */
    public long[][] generateMatrix(ArrayList<String> cities, ArrayList<Cities> paths){
    	// n x n matrix
        long matriz[][] = new long[cities.size()][cities.size()];
        //Go thought all matrix's spaces 
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
            	//If the origin and destiny coincide, then a 0 is set in the space
                if(i == j){
                    matriz[i][j] = 0;
                //
                }else{
                    String origen = cities.get(i);
                    String destino = cities.get(j);
                    int distancia = 0;
                    //Look for the right path distance
                    for(Cities c: paths){
                    	//Once it's been found, it's stated the right distance within the space
                        if(origen.equals(c.getOrigin()) && destino.equals(c.getDestiny())){
                            distancia = c.getDistance();
                        }
                    }
                    matriz[i][j] = distancia;
                }
            }
        }
        return matriz;
    }
    
    /**
     * Verify the existence of cities's paths
     * @param origin
     * @param destiny
     * @param cities
     * @return
     */
    public boolean verify(String origin, String destiny, ArrayList<String> cities){
        //Confirms the existence of the matrix
        boolean existence;
        
        existence = cities.contains(origin) && cities.contains(destiny);
        
        return existence;
    }
    
    /**
     * Shows the matrix
     * @param matrix
     * @return
     */
    public String showMatrix(long[][] matrix){
        
        int fila = matrix.length;
        
        String chain = "";
        
        for(int x = 0; x < fila; x++){
            for(int y = 0; y < fila; y++){
                if(matrix[x][y]==999999999){
                    chain += -1 + "\t";
                }else{
                    chain += matrix[x][y] + "\t";
                }
                
            }
            chain += "\n";
        }
        if(chain.equals("")){
            chain = "Matrix hasn't been found.";
        }
        return chain;
    }
    
    /**
     * Shows the graph's center
     * @param matrix
     * @return
     */
    public int graphCenter(long[][] matrix){
        ArrayList<Long> addition = new ArrayList<>();
        ArrayList<Long> maximum = new ArrayList<>();
        long max;
        int cont = 0;
        
        while(cont != matrix.length){
            max = 0;
            for (int i = 0; i < matrix.length; i++) {
                addition.add(matrix[i][cont]);
            }
            for(Long l: addition){
                if((l <= 9999999) && (l != 0)){
                    if(l > max){
                        max = l;
                    }
                }
            }
            maximum.add(max);
            addition.clear();
            cont++;
        }
        
        
        max = 0;
        cont = 0;
        for(Long l: maximum){
            if(l > max){
                max = l;
            }
        }
        int pos = maximum.indexOf(max);
        
        long min = max;
        
        for (int i = 0; i < matrix.length; i++) {
            if((matrix[i][pos]<=999999) && (matrix[i][pos]!=0)){
                if(matrix[i][pos] < min){
                    min = matrix[i][pos];
                }
            }
        }
        
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i][pos] == min){
                result = i;
                break;
            }
        }
        
        return result;
    }
}