# Pathfinding Algorithm Visualizer
___
A program inspired by the design of [Francisco Zacarias own pathfinder](https://github.com/FranciscoZacarias/Algorithm-Visualizer-JavaFX), written from the ground up using Java and JavaFX 17.
The program supports a verity of four directional pathfinding and maze generation algorithms 
## Demo Video

___
## Supported Pathfinding Algorithms
* A* (Supports Weights)
  * Manhattan Heuristic
  * Euclidean Heuristic
  * Chebyshev Heuristic
* Breadth First Search (BFS)
* Breadth First Search Greedy
  * Manhattan Heuristic
  * Euclidean Heuristic
  * Chebyshev Heuristic
* Depth First Search (DFS)
* Dijkstra (Supports Weights)
* Wave Front Propagation
___
### A*
A* is a single pair pathfinding algorithm that leverages a heuristics to help it efficiently search a weighted graph to find a near optimal or optimal path solution\
This program implementation allows the support of three different Heuristics: Manhattan, Euclidean, and Chebyshev\
Pseudo code
```
h(x):double = ranking heuristic
path(node,precessor):List<node>
  list = []
  while(node != predecessor[node]):
    list.add(node)
    node = predecessor[node]
  return list
A*(start, goal):
  visitedNodes = []
  predecessor = []
  searchQueue = {[start,start,0.0],0.0} //min heap
  occurredCost = []
  while(searchQueue is not empty):
    node = searchQueue.get()[0]
    predecessor[node] = searchQueue.get()[1]
    double weight = searchQueue.remove()[2]
    occurredCost[node] = weight
    visitedNodes[node] = true
    for(nodes in AdjcentCells(node)):
      if(nodes is goal):
        return path(node)
      searchQueue.add([nodes,node,weight+nodes.weight],h(nodes))
```
***demo***
video of A* go here
___
### Breadth First Search (BFS)
BFS is a graph traversal and single source pathfinding algorithm, that expands in respect of the nodes added to the search, or in other words it explores
all adjacent cells in order before moving on to other cells. Given that BFS expands respectfully of order, it guarantees that the shortest single source path is found in an unweighted graph\
Pseudo Code
```
  path(node,predecessor):List<node>
    list = []
    while(node != predecessor[node]):
      list.add(node)
      node = predecessor[node]
    return list
  BFS(start, goal):
    searchQueue = []
    visited = []
    searchQueue.add(start)
    predecessor = []
    while(searchQueue is not empty):
      visited[searchQueue.get()] = true;
      node = searchQueue.remove()
      for(nodes in AdjcentCells(node)):
         if(nodes is goal):
          return path(node)
        predecessor[nodes] = node
        visited[nodes] = true;
        searchQueue.add(nodes)
```

***demo***
video of bfs here

___
### BREADTH FIRST SEARCH GREEDY
BFS Greedy is a variation of BFS that uses a heuristic function to evaluate adjacent cells to figure out what order
to visit them in. BFS Greedy in most variations uses some sort of min heap or similar structure to handel the order of cell
traversal.\
Pseudo code
```
  h(x):double = ranking heuristic
  path(node,predecessor):List<node>
    list = []
    while(node != predecessor[node]):
      list.add(node)
      node = predecessor[node]
    return list
  BFS(start, goal):
    searchQueue = [] //min-heap
    visited = []
    searchQueue.add(start)
    predecessor = []
    predecessor[start] = start
    while(searchQueue is not empty):
      visited[searchQueue.get()] = true;
      node = searchQueue.remove()
      for(nodes in AdjcentCells(node)):
         if(nodes is goal):
          return path(node)
        predecessor[nodes] = node
        visited[nodes] = true;
        searchQueue.add(nodes,h(nodes))
```
___
### DEPTH FIRST SEARCH (DFS)
DFS similar to BFS is a graph traversal that expands only a single search path until a dead end is meet, where it then back-tracks 
to an unvisited node and continues this process until all nodes have been searched or the goal has been found. This algorithm does
not guarantee to find the shortest path.\
Pseudo code
```
  path(node,predecessor):List<node>
      list = []
    while(node != predecessor[node]):
      list.add(node)
      node = predecessor[node]
    return list
  DFS(start, goal):
    predecessor = []
    predecessor[start] = start
    stack = []
    visited = []
    stack.add(start)
    vistied[start] = true
    while(stack is not empty):
      node = stack.pop()
      for(nodes in AdjcentCells(node)):
        if(nodes == goal):
          return path(node)
        predecessor[nodes] = node
        stack.add(nodes)
        visited[nodes] = true
```
***demo***
dfs video here
___
### Dijkstra
Dijkstra is weighted graph single source pathfinding algorithm that **guarantees** to find the shortest path. This algorithm
leverages a greedy process in which it picks the shortest edge plus occurred weight from the list of current queued cells, it repeats this process
until the goal has been found or all cells have been searched\
Pseudo code
```
path(node,predecessor): List<node>
  list = []
  while(node != predecessor[node]):
    list.add(node)
    node = predecessor[node]
  return list

Dijkstra(start, goal):
  visited = []
  queue = [start] //min-heap
  occurredCost = []
  occurredCost[start] = 0
  predecessor = []
  predecessor[start] = start
  while(queue is not empty):
    node = queue.remove()
    for(nodes in AdjcentCells(node):
      if(nodes equals goal):
        return path(node)
      occurredCost[nodes] = occurredCost[node]+nodes.weigth
      queue.add(nodes, occurredCost[nodes])
```
***demo***
video goes here 
___
### Wave Front Propagation
Wave Front Propagation works like BFS in the way it respects the order of cells as they are added but differs as it starts from the target and expands out ward.
It searches the entire grid assigning a number to each cell representing the number of steps required to reach that cell. Once all cells have been searched the algorithm
then chooses the least weighted cell around the source and repeats until the target is reached guarantying the shortest path.\
Pseudo code
```
path(start,goal,cost[]):List<node>
  path  = []
  curr = start
  while(curr not null):
    path.add(curr)
    if(curr equals goal) break
    min = null
    minVal = Infinity
    for(nodes in AdjcentCells(curr)):
        minVal = Math.min(cost[nodes],minVal)
        if(minVal equals cost[nodes]): 
          min = nodes
  return path
WaveFront(start, goal):
   visted = []
   queue = [goal]
   visted[start] = true
   cost = []
   cost[goal] = 0
   while(queue is not empty):
    node = queue.remove()
    currentCost = cost[node]
    for(nodes in AdjcentCells(node)):
       cost[nodes] = currentCost+1
       queue.add(nodes)
       visited[nodes] = true
    return path(start,goal,cost)
```
___
## Supported Maze Generation Algorithms
* Binary Tree
* Depth First Search Randomized Backtracking
* Kruskal
* Hunt and Kill
* Prims randomized 
___
### Binary Tree
___
### Depth First Search Randomized Backtracking
___
### Kruskal
___
### Hunt and Kill
___
### Prims Randomized
___
## Utilized Libraries
* [ControlsFX](https://github.com/controlsfx/controlsfx)
  * Validation support
* [ikonli Icons](https://github.com/kordamp/ikonli)
  * BootStrap Icons
* [atlantafx](https://github.com/mkpaz/atlantafx)
  * Primer Light