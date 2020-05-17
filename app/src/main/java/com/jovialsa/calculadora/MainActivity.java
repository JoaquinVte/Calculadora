package com.jovialsa.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDividir;
    private Button btnMultiplicar;
    private Button btnRestar;
    private Button btnSumar;
    private Button btnIgual;
    private Button btnComa;
    private Button btnCambioSigno;
    private TextView display;
    private int operacion;
    private Double operandoA;

    private final static int NINGUNA = -1;
    private final static int DIVISION = 0;
    private final static int MULTIPLICACION = 1;
    private final static int RESTA = 2;
    private final static int SUMA = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Incializamos los elementos de la vista
        inicializar();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mimenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int p = android.os.Process.myPid();
        android.os.Process.killProcess(p);
        return true;
    }
    private void inicializar() {

        btnDividir = (Button) findViewById(R.id.btnDividir);
        btnMultiplicar = (Button) findViewById(R.id.btnMultiplicar);
        btnRestar = (Button) findViewById(R.id.btnRestar);
        btnSumar = (Button) findViewById(R.id.btnSumar);
        btnIgual = (Button) findViewById(R.id.btnIgual);
        btnComa = (Button) findViewById(R.id.btnComa);
        btnCambioSigno = (Button) findViewById(R.id.btnCambioSigno);
        display = (TextView) findViewById(R.id.display);

        operacion = NINGUNA;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnC:
                display.setText("0");
                operacion = NINGUNA;
                operandoA = 0d;
                break;
            case R.id.btnRetroceso:
                if(display.getText().toString().replaceAll("-","").length()>1)
                    display.setText(display.getText().toString().substring(0,display.getText().toString().length()-1));
                else if (display.getText().toString().replaceAll("-","").length()==1)
                    display.setText("0");
                break;
            case R.id.btnDividir:
                cargarOperando(DIVISION);
                break;
            case R.id.btnMultiplicar:
                cargarOperando(MULTIPLICACION);
                break;
            case R.id.btnRestar:
                cargarOperando(RESTA);
                break;
            case R.id.btnSumar:
                cargarOperando(SUMA);
                break;
            case R.id.btnIgual:
                realizarCalculo();
                break;
            case R.id.btnComa:
                if (!display.getText().toString().contains("."))
                    display.setText(display.getText().toString() + ".");
                break;
            case R.id.btnCambioSigno:
                String visualizado = display.getText().toString();
                if(visualizado.startsWith("-"))
                    display.setText(visualizado.substring(1,visualizado.length()));
                else
                    display.setText("-"+visualizado);
                break;
            default:
                if (Double.parseDouble(display.getText().toString()) == 0 && !display.getText().toString().contains(".")) {
                    display.setText(((Button) findViewById(v.getId())).getText());
                } else {
                    display.setText(display.getText().toString() + ((Button) findViewById(v.getId())).getText());
                }
                break;
        }
    }

    private void realizarCalculo() {
        if (operacion == NINGUNA)
            Toast.makeText(MainActivity.this, "Debe especificar primero la operacion", Toast.LENGTH_SHORT).show();
        else {

            Double operandoB = Double.parseDouble(display.getText().toString());

            try {
                switch (operacion) {
                    case SUMA:
                        display.setText(String.valueOf(operandoA + operandoB));
                        break;
                    case RESTA:
                        display.setText(String.valueOf(operandoA - operandoB));
                        break;
                    case MULTIPLICACION:
                        display.setText(String.valueOf(operandoA * operandoB));
                        break;
                    case DIVISION:
                        display.setText(String.valueOf(operandoA / operandoB));
                        break;
                }

                limpiar();

                operacion = NINGUNA;
                operandoA = 0d;
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Se ha producido un error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void limpiar() {
        if (display.getText().toString().endsWith(".0"))
            display.setText(display.getText().toString().replaceAll(".0", ""));
    }

    private void cargarOperando(int op) {
        operandoA = Double.parseDouble(display.getText().toString());
        display.setText("0");
        operacion = op;
    }
}



