package prototype.other.generic.UriyTkach.bounded3ver;

public class Camera extends Product<Camera> {
    int pix;

    @Override
    void subCompare(Camera o) {
        return;
    }
}
//    //without generic BAD-ER
//    void subCompare(Product o) {
//        if(o instanceof Camera){}
//        return;
//    }
