package domini.patroEstats;

public abstract class Actiu implements IEstatsVentilador {
    public static long DURATION = 7000; // 7 segons
    // caldria posar 2 hores = 7200000 mil·lisegons
    protected long initTime;

    public Actiu( long initTime)
    {
        this.initTime = initTime;
    }
    public Actiu(){
        this(System.currentTimeMillis());
    }

    @Override
    public IEstatsVentilador botoActivacio() {
        initTime = System.currentTimeMillis(); // reiniciem el temps
        return this;
    }

    protected String getTextTemporitzador() {
        long timer = DURATION - (System.currentTimeMillis() - this.initTime);
        return timer / 1000 + " seg.";
    }

    protected boolean isOver(){
        return System.currentTimeMillis() - this.initTime > DURATION;
    }
}
