package com.example.mygatoedher;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView PuntajeJugadorUno;
    private TextView PuntajeJugadorDos;
    private TextView estado;
    private Button[] botones = new Button[9];
    private Button reiniciarJuego;
    private int ContPuntajeJugadorUno;
    private int ContPuntajeJugadorDos;
    private int contRonda;
    boolean jugadorActivo;
    int [] estadoDeJuego = {2,2,2,2,2,2,2,2,2};
    int [][] posicionesGanadoras = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PuntajeJugadorUno = (TextView) findViewById(R.id.PuntajeJugadorUno);
        PuntajeJugadorDos = (TextView) findViewById(R.id.PuntajeJugadorDos);
        estado = (TextView) findViewById(R.id.estado);

        reiniciarJuego = (Button) findViewById(R.id.reiniciarJuego);

        for(int i = 0; i < botones.length; i++){
            String botonId = "btn_" + i;
            int resourceId = getResources().getIdentifier(botonId, "id", getPackageName());
            botones[i] = (Button) findViewById(resourceId);
            botones[i].setOnClickListener(this);
        }

        contRonda = 0;
        ContPuntajeJugadorUno = 0;
        ContPuntajeJugadorDos = 0;
        jugadorActivo = true;

    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String botonId = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(botonId.substring(botonId.length()-1, botonId.length()));

        if(jugadorActivo){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FF5733"));
            estadoDeJuego[gameStatePointer] = 0;
        } else{
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#900C3F"));
            estadoDeJuego[gameStatePointer] = 1;
        }
        contRonda++;
        if(checarGanador()){
            if(jugadorActivo){
                ContPuntajeJugadorUno++;
                actualizarPuntaje();
                Toast.makeText(this, "¡Ganó Jugador 1!", Toast.LENGTH_SHORT).show();
                jugarDeNuevo();
            }else {
                ContPuntajeJugadorDos++;
                actualizarPuntaje();
                Toast.makeText(this, "¡Ganó Jugador 2!", Toast.LENGTH_SHORT).show();
                jugarDeNuevo();
            }
        }else if(contRonda == 9){
            jugarDeNuevo();
            Toast.makeText(this, "Empate /:", Toast.LENGTH_SHORT).show();
        }else{
            jugadorActivo = !jugadorActivo;
        }

        if(ContPuntajeJugadorUno > ContPuntajeJugadorDos){
            estado.setText("El Jugador 1 va ganando :D");
        }else if(ContPuntajeJugadorDos > ContPuntajeJugadorUno){
            estado.setText("El Jugador 2 va ganando :D");
        }else{
            estado.setText("");
        }

        reiniciarJuego.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                jugarDeNuevo();
                ContPuntajeJugadorUno = 0;
                ContPuntajeJugadorDos = 0;
                estado.setText("");
                actualizarPuntaje();
            }
        });
    }

    public boolean checarGanador(){
        boolean resultado = false;

        for(int [] posicionesGanadoras : posicionesGanadoras){
            if(estadoDeJuego[posicionesGanadoras[0]] == estadoDeJuego[posicionesGanadoras[1]] && estadoDeJuego[posicionesGanadoras[1]] == estadoDeJuego[posicionesGanadoras[2]] && estadoDeJuego[posicionesGanadoras[0]] != 2){
                resultado = true;
            }
        }
        return resultado;
    }

    public void actualizarPuntaje(){
        PuntajeJugadorUno.setText(Integer.toString(ContPuntajeJugadorUno));
        PuntajeJugadorDos.setText(Integer.toString(ContPuntajeJugadorDos));
    }

    public void jugarDeNuevo(){
        contRonda = 0;
        jugadorActivo = true;

        for(int i = 0; i < botones.length; i++){
            estadoDeJuego[i] = 2;
            botones[i].setText("");
        }
    }
}