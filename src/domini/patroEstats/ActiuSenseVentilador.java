package domini.patroEstats;

public class ActiuSenseVentilador extends Actiu{

    public ActiuSenseVentilador(long initialTime){super(initialTime);}

    @Override
    public IEstatsVentilador sensor(boolean activar) {
        if( super.isOver() )
            return Inactiu.getEstatUnic();
        if(activar)
            return new ActiuAmbVentilador(this.initTime);
        return this;
    }
    @Override
    public IEstatsVentilador botoVentilador() {
        if( super.isOver() )
            return Inactiu.getEstatUnic();
        return new ActiuSenseVentiladorExtrafort(initTime);
    }

    @Override
    public String getTextEstat() {
        return "Ventilador en repós"+ " | Temporitzador: " + super.getTextTemporitzador();
    }
}
