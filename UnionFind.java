import java.io.*;
import java.util.*;

// this is a standard union-find (uf) data structure. this keeps track of all the cells on the board and efficiently determines whether two edges have been connected

public class UnionFind {
	int n; // amount of points in my uf to keep track of
	int[] parent; // uf uses a forest of trees, and "parent" keeps track of the head of each vertex
	int[] weight; // this represents the amount of points in the tree rooted at each vertex
	int numcomp; // this represents the number of separate trees in the uf, aka the number of components
	
	/**
	 * constructor sets up the uf data structure
	 * 
	 * @param nin  the size of the uf data structure
	 */
	UnionFind(int nin) {
		this.n = nin;
		numcomp = nin;
		parent = new int[n];
		weight = new int[n];
		
		for (int i = 0; i < n; ++i) {
			parent[i] = i; // each vertex starts off as the root of its own tree
			weight[i] = 1; // each vertex starts of in a tree of size 1
		}
	}
	
	/**
	 * determines the highest root of the tree a point is located in
	 * 
	 * @param p  the vertex we want to find the root of
	 * @return the root
	 */
	
	int find(int p) {
		while (!(p == parent[p])) {
			parent[parent[p]] = parent[p]; // works to "flatten" out the trees, since each vertex will now be joined to its "grandparent" instead of its parent. Connected vertices will still be connected, so this will only increase efficiency
			parent[p] = p;
		}
		return p;
	}
	
	/**
	 * determines whether two vertices have the same root
	 * 
	 * @param p  the first vertex to compare
	 * @param q  the second vertex to compare
	 * @return whether the vertices have the same root, which implies they are connected
	 */
	
	boolean connected(int p, int q) {
		return (find(p) == find(q));
	}
	
	/**
	 * connects two vertices if they are unconnected by making one vertex the parent of the other vertex
	 * 
	 * @param p  first vertex to connect
	 * @param q  second vertex to connect
	 */
	
	void join(int p, int q) {
		if (connected(p, q)) {
			return;
		}
		
		// if the size of the tree rooted at p is larger than that size of the tree rooted at q, q should be attached at p, to keep the height of trees to a minimum
		
		if (weight[p] > weight[q]) {
			parent[q] = p;
			weight[p] += weight[q]; // increase the weight of p
		}
		else {
			parent[p] = q;
			weight[q] += weight[p];
		}
		--numcomp;
	}
	
	/**
	 * returns the size of the component p is in
	 * 
	 * @param p  the vertex we want to find the size of
	 * @return the size
	 */
	
	int sizeComp(int p) {
		return weight[find(p)];
	}
	
	/**
	 * returns the size of the tree rooted at p
	 * 
	 * @param p  the vertex we want to find the size of
	 * @return the size
	 */
	
	int size(int p) {
		return weight[p];
	}
	
	/**
	 * returns the number of trees in this uf
	 * 
	 * @return the number of components
	 */
	
	int numComp() {
		return numcomp;
	}
}
