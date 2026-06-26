package domini;

public class AireCondicionat {
    private static boolean estiu = true;
    public static void setEstiu(){
        estiu = true;
    }
    public static void setHivern(){
        estiu = false;
    }
    public static boolean isEstiu() {
        return estiu;
    }
}
