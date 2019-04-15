package e.eloifilaudeau.td6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class View1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view1);

        String key = "rRP2HHWfXumsh6mBzqUmwogasTEsp1MCwgUjsnHVWqnYGd9InR";

        final Button button = (Button) findViewById(R.id.btn1);

        final Spinner class_spinner = (Spinner) findViewById(R.id.spinner1);
        final Spinner type_spinner = (Spinner) findViewById(R.id.spinner2);
        final Spinner faction_spinner = (Spinner) findViewById(R.id.spinner3);
        final Spinner race_spinner = (Spinner) findViewById(R.id.spinner4);
        final EditText name = (EditText) findViewById(R.id.name);

        //spinner1
        final ArrayAdapter<CharSequence> class_adapter = ArrayAdapter.createFromResource(View1.this, R.array.cards_class, android.R.layout.simple_spinner_item);// Create an ArrayAdapter using the string array and a default spinner layout
        class_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        class_spinner.setAdapter(class_adapter);// Apply the adapter to the spinner

        //spinner2
        final ArrayAdapter<CharSequence> type_adapter = ArrayAdapter.createFromResource(this, R.array.cards_type, android.R.layout.simple_spinner_item);
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(type_adapter);

        //spinner3
        final ArrayAdapter<CharSequence> faction_adapter = ArrayAdapter.createFromResource(this, R.array.cards_faction, android.R.layout.simple_spinner_item);
        faction_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        faction_spinner.setAdapter(faction_adapter);

        //spinner4
        final ArrayAdapter<CharSequence> race_adapter = ArrayAdapter.createFromResource(this, R.array.cards_race, android.R.layout.simple_spinner_item);
        race_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        race_spinner.setAdapter(race_adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(View1.this, "Recherche en cours...", Toast.LENGTH_SHORT).show();
                //recupère la selection
                final String tab[] = new String[4];
                tab[0] = class_spinner.getSelectedItem().toString();
                tab[1] = type_spinner.getSelectedItem().toString();
                tab[2] = faction_spinner.getSelectedItem().toString();
                tab[3] = race_spinner.getSelectedItem().toString();

                //choix si vide on passe au suivant, recherche possible avec un seul critère
                if (name.getText().toString().equals("")==false){
                    String nameS = name.getText().toString();
                    Intent envoi = new Intent(View1.this, View3.class);
                    envoi.putExtra("name", nameS);
                    View1.this.startActivity(envoi);
                } else if (tab[0].toString().equals("-")==false) {
                    Intent envoi = new Intent(View1.this, View2.class);
                    envoi.putExtra("choice", "classes");
                    envoi.putExtra("tab", tab[0].toString());
                    View1.this.startActivity(envoi);
                } else if (tab[1].toString().equals("-")==false) {
                    Intent envoi = new Intent(View1.this, View2.class);
                    envoi.putExtra("choice", "types");
                    envoi.putExtra("tab", tab[1].toString());
                    View1.this.startActivity(envoi);
                } else if (tab[2].toString().equals("-")==false) {
                    Intent envoi = new Intent(View1.this, View2.class);
                    envoi.putExtra("choice", "factions");
                    envoi.putExtra("tab", tab[2].toString());
                    View1.this.startActivity(envoi);
                } else if (tab[3].toString().equals("-")==false) {
                    Intent envoi = new Intent(View1.this, View2.class);
                    envoi.putExtra("choice", "races");
                    envoi.putExtra("tab", tab[3].toString());
                    View1.this.startActivity(envoi);
                } else {
                    Toast.makeText(View1.this, "Veuillez faire un choix.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
