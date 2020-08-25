public class Complex {
    
    public static double dist(double x, double y, double z, double x1, double y1, double z1) {
        return Math.sqrt(Math.pow((x1 - x),2) + Math.pow((y1 - y),2) + Math.pow((z1 - z),2));
    }
    
    //euler angles
    public static double eulerX(double x, double y, double z, double angleX, double angleY, double angleZ) {
        return (x*(Math.cos(angleX)*Math.cos(angleY))) + (y*((Math.cos(angleX)*Math.sin(angleY)*Math.sin(angleZ))-(Math.sin(angleX)*Math.cos(angleZ)))) + (z*((Math.cos(angleX)*Math.sin(angleY)*Math.cos(angleZ))+(Math.sin(angleX)*Math.sin(angleZ))));
    }
    public static double eulerY(double x, double y, double z, double angleX, double angleY, double angleZ) {
        return (x*(Math.sin(angleX)*Math.cos(angleY))) + (y*((Math.sin(angleX)*Math.sin(angleY)*Math.sin(angleZ))+(Math.cos(angleX)*Math.cos(angleZ)))) + (z*((Math.sin(angleX)*Math.sin(angleY)*Math.cos(angleZ))-(Math.cos(angleX)*Math.sin(angleZ))));
    }
    public static double eulerZ(double x, double y, double z, double angleX, double angleY, double angleZ) {
        return (x*(Math.sin(angleY)*-1)) + (y*(Math.cos(angleY)*Math.sin(angleZ))) + (z*(Math.cos(angleY)*Math.cos(angleZ)));
    }
    //god i hate these equasions with a burning passion. Like seriously just look at them and tell me you DONT hate them; you cant.
    
    //parametric function
    public static double ray(double p, double p1, double t) {
        return p + t * (p1 - p);
    }
}