package graph;

import korat.finitization.IArraySet;
import korat.finitization.IFinitization;
import korat.finitization.IIntSet;
import korat.finitization.IObjSet;
import korat.finitization.impl.FinitizationFactory;

import java.awt.print.Printable;
import java.util.*;

public class Graph {

    public static class Vertex {
        Vertex[] outgoingEdges;
    }

    
    Vertex root;
    int size; //for checking reachable
    int num = 0;
    
    public boolean repOK() {
        Set<Vertex> visited = new HashSet<Vertex>();
        Set<Vertex> path = new HashSet<Vertex>();
        Vertex[] translate = new Vertex[size];
        int[][] mat = new int[size-1][size-1];
        int[][] printgraph = new int[size][size];
        int check = 0;
        
        for (int i=0;i<size;i++) {
        	for (int j=0;j<size;j++) {
        		printgraph[i][j] = 0;
        	}
        }
        
        if(dfs(root,visited,path,printgraph,translate,size)){
            if(size == visited.size()){
                for (int i=0;i<size;i++) {
                	for (int j=0;j<size;j++) {
                		System.out.print(printgraph[i][j] + " "); 
                	}
                	System.out.print("\n");
                }
                System.out.print("\n");
                for (int i=1;i<size;i++) {
                	for (int j=1;j<size;j++) {
                	}
                }
                
                for(int i=0;i<num;i++) {
                    for (int j=0;j<size;j++) {
                    	for (int k=0;k<size;k++) {
                    	}
                    }
                    if(check != 0) {
                    	check = 0;
                    }
                    else {
						break;
					}
                }
                return true;
            }
        }
        return false;
        
    }

    private boolean dfs(Vertex v,Set<Vertex> visited,Set<Vertex> path,int[][] printgraph,Vertex[] translate,int size){
    	int flag = -1;
    	
		for (int j=0;j<size;j++) {
			if (translate[j] == v) {
				flag = j;
				break;
			}
		}
		if (flag == -1) {
			for (int j=0;j<size;j++) {
				if (translate[j] == null) {
					translate[j] = v;
					break;
				}
			}
		}
        visited.add(v);
        path.add(v);

        Set<Vertex> temp = new HashSet<Vertex>();
        if (v.outgoingEdges.length != 0) {
            for (int i=0;i<v.outgoingEdges.length;i++) {
                Vertex nextchild = v.outgoingEdges[i];
                if (path.contains(nextchild)) {
                   return false;
                }
                if (!temp.contains(nextchild)) {
                	temp.add(nextchild);
                }
                else {
					return false;
				}
                if (!dfs(nextchild, visited, path,printgraph,translate,size)) {
                    return false;
                }
                for(int j=0;j<size;j++) {
                	if(translate[j] == v) {
                		for(int k=0;k<size;k++) {
                			if(translate[k] == nextchild) {
                				printgraph[j][k] = 1;
                			}
                		}	
                	}
                }
            }
        }
        path.remove(v);
        return true;
    }

    public static IFinitization finGraph(int nodesNum) {
        IFinitization f = FinitizationFactory.create(Graph.class);
        
        IObjSet nodes = f.createObjSet(Vertex.class, nodesNum, false);
        IIntSet arrayLength = f.createIntSet(0, nodesNum - 1);
        IArraySet arrays = f.createArraySet(Vertex[].class, arrayLength, nodes, nodesNum);
        
        f.set("size", f.createIntSet(nodesNum));
        f.set("root",nodes);
        f.set("Vertex.outgoingEdges",arrays);
        
        return f;
    }
}
