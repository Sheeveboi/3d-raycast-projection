import java.awt.*;
import java.util.ArrayList; 
class Enviorment extends Canvas{

    public Enviorment(){
        setSize(500, 500);
        setBackground(Color.white);
    }
    
    static int xSize = 500;
    static int ySize = 500;

    public static void start() {

        Enviorment GP = new Enviorment();  

        Frame aFrame = new Frame();
        aFrame.setSize(xSize, ySize);
        aFrame.add(GP);
        
        aFrame.setVisible(true);
    }

    public void paint(Graphics c) {  
        ThreeD.init.run();
        try {
            while (true) {
                c.clearRect(0,0,xSize,ySize);
                ThreeD.update.run();
                for (int i = 0; i < ThreeD.globalMeshArr.size(); i++) {             
                    for (int u = 0; u < ThreeD.globalMeshArr.get(i).nodes.size(); u++) {
                        int screenX = (int)(ThreeD.globalMeshArr.get(i).nodes.get(u).screenX);
                        int screenY = (int)(ThreeD.globalMeshArr.get(i).nodes.get(u).screenY);
                        for (int o = 0; o < ThreeD.globalMeshArr.get(i).nodes.get(u).hashArr.length; o++) {
                            int screenX2 = (int)(ThreeD.globalMeshArr.get(i).nodes.get(ThreeD.globalMeshArr.get(i).nodes.get(u).hashArr[o])).screenX;
                            int screenY2 = (int)(ThreeD.globalMeshArr.get(i).nodes.get(ThreeD.globalMeshArr.get(i).nodes.get(u).hashArr[o])).screenY;
                            c.drawLine(screenX,screenY,screenX2,screenY2);
                        }
                        //c.fillRect(screenX,screenY,5,5);    
                    }
                }
                Thread.sleep(5);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}