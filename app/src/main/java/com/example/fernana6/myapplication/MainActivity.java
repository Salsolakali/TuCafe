package com.example.fernana6.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.EditText.*;

public class MainActivity extends AppCompatActivity {

    private EditText inputName;
    private Button btnAdd, btnPlus, btnMinus, btnPedido, btnMaps;
    private TextView client, order, numberCoffees;
    private Integer coffees;
    private CheckBox milk;
    private CheckBox sugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = findViewById(R.id.inputNombre);

        client = findViewById(R.id.client);
        order = findViewById(R.id.pedido);
        numberCoffees = findViewById(R.id.number);

        btnMinus = findViewById(R.id.minus);
        btnPlus = findViewById(R.id.plus);
        btnAdd = findViewById(R.id.btnAnadir);
        btnPedido = findViewById(R.id.btnPedir);
        btnMaps = findViewById(R.id.btnMaps);

        sugar = findViewById(R.id.sugar);
        milk = findViewById(R.id.milk);

        coffees = Integer.parseInt(numberCoffees.getText().toString());

        inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                client.setText(editable.toString());
            }
        });

        btnPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                coffees +=1;
                numberCoffees.setText(coffees+"");
            }
        });
        btnMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coffees>0){
                    coffees-=1;
                    numberCoffees.setText(coffees+"");
                }
            }
        });
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                refrescaLaFactura(coffees, milk.isChecked(),sugar.isChecked());
            }
        });

        btnPedido.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido para tu cafe");
                intent.putExtra(Intent.EXTRA_TEXT, client.getText()+"\n"+order.getText());
                intent.setData(Uri.parse("mailto:default@recipient.com")); // or just "mailto:" for blank
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);
            }
        });

        btnMaps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /*PARA ABRIR EN GOOGLE MAPS
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);*/

                Intent activity = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(activity);
            }
        });

        }
        public void refrescaLaFactura(int coffees, boolean milk, boolean sugar){

            String pedido = order.getText().toString();
            this.coffees = coffees;
            Boolean noMebugees =false;
            if(coffees<=0){
                Toast.makeText(MainActivity.this, "Debes introducir al menos un cafe", Toast.LENGTH_SHORT).show();
                this.coffees = 0;
                return;
            }
            else if(coffees==1){
                pedido +="\n" + coffees + " cafe";
                noMebugees =true;
            }
            else{
                pedido +="\n" + coffees + " cafés";
                noMebugees =true;
            }

            if(milk && sugar && noMebugees){
                pedido+= " con leche y azúcar.";
            }
            else{
                if(milk){
                    pedido+= " con leche.";
                }
                else if(sugar){
                    pedido+= " con azúcar.";
                }
                else{
                    if(coffees>0){
                        pedido += ".";
                    }

                }
            }
            order.setText(pedido);
        }

    }


