import java.util.ArrayList; 
public class ThreeD {
    public static ArrayList<Mesh> globalMeshArr = new ArrayList<Mesh>();
    public static Runnable init;
    public static Runnable update;
   
    public static Mesh initMesh(int x, int y, int z) {
        ArrayList<Node> emptyMap = new ArrayList<Node>();
        return new Mesh(x,y,z,0,0,0,emptyMap,glVars.perspective);
    }
    public static ArrayList<Node> initNodes() {
        return new ArrayList<Node>();
    }
    public static Node mountNode(int x, int y, int z, int[] hashes) {
        return new Node(x,y,z,hashes);
    }
    
    public static Mesh mountNodes(Mesh mounted, ArrayList nodes) {
        Mesh newMesh = mounted;
        newMesh.nodes = nodes;
        return newMesh;
    }
    
    public static void start(Runnable x, Runnable y) {
        init = x;
        update = y;
        Enviorment.start();
    }
    
}