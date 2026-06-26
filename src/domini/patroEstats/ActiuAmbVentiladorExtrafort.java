package domini.patroEstats;

public class ActiuAmbVentiladorExtrafort extends Actiu{

    public ActiuAmbVentiladorExtrafort(long initialTime){super(initialTime);}

    @Override
    public IEstatsVentilador sensor(boolean activar) {
        if( super.isOver() )
            return Inactiu.getEstatUnic();
        if( activar )
            return this; // no fem res
        return new ActiuSenseVentiladorExtrafort(initTime);
    }
    @Override
    public IEstatsVentilador botoVentilador() {
        return Inactiu.getEstatUnic();
    }

    @Override
    public String getTextEstat() {
        return "Ventilador actiu (extrafort)" + " | Temporitzador: " + super.getTextTemporitzador();
    }
}
