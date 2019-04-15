package e.eloifilaudeau.td6;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class View4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view4);

        final ListView listView = (ListView) findViewById(R.id.listView);

        //lire fichier
        FileInputStream input = null;
        InputStreamReader reader = null;
        char[] inputBuffer = new char[1000];
        String data = "";
        try{
            input = openFileInput("test2.txt");
            reader = new InputStreamReader(input);
            reader.read(inputBuffer);
            data = new String(inputBuffer);
            //Toast.makeText(View4.this, data,Toast.LENGTH_SHORT).show();

            String[] splitArray = null; //tableau de chaînes

            // On découpe la chaîne "str" à traiter et on récupère le résultat dans le tableau "splitArray"
            splitArray = data.split("-");

            // On affiche chaque élément du tableau
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    splitArray
            );
            listView.setAdapter(adapter);

        }
        catch (Exception e) {
            Toast.makeText(View4.this, "Settings not read", Toast.LENGTH_SHORT).show();
        }




        //ecrire fichier
        FileOutputStream output = null;
        String s = data;
        try {
            output = openFileOutput("test2.txt", MODE_PRIVATE);
            output.write(s.getBytes());
            if(output != null)
                output.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(View4.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(View4.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retour = new Intent(View4.this, View1.class);
                View4.this.startActivity(retour);
            }
        });

    }
}
