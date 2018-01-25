# MHMHTree
A balanced binary search tree implementation

## Introduction
The MHMHTree(MaxHeap-MinHeap Tree) is an implementation of a balanced binary search tree(BST). It balances itself after every insert to not become skewed on one side or another. It builds on the idea of having two heaps, a maxheap for lower half elements in the list, and a maxheap of the upper half elements in the list. Then each heap itself is further split into max heap, and min heap, recursively.

## Nodes
There are two types of nodes in the tree.

### Data Nodes - 
These contain actual user keys. 
These are denoted by a circle with a numeric value in it in the diagrams below.

### Balance Nodes - 
These nodes don’t contain any user keys.
They are around just to maintain the balance of the tree.
These are denoted by a circle with an ‘X’ mark in it.

<WIP>