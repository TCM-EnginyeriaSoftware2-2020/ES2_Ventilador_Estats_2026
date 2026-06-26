package domini;

import domini.patroEstats.IEstatsVentilador;

public class ControladorVentilador {

    private double temperaturaSensor;
    private double temperaturaDesitjada;
    private IEstatsVentilador estat;

    public ControladorVentilador(double temperaturaSensor, double temperaturaDesitjada)
    {
        this.temperaturaDesitjada = temperaturaDesitjada;
        this.temperaturaSensor = temperaturaSensor;
        this.estat = IEstatsVentilador.getEstatInicial();
    }

    public double getTemperaturaSensor() {
        return temperaturaSensor;
    }
    public double getTemperaturaDesitjada() {
        return temperaturaDesitjada;
    }
    public String getTextEstat() {
        return this.estat.getTextEstat();
    }

    public void setTemperaturaSensor( double variacion )
    {
        this.temperaturaSensor += variacion;
        this.estat = this.estat.sensor(activarVentilador());
    }

    private boolean activarVentilador() // aquí podríem fer un patró estratègia
    {
        if( AireCondicionat.isEstiu())
            return this.temperaturaSensor > this.temperaturaDesitjada;
        return this.temperaturaSensor < this.temperaturaDesitjada;
    }

    public void botoActivacio() {
        this.estat = this.estat.botoActivacio();
    }
    public void botoVentilador() {
        this.estat = this.estat.botoVentilador();
    }
    public void botoAugmentarTemperaturaDesitjada() {
        this.temperaturaDesitjada += 0.5;
        this.estat = this.estat.sensor(activarVentilador());
    }
    public void botoDisminuirTemperaturaDesitjada() {
        this.temperaturaDesitjada -= 0.5;
        this.estat = this.estat.sensor(activarVentilador());
    }
}
