# Pathfinding Algorithm Visualizer
___
A program inspired by the design of [Francisco Zacarias own pathfinder](https://github.com/FranciscoZacarias/Algorithm-Visualizer-JavaFX), written from the ground up using Java and JavaFX 17.
The program supports a verity of four directional [pathfinding](#supported-pathfinding-algorithms) and [maze](#supported-maze-generation-algorithms) generation algorithms 
## Demo Video

___
## Supported Pathfinding Algorithms
* [A*](#a-a-star)(Supports Weights)
  * Manhattan Heuristic
  * Euclidean Heuristic
  * Chebyshev Heuristic
* [Breadth First Search](#breadth-first-search-bfs) (BFS)
* [Breadth First Search Greedy](#breadth-first-search-greedy)
  * Manhattan Heuristic
  * Euclidean Heuristic
  * Chebyshev Heuristic
* [Depth First Search (DFS)](#depth-first-search-dfs)
* [Dijkstra](#dijkstra) (Supports Weights)
* [Wave Front Propagation](#wave-front-propagation)
___
### A* (A-star)
[A*](https://en.wikipedia.org/wiki/A*_search_algorithm) is a single pair pathfinding algorithm that leverages a heuristics to help it efficiently search a weighted graph to find a near optimal or optimal path solution\
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
[BFS](https://en.wikipedia.org/wiki/Breadth-first_search#Bias_towards_nodes_of_high_degree) is a graph traversal and single source pathfinding algorithm, that expands in respect of the nodes added to the search, or in other words it explores
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
[BFS Greedy](https://en.wikipedia.org/wiki/Best-first_search#Greedy_BFS) is a variation of BFS that uses a heuristic function to evaluate adjacent cells to figure out what order
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
[DFS](https://en.wikipedia.org/wiki/Depth-first_search) similar to BFS is a graph traversal that expands only a single search path until a dead end is meet, where it then back-tracks 
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
        stack.push(nodes)
        visited[nodes] = true
```
***demo***
dfs video here
___
### Dijkstra
[Dijkstra](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) is weighted graph single source pathfinding algorithm that **guarantees** to find the shortest path. This algorithm
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
[Wave Front Propagation](https://en.wikipedia.org/wiki/Wavefront_expansion_algorithm) works like BFS in the way it respects the order of cells as they are added but differs as it starts from the target and expands out ward.
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
***demo***
put video here
___
## Supported Maze Generation Algorithms
* [Binary Tree](#binary-tree)
* [Depth First Search Randomized Backtracking](#depth-first-search-randomized-backtracking)
* [Kruskal](#kruskal)
* [Hunt and Kill](#hunt-and-kill)
* [Prims randomized](#prims-randomized) 
___
### Binary Tree
[Binary Tree Maze](https://weblog.jamisbuck.org/2011/2/1/maze-generation-binary-tree-algorithm) generation algorithm is a simple and stateless way of creating a perfect maze, such that only a single path
connects any two cells. The algorithm functions by iterating over all cells and making a binary decision of a direction to connect
two particular cells together. The implementation in this program has the binary choices of north or east.\
Pseudo Code
```
BinaryTree(list{nodes}):
  for(cells in list):
      node = getNorthOrWestAdjcentCell()
      join(cells,node)
```
***demo***
___
### Depth First Search Randomized Backtracking
[Depth First Search Randomized Backtracking](https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_depth-first_search) is an extension of the DFS algorithm in which cells are traversed randomly 
using the DFS algorithm. The process starts by choosing any cell randomly in the grid as the starting point and then choosing 
a random unvisited neighboring cell and removing the wall between them and adding it to the stack. The last step in this process is to remove
any cell from the stack once it has no unvisited neighboring cell remaining. The maze is generated once all cells have been removed from the stack\
Pseudo code
```
  DFSRandomized(list{nodes}):
    stack = []
    visited = []
    stack = random cell from list
    while(stack is not empty):
      node = randomAdjcentCell(stack.peek()) not in visited
      if node is null:
        stack.pop()
      visited[node] = true
      join(stack.peek(),node)
      stack.push(node)
```
***demo***
___
### Kruskal
[Kruskal](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm) is a [minimum spanning tree](https://en.wikipedia.org/wiki/Minimum_spanning_tree)(MST) 
algorithm that can be utilized to create a perfect maze. The algorithm starts by first creating a set for each cell that contains what cell their connected to.
It then randomly selects an edge between two cells and checks weather their sets contain each other if so the edge if skipped otherwise the two cells are then
connected and their sets updated accordingly. This process continues till all edges have been exhausted, at which point a perfect maze has been created\
Pseudo Code
```

```
___
### Hunt and Kill
Hunt and Kill algorithm is a [random walk](https://en.wikipedia.org/wiki/Random_walk) algorithm that guarantees a perfect maze when it finishes. The algorithm starts
by first picking a random cell to be starting point of the first random walk. The program then random walks across the grid
and stops once no more steps can be taken, i.e. it's been boxed in. The algorithm then scans across grid to find a cell to be next
starting point of the random walk, this cell must be adjacent to an already visited cell. The algorithm then random walks and repeat the process 
above until no valid starting points remain.\
Pseudo Code
````
````
___
### Prims Randomized
Prims Randomized is another MST based algorithm that can be used to create a perfect maze. The algorithm first starts by
first picking a random cell in the grid. It then finds all the so-called frontier cells, these are cells that are adjacent but seperated by a cell 
from current cell, and adds them to the set of potential future cells to visit. Prims then randomly picks one of the cells from future cells set and 
finds a neighboring cell that has already been visited and joins them together. It then adds its frontier cells to the future cell set and repeats till
no cells remain.\
Pseudo Code
```

```
___
## Utilized Libraries
* [ControlsFX](https://github.com/controlsfx/controlsfx)
  * Validation support
* [ikonli Icons](https://github.com/kordamp/ikonli)
  * BootStrap Icons
* [atlantafx](https://github.com/mkpaz/atlantafx)
  * Primer Light