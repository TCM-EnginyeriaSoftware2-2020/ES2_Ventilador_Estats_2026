package domini.patroEstats;

public class ActiuSenseVentiladorExtrafort extends Actiu{

    public ActiuSenseVentiladorExtrafort(long initialTime){super(initialTime);}

    @Override
    public IEstatsVentilador sensor(boolean activar) {
        if( super.isOver() )
            return Inactiu.getEstatUnic();
        if(activar)
            return new ActiuAmbVentiladorExtrafort(this.initTime);
        return this;
    }
    @Override
    public IEstatsVentilador botoVentilador() {
        return Inactiu.getEstatUnic();
    }

    @Override
    public String getTextEstat() {
        return "Ventilador en repós (extrafort)"+ " | Temporitzador: " + super.getTextTemporitzador();
    }
}
