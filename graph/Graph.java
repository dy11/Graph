package graph;

import korat.finitization.IArraySet;
import korat.finitization.IFinitization;
import korat.finitization.IIntSet;
import korat.finitization.IObjSet;
import korat.finitization.impl.FinitizationFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

public class Graph {

    public static class Vertex {
        Vertex[] outgoingEdges;
    }

    
    Vertex root;
    int size; //for checking reachable
    int num = 0;
    static int count = 0;
    
    public boolean repOK() throws IOException {

        String data = ""; 
        //fw.write(data,0,data.length());  
        //fw.flush();
        //while((data = br.readLine())!=null)  
        
        Set<Vertex> visited = new HashSet<Vertex>();
        Set<Vertex> path = new HashSet<Vertex>();
        Vertex[] translate = new Vertex[size];
        int[][] mat = new int[size][size];
        int[][] printgraph = new int[size][size];
        int check = 0;
        int ch = 0;
        
        for (int i=0;i<size;i++) {
            for (int j=0;j<size;j++) {
                printgraph[i][j] = 0;
            }
        }
        
        if(dfs(root,visited,path,printgraph,translate,size)){
            FileWriter fw = new FileWriter("mat"+size+".txt", true);
            FileReader fr = new FileReader("mat"+size+".txt"); 
            if(size == visited.size()){
                for(int i=0;i<num;i++) {
                    check = 0;
                    int count = -1;
                    while((ch = fr.read())!=-1) {
                        count++;
                        mat[count/size][count%size] = (char)ch-'0';
                        if(count == size*size - 1)
                            break;
                    }
                    
                    for (int j=0;j<size;j++) {
                        for (int k=0;k<size;k++) {
                            if (mat[j][k] == printgraph[j][k]) {
                                check++;
                            }
                        }
                    }
                    
                    if(check == size*size) {
                        return false;
                    }
                    
                }
                
                for (int i=0;i<size;i++) {
                    for (int j=0;j<size;j++) {
                        System.out.print(printgraph[i][j] + " "); 
                    }
                    System.out.print("\n");
                }
                System.out.print("\n");
                
                int[] number = new int[size];
                int[][] numberset = new int[factorial(size)][size];
                for(int i=0;i<size;i++) {
                    number[i] = i;
                }
                if(count == 0) {
                    sort(0,size,number,numberset);
                }
                else {
                    count = 0;
                    sort(0, size, number, numberset);
                }
                for(int i=0;i<factorial(size);i++) {
                    for(int j=0;j<size;j++) {
                        for(int k=0;k<size;k++) {
                            mat[numberset[i][j]][numberset[i][k]] = printgraph[j][k];
                        }
                    }                   
                    for(int j=0;j<size;j++) {
                        for(int k=0;k<size;k++) {
                            data = data + mat[j][k];
                        }
                    }
                    fw.append(data);  
                    fw.flush();
                    data = "";
                }
                data = "";
                num = num + factorial(size);
                fw.close();
                fr.close();
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

    
    public static int factorial(int num){
        int sum=1;
        if(num < 0)
            throw new IllegalArgumentException("error");
        if(num==1){
            return 1;
        }else{
            sum=num * factorial(num-1);
            return sum;
        }
    }
        
    static void swap(int arg1, int arg2 ,int[] a){
        int temp;
        temp = a[arg1];
        a[arg1] = a[arg2];
        a[arg2] = temp;
    }
    static void  sort(int index , int n ,int[] a, int[][] numberset){
        int i;
        if (index == n){
            for (i = 0; i < n; i++){
                numberset[count][i] = a[i];
            }
                count++;
                return;
        }
        for (i = index; i < n; i++) {
            swap(index,i,a);
            sort(index+1,n,a,numberset);
            swap(index,i,a);
        }
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
