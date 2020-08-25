public class Node {
    
    int x;
    int y;
    int z;
    
    float screenX = 0;
    float screenY = 0;
    
    int[] hashArr;
    Node (int x1, int y1, int z1, int[] hashArr1) {
        x = x1;
        y = y1;
        z = z1;
        hashArr = hashArr1;
    }
}
