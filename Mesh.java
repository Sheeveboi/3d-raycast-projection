import java.util.ArrayList; 
import java.awt.*; 
public class Mesh {
    
    static ArrayList<Node> nodes;
    
    String name;
    static int x;
    static int y;
    static int z;
    
    static double rx;
    static double ry;
    static double rz;
    
    public Mesh(int x1, int y1, int z1, double rx1, double ry1, double rz1, ArrayList nodes1) {
        x = x1;
        y = y1;
        z = z1;
        
        rx = rx1;
        ry = ry1;
        rz = rz1;
        
        nodes = nodes1;
    }
    
    public static void setScreenCartesian() {
        for (int i = 0; i < nodes.size(); i++) {
            double fullRotX = rx + glVars.cameraRX;
            double fullRotY = ry + glVars.cameraRY;
            double fullRotZ = rz + glVars.cameraRZ;
            
            double rayVectorX = (Complex.eulerX(x,y,z,glVars.cameraRX,glVars.cameraRY,glVars.cameraRZ)+glVars.cameraX) + Complex.eulerX(nodes.get(i).x,nodes.get(i).y,nodes.get(i).z,fullRotX,fullRotY,fullRotZ);
            double rayVectorY = (Complex.eulerY(x,y,z,glVars.cameraRX,glVars.cameraRY,glVars.cameraRZ)+glVars.cameraY) + Complex.eulerY(nodes.get(i).x,nodes.get(i).y,nodes.get(i).z,fullRotX,fullRotY,fullRotZ);
            double rayVectorZ = (Complex.eulerZ(x,y,z,glVars.cameraRX,glVars.cameraRY,glVars.cameraRZ)+glVars.cameraZ) + Complex.eulerZ(nodes.get(i).x,nodes.get(i).y,nodes.get(i).z,fullRotX,fullRotY,fullRotZ);
            
            double cameraNormalX = 0;
            double cameraNormalY = 0;
            double cameraNormalZ = glVars.perspective;
            
            double testVectorX = cameraNormalX - rayVectorX;
            double testVectorY = cameraNormalY - rayVectorY;
            double testVectorZ = cameraNormalZ * 10 - rayVectorZ;
            
            double t = -(testVectorX * cameraNormalX + testVectorY * cameraNormalY + testVectorZ * cameraNormalZ) / (cameraNormalX * Complex.ray(rayVectorX,0,0) + cameraNormalY * Complex.ray(rayVectorY,0,0) + cameraNormalZ * Complex.ray(rayVectorZ,0,0));
            
            nodes.get(i).screenX = (float)(Complex.ray(rayVectorX,0,t) + Enviorment.xSize/2);
            nodes.get(i).screenY = (float)(Complex.ray(rayVectorY,0,t) + Enviorment.ySize/2);
           
        }
    }
}