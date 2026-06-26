package domini.patroEstats;

public final class Inactiu implements IEstatsVentilador {
    // patró singleton
    private static Inactiu estatUnic = new Inactiu();
    private Inactiu(){}
    public static Inactiu getEstatUnic() {
        return estatUnic;
    }

    @Override
    public IEstatsVentilador botoActivacio() {
        return new ActiuAmbVentilador();
    }
    @Override
    public IEstatsVentilador botoVentilador() {
        return this;
    } // no fem res

    @Override
    public IEstatsVentilador sensor(boolean activar) {
        return this;
    } // no fem res

    @Override
    public String getTextEstat() {
        return "Apagat";
    }

}
