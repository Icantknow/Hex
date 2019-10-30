import java.io.*;
import java.util.*;

public class Board {
	int size;
	UnionFind p1;
	UnionFind p2;
	
	Board(int n) {
		size = n;
		p1 = new UnionFind(n*n+2);
	}
	
	
}
