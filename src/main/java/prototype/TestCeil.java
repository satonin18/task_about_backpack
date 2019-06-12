package prototype;

public class TestCeil {
    public static void main(String[] args) throws Exception {
        for (int i = Integer.MAX_VALUE - Integer.MAX_VALUE/1000000; i< Integer.MAX_VALUE ; i++){
            int res=0;
            res = (int) Math.ceil((double)i / 1);
            //res = (int) Math.ceil((double)1 / 2);
            if(res != i) {
                throw new Exception();
            }
        }
    }
}
