package prototype.other.generic.UriyTkach.bounded2compare;

public class Dummy extends Product<NotProduct> {
    String strDummy;

    @Override
    void subCompare(NotProduct o) {
        return;
    }
}

class NotProduct extends Object{}