package domini.patroEstats;

public interface IEstatsVentilador {
    public abstract IEstatsVentilador botoActivacio();
    public abstract IEstatsVentilador botoVentilador();
    public abstract IEstatsVentilador sensor(boolean activar);

    public abstract String getTextEstat();

    public static IEstatsVentilador getEstatInicial(){ return Inactiu.getEstatUnic(); }
}
