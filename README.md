# MHMHTree
A balanced binary search tree implementation

## Introduction
The MHMHTree(MaxHeap-MinHeap Tree) is an implementation of a balanced binary search tree(BST). It balances itself after every insert to not become skewed on one side or another. It builds on the idea of having two heaps, a maxheap for lower half elements in the list, and a minheap of the upper half elements in the list. Then each heap itself is further split into max heap, and min heap, recursively.

## Nodes
There are two types of nodes in the tree.

### Data Nodes
These contain actual user keys. 
These are denoted by a circle with a numeric value in it in the diagrams below.

![alt text](screenshots/SingleDataNode.png "Single Data Node")

### Balance Nodes
These nodes don’t contain any user keys.
They are around just to maintain the balance of the tree.
These are denoted by a circle with an ‘X’ mark in it.

![alt text](screenshots/SingleBlncNode.png "Single Balance Node")

## Working
The tree self-balances itself whenever there is an insert or delete, as follows:

### Inserts
Here are the possible scenarios

#### Empty Tree
Create a new data node with user data, left sub-tree is null, right sub-tree is null.
![alt text](screenshots/SingleDataNode.png "Single Data Node")

#### Odd number of data nodes
When there are odd number of data nodes in tree, the root node will be a data node, and each of the two sub-trees will have equal number data nodes. Like in the case of 1 data node, the root node is a data node.
If there is an insert in this kind of tree, the inserted data is compared with the medial(root) data and based on comparison result, it gets inserted into one sub-tree, and the data node's data gets inserted into the other sub-tree. The root data node now becomes a balance node.
![alt text](screenshots/2NodeTree.png "2 Node Tree")

#### Even number of data nodes
When there are even number of data nodes in tree, the root node will be a balance node, and each of the two sub-trees will have equal number of data nodes.
If there is an insert in this kind of tree, then 
* inserted data is compared with largest child of left sub-tree(max heap). If it is lesser than this child, then 
  * we insert the new data node in left sub-tree
  * remove the largest child in left sub-tree
  * add the data of largest child to the root node, thus making it a data node.
* else, inserted data is compared with smallest child of right sub-tree(min heap). If it is larger than this child, then 
  * we insert the new data node in right sub-tree
  * remove the smallest child in left right sub-tree
  * add the data of smallest child to the root node, thus making it a data node.
* else, we put the inserted data into the root of the tree.
![alt text](screenshots/3NodeTree.png "3 Node Tree")

### Deletes
Deletes happen exactly as a reverse of inserts.

#### Odd number of data nodes
If you delete a node from one sub-tree, you need to insert the root data(it has to be data node since the tree has odd number of data nodes) into that sub-tree to maintain the balance.

#### Even number of data nodes
If you delete a node from minheap sub-tree, then you need to remove the max node from maxheap sub-tree and put it in root node. And do the opposite if you remove a node from maxheap sub-tree.

### Lookups
Lookups are the same as a normal binary search tree since all the data nodes in the left sub-tree of current node are smaller than current node, and all the nodes in the right sub-tree of current node are greater than current node.

## Performance
### Run-time
The number of rebalances happening in the tree per insert/delete is proportional to the number of nodes in the tree. Hence runtime increases linearly(O(n)) with the number of data nodes in tree.
![alt text](screenshots/InsertPerf.png "Insertion performance Chart")

## Memory
The number of balance nodes in the tree are in the range [0, N-1] for N data nodes.
Each node has references to:
*  each of its two children,
* its parent,
* the actual user data,
* the max data node of maxheap, and
* min data node from minheap.
