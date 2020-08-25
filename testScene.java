import java.util.ArrayList; 
public class testScene {
    
    public static void init() {
        ArrayList<Node> emptyMap = new ArrayList<Node>();
        Mesh empty = new Mesh(0,0,200,0,0,0,emptyMap,glVars.perspective);
        ArrayList nodeMap = ThreeD.initNodes();
        
        int[] hashMap = {1,3,4};
        nodeMap.add(ThreeD.mountNode(20,20,20,hashMap)); //0 top right 
        int[] hashMap1 = {5};
        nodeMap.add(ThreeD.mountNode(-20,20,20,hashMap1)); //1 top left
        int[] hashMap2 = {1,3,6};
        nodeMap.add(ThreeD.mountNode(-20,-20,20,hashMap2)); //2 bottom right
        int[] hashMap3 = {7};
        nodeMap.add(ThreeD.mountNode(20,-20,20,hashMap3)); //3 bottom left
        
        int[] hashMap4 = {5,7};
        nodeMap.add(ThreeD.mountNode(20,20,-20,hashMap4)); //4
        int[] hashMap5 = {};
        nodeMap.add(ThreeD.mountNode(-20,20,-20,hashMap5)); //5
        int[] hashMap6 = {5,7};
        nodeMap.add(ThreeD.mountNode(-20,-20,-20,hashMap6)); //6
        int[] hashMap7 = {};
        nodeMap.add(ThreeD.mountNode(20,-20,-20,hashMap7)); //7
       
        
        Mesh finalMesh = ThreeD.mountNodes(empty, nodeMap);
        ThreeD.globalMeshArr.add(finalMesh);
    }
    static float x = 0;
    static float y = 0;
    static float z = 0;
    public static void update() {
        x += .01;
        y += .01;
        z += .01;
        for (int i = 0; i < ThreeD.globalMeshArr.size(); i++) {
            ThreeD.globalMeshArr.get(i).rx = x;
            ThreeD.globalMeshArr.get(i).ry = y;
            ThreeD.globalMeshArr.get(i).rz = z;
            ThreeD.globalMeshArr.get(i).setScreenCartesian();
        }
    }
    public static void main() {
        Runnable x = () -> init();
        Runnable y = () -> update();
        ThreeD.start(x,y);
    }
}