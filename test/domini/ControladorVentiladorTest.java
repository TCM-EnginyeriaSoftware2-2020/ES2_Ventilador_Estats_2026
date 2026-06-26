package domini;

import domini.patroEstats.Actiu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControladorVentiladorTest {

    private ControladorVentilador controlador;
    private double temperaturaSensor;
    private double temperaturaDesitjada;
    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        temperaturaSensor = random.nextDouble(25,30);
        temperaturaDesitjada = random.nextDouble(20,25);
        controlador = new ControladorVentilador(temperaturaSensor, temperaturaDesitjada);
    }

    @Test
    void estatInicial() {
        // estat inicial
        assertEquals("Apagat", controlador.getTextEstat());
        assertEquals(temperaturaSensor, controlador.getTemperaturaSensor());
        assertEquals(temperaturaDesitjada, controlador.getTemperaturaDesitjada());
        // segona instància independent
        ControladorVentilador controlador2 = new ControladorVentilador(temperaturaSensor, temperaturaDesitjada);
        assertEquals("Apagat", controlador2.getTextEstat());
        controlador2.botoActivacio();
        assertTrue(controlador2.getTextEstat().contains("Ventilador actiu"));
        assertEquals("Apagat", controlador.getTextEstat());
    }

    @Test
    void estatApagat() {
        assertEquals("Apagat", controlador.getTextEstat());
        controlador.botoDisminuirTemperaturaDesitjada();
        assertEquals("Apagat", controlador.getTextEstat());
        controlador.botoAugmentarTemperaturaDesitjada();
        assertEquals("Apagat", controlador.getTextEstat());
        controlador.setTemperaturaSensor(0);
        assertEquals("Apagat", controlador.getTextEstat());
        controlador.botoVentilador();
        assertEquals("Apagat", controlador.getTextEstat());
    }

    @Test
    void botoVentilador() {
        assertEquals("Apagat", controlador.getTextEstat());
        controlador.botoActivacio();
        assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
        controlador.botoVentilador();
        assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
        assertTrue(controlador.getTextEstat().contains("extrafort"));
        controlador.botoVentilador();
        assertEquals("Apagat", controlador.getTextEstat());
    }

    @Test
    void botoActivacioTemporització() {
        assertEquals("Apagat", controlador.getTextEstat());
        controlador.botoActivacio();
        assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
        // el ventilador s'ha d'apagar automàticament després de DURATION
        try {
            Thread.sleep(Actiu.DURATION+10);
            controlador.setTemperaturaSensor(0);
            assertEquals("Apagat", controlador.getTextEstat());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // si tornem a prémer el botó del ventilador després de DURATION, hauria de reiniciar el temps
        try {
            controlador.botoActivacio();
            assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
            Thread.sleep(Actiu.DURATION/2);
            controlador.setTemperaturaSensor(0);
            assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
            controlador.botoActivacio();
            assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
            Thread.sleep(Actiu.DURATION/2);
            controlador.setTemperaturaSensor(0);
            assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
            Thread.sleep(Actiu.DURATION/2);
            controlador.setTemperaturaSensor(0);
            assertEquals("Apagat", controlador.getTextEstat());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void cambiosEstadosPorTemperatura() {
        // Es necessari estiu per que la lògica sigui: temperaturaSensor > temperaturaDesitjada = activarVentilador
        AireCondicionat.setEstiu();
        
        // temperaturaSensor inicial > temperaturaDesitjada (activarVentilador = true)
        temperaturaSensor = 30;
        temperaturaDesitjada = 25;
        controlador = new ControladorVentilador(temperaturaSensor, temperaturaDesitjada);
        
        // Inactiu
        assertEquals("Apagat", controlador.getTextEstat());
        
        // 1. Inactiu → ActiuAmbVentilador (botoActivacio)
        controlador.botoActivacio();
        assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));

        // 2. ActiuAmbVentilador → ActiuSenseVentilador (sensor activar=false)
        // Disminuir temperatura perquè activarVentilador() retorni false
        controlador.setTemperaturaSensor(-10); // 20 graus, menor que 25
        assertTrue(controlador.getTextEstat().contains("Ventilador en repós"));
        assertFalse(controlador.getTextEstat().contains("extrafort"));
        
        // 3. ActiuSenseVentilador → ActiuAmbVentilador (sensor activar=true)
        // Aumentar temperatura perquè activarVentilador() retorni true
        controlador.setTemperaturaSensor(15); // 35 graus, mayor que 25
        assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
        assertFalse(controlador.getTextEstat().contains("extrafort"));
        assertFalse(controlador.getTextEstat().contains("repós"));

        // 4. ActiuAmbVentilador → ActiuAmbVentiladorExtrafort (botoVentilador)
        controlador.botoVentilador();
        assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
        assertTrue(controlador.getTextEstat().contains("extrafort"));
        assertFalse(controlador.getTextEstat().contains("repós"));
        
        // 5. ActiuAmbVentiladorExtrafort → ActiuSenseVentiladorExtrafort (sensor activar=false)
        // Disminuir temperatura perquè activarVentilador() retorni false
        controlador.setTemperaturaSensor(-15); // 20 graus, menor que 25
        assertTrue(controlador.getTextEstat().contains("Ventilador en repós"));
        assertTrue(controlador.getTextEstat().contains("extrafort"));
        
        // 6. ActiuSenseVentiladorExtrafort → Inactiu (botoVentilador)
        controlador.botoVentilador();
        assertEquals("Apagat", controlador.getTextEstat());
    }

    @Test
    void cambiosEstadosPorTemperaturaDesitjada() {
        // Es necessari estiu per que la lògica sigui: temperaturaSensor > temperaturaDesitjada = activarVentilador
        AireCondicionat.setEstiu();
        
        // temperaturaSensor inicial > temperaturaDesitjada (activarVentilador = true)
        temperaturaSensor = 30;
        temperaturaDesitjada = 25;
        controlador = new ControladorVentilador(temperaturaSensor, temperaturaDesitjada);
        
        // Inactiu
        assertEquals("Apagat", controlador.getTextEstat());
        
        // 1. Inactiu → ActiuAmbVentilador (botoActivacio)
        controlador.botoActivacio();
        assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
        assertFalse(controlador.getTextEstat().contains("extrafort"));
        
        // 2. ActiuAmbVentilador → ActiuSenseVentilador (aumentar temperaturaDesitjada para que activarVentilador = false)
        // Aumentar temperaturaDesitjada a 35 (mayor que 30) → temperaturaSensor <= temperaturaDesitjada
        for (int i = 0; i < 20; i++) {
            controlador.botoAugmentarTemperaturaDesitjada();
        }
        assertTrue(controlador.getTextEstat().contains("Ventilador en repós"));
        assertFalse(controlador.getTextEstat().contains("extrafort"));
        
        // 3. ActiuSenseVentilador → ActiuAmbVentilador (disminuir temperaturaDesitjada para que activarVentilador = true)
        // Disminuir temperaturaDesitjada a 25 (menor que 30) → temperaturaSensor > temperaturaDesitjada
        for (int i = 0; i < 20; i++) {
            controlador.botoDisminuirTemperaturaDesitjada();
        }
        assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
        assertFalse(controlador.getTextEstat().contains("extrafort"));
        assertFalse(controlador.getTextEstat().contains("repós"));
        
        // 4. ActiuAmbVentilador → ActiuAmbVentiladorExtrafort (botoVentilador)
        controlador.botoVentilador();
        assertTrue(controlador.getTextEstat().contains("Ventilador actiu"));
        assertTrue(controlador.getTextEstat().contains("extrafort"));
        assertFalse(controlador.getTextEstat().contains("repós"));
        
        // 5. ActiuAmbVentiladorExtrafort → ActiuSenseVentiladorExtrafort (aumentar temperaturaDesitjada para que activarVentilador = false)
        for (int i = 0; i < 20; i++) {
            controlador.botoAugmentarTemperaturaDesitjada();
        }
        assertTrue(controlador.getTextEstat().contains("Ventilador en repós"));
        assertTrue(controlador.getTextEstat().contains("extrafort"));
        
        // 6. ActiuSenseVentiladorExtrafort → Inactiu (botoVentilador)
        controlador.botoVentilador();
        assertEquals("Apagat", controlador.getTextEstat());
    }
    



}