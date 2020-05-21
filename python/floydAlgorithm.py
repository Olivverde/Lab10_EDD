#Oliver Josué de León Milian
#Universidad del Valle de Guatemala
#Algoritmos y Estructura de datos
#Hoja de trabajo 10 - Individual
#Fecha de modificacion: 21/05/2020

import networkx as nx
#Gathers origin and destiny city
def getSubmittedCity():
    #Open file
    archive = open("guategrafo.txt","r")
    dictionary = {}
    flag = 1
    city = ""
    #Reading file
    for line in archive.readlines():
        #Cut first part of the submitted line 
        city = line[0 : line.find(" ")]
        if (not(city in dictionary)):
            dictionary[city] = flag
            flag += 1
        #Cut second part of the submitted line
        line = line[line.find(" ")+1 : len(line)]
        city = line[0 : line.find(" ")]
        
        if (not(city in dictionary)):
            dictionary[city] = flag
            flag += 1
        
    archive.close()
    
    return dictionary,flag

#Builds graph's chart
def buildGraph(dictionary):
    g = nx.DiGraph()
    archive = open("guategrafo.txt","r")
    #Cut cities
    for line in archivo.readlines():
        #Origin city
        originCity = line[0 : line.find(" ")]
        #Destiny city      
        line = line[line.find(" ")+1:len(line)]
        destinyCity = line[0 : line.find(" ")]
        #Distance among cities
        line = line[line.find(" ")+1:len(line)]
        distance = line[0 : len(line)]
        
        g.add_edge(dictionary[originCity],dictionary[destinyCity],weight = float(distance))
    return g
    
   
    
#Show Matrix
def getMatrix(citiesAmount):
    list1 = []
    list2 = []
    for i in range(citiesAmount):
        list2.append(0)

    for j in range(citiesAmount):
        list1.append(list2)
        
    return list1

#Shows the shortest path
def getPreviousMatrix(g,distance):   
    distance = nx.floyd_warshall_predecessor_and_distance(g)
    predecesor = nx.floyd_warshall_predecessor_and_distance(g)
    print ("Shortest path: ")
    return predecesor

    

def shortestPath(origin,destiny,predecesor,cityDictionary):
    startIndex = 0
    endIndex = 0
    route = []
    

    for i in cityDictionary:
        if(i == origin):
            startIndex = cityDictionary[i]

        if(i == destiny):
            endIndex = cityDictionary[i]
    if((startIndex == 0) or (endIndex == 0)):
        print("Data submission has failed!")

   
    try:
        next = predecesor[startIndex][endIndex]
        route.append(next)
    except KeyError:
        print()


        
    while(True):
        try:
            if(predecesor[startIndex][endIndex] == origin):
                break

            
            next = predecesor[startIndex][next]
            route.append(next)

            if(predecesor[startIndex][next] == origin):
                break
        except KeyError:
            break
    routeFlag = 1
    takenRoute = []
    for i in cityDictionary:
        for j in route:
            try:
                if(cityDictionary[i]==j):
                    takenRoute.append(i)
                routeFlag += 1
            except IndexError:
                break
    takenRoute.append(destiny)

    stringing = "The best route is: "
    controlStringing = 0
    for k in takenRoute:
        if (controlStringing ==0):
            stringing = stringing + " " + str(k)
        else:
            stringing = stringing + ", " + str(k)
        
        controlStringing += 1

    if(len(takenRoute) == 1):
        print("Wrong data")
    else:
        print(stringing)
        

def menu():
    return "\n 1. Shortest route among cities. \n 2. Graph's center. \n 3. Add path's interruption. \n 4. Exit."

def getGraphCenter(g,cityDictionary):
    ranking = nx.betweenness_centrality(g)
    print (ranking)

    list = []
    for n in ranking:
        
        if (ranking[n]==0):
            list.append(n)

    newList=[]
    for i in cityDictionary:
        for j in list:
            try:
                if(cityDictionary[i]==j):
                    newList.append(i)
            except IndexError:
                break

    print(newList)

    print("Likely graph's center: ")
    for t in newList:
        print(t)
        
            
def newInterruption():
    originCity=input("Input origin city: ")
    destinyCity=input("Input destiny city: ")

    file = open("guategrafo.txt","r")
    lines = file.readlines()
    file.close()

    

    file = open("guategrafo.txt","w")

    control=0
    for line in lines:
        city1=line[0:line.find(" ")]
        line2=line[line.find(" ")+1:len(line)]
        city2=line2[0:line.find(" ")]

        if((city1==originCity) and (city2==destinyCity)):
            control += 1
        else:
            file.write(line)
    file.close()

    if(control==0):
        print("Path hasn't been found")
        

def stateconnection():
    originCity=input("Input origin city: ")
    destinyCity=input("Input destiny city: ")
    distance=input("Input distance among cities: ")

    if(not(isinstance(distance, int))):
        print("Distance value must be an interger ")
        return ""

    file = open("guategrafo.txt","r")
    lines = file.readlines()
    file.close()
    

    file = open("guategrafo.txt","w")
    for line in lines:
        file.write(line)
    file.write("\n"+originCity+" "+destinyCity+" "+distance)
    file.close()
    

##MAIN BUENO
while(True):
    print(menu())
    option=input("Input an option: ")

    if(option=="1"):
        #para ver route más corta
        origin=input("Input origin city")
        destiny=input("Input destiny city")
        shortestPath(origin,destiny,predecesor,cityDictionary)
        
    if(option=="2"):
        getGraphCenter(buildGraph(cityDictionary),cityDictionary)

    if(option=="3"):
        print("a) Input new interruption among cities")
        print("b) Input new connection among cities")
        subSelection=input("Ingrese la opcion")
        
        if(subSelection=="a"):
            newInterruption()
        if(subSelection=="b"):
            stateconnection()
            
    