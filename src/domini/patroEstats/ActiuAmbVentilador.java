package domini.patroEstats;

public class ActiuAmbVentilador extends Actiu{

    public ActiuAmbVentilador(){ super(); }
    public ActiuAmbVentilador(long initialTime){super(initialTime);}

    @Override
    public IEstatsVentilador sensor(boolean activar) {
        if( super.isOver() )
            return Inactiu.getEstatUnic();
        if( activar )
            return this; // no fem res
        return new ActiuSenseVentilador(initTime);
    }
    @Override
    public IEstatsVentilador botoVentilador() {
        if( super.isOver() )
            return Inactiu.getEstatUnic();
        return new ActiuAmbVentiladorExtrafort(initTime);
    }

    @Override
    public String getTextEstat() {
        return "Ventilador actiu" + " | Temporitzador: " + super.getTextTemporitzador();
    }
}
