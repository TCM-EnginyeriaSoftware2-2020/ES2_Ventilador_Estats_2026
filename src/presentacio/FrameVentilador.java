package presentacio;


import domini.ControladorVentilador;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FrameVentilador extends JFrame {

    // ************* aquesta classe no es pot MODIFICAR *************

    private JLabel lblTempActual;
    private JLabel lblTempDeseada;
    private JLabel lblEstado;
    private final Random random = new Random();

    private ControladorVentilador controlador;

    public FrameVentilador() {

        controlador = new ControladorVentilador(25.0, 22.0); // temperatura actual y deseada inicial

        setTitle("Control de Ventilador");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel pantalla (simula LCD)
        JPanel pantalla = new JPanel();
        pantalla.setLayout(new GridLayout(3, 1));
        pantalla.setBackground(Color.BLACK);

        lblTempActual = new JLabel("Temp Actual: " , SwingConstants.CENTER);
        lblTempDeseada = new JLabel("Temp Desitjada: ", SwingConstants.CENTER);
        lblEstado = new JLabel("Estat:", SwingConstants.CENTER);
        actualizarPantalla();

        lblTempActual.setForeground(Color.GREEN);
        lblTempDeseada.setForeground(Color.GREEN);
        lblEstado.setForeground(Color.GREEN);

        pantalla.add(lblTempActual);
        pantalla.add(lblTempDeseada);
        pantalla.add(lblEstado);

        // Panel lateral izquierdo para subir/bajar
        JPanel lateralIzquierdo = new JPanel();
        lateralIzquierdo.setLayout(new GridLayout(2, 1, 5, 5));
        lateralIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnSubir = new JButton("▲");
        JButton btnBajar = new JButton("▼");

        lateralIzquierdo.add(btnSubir);
        lateralIzquierdo.add(btnBajar);

        // Panel botones inferiores para modo y activación
        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(1, 2, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnActivacio = new JButton("Activació");
        JButton btnVentilador = new JButton("Mode");

        botones.add(btnVentilador);
        botones.add(btnActivacio);

        // Acciones
        btnSubir.addActionListener(e -> {
            controlador.botoAugmentarTemperaturaDesitjada();
            actualizarPantalla();
        });

        btnBajar.addActionListener(e -> {
            controlador.botoDisminuirTemperaturaDesitjada();
            actualizarPantalla();
        });

        btnActivacio.addActionListener(e -> {
            controlador.botoActivacio();
            actualizarPantalla();
        });

        btnVentilador.addActionListener(e -> {
            controlador.botoVentilador();
            actualizarPantalla();
        });

        add(pantalla, BorderLayout.CENTER);
        add(lateralIzquierdo, BorderLayout.WEST);
        add(botones, BorderLayout.SOUTH);

        // Timer que cada segundo aplica una variación aleatoria a la temperatura del sensor
        Timer timer = new Timer(1000, e -> {
            double p = random.nextDouble();
            if (p > 0.75) {
                controlador.setTemperaturaSensor(+0.5); // 25% aumenta
            } else if (p > 0.5) {
                controlador.setTemperaturaSensor(-0.5); // 25% disminuye
            }else {
                controlador.setTemperaturaSensor(0); // 50% no varia
            }
            actualizarPantalla();
        });
        timer.start();
    }

    private void actualizarPantalla() {
        lblTempActual.setText("Temp Actual: " + controlador.getTemperaturaSensor() + "°C");
        lblTempDeseada.setText("Temp Desitjada: " + controlador.getTemperaturaDesitjada() + "°C");
        lblEstado.setText("Estat: " + controlador.getTextEstat() );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrameVentilador().setVisible(true);
        });
    }

}
