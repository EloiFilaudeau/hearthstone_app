package e.eloifilaudeau.td6;

        import android.app.DownloadManager;
        import android.content.Intent;
        import android.graphics.Color;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.constraint.ConstraintLayout;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Adapter;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.ScrollView;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.gson.Gson;
        import com.google.gson.JsonArray;
        import com.google.gson.JsonElement;
        import com.google.gson.JsonObject;
        import com.google.gson.reflect.TypeToken;
        import com.koushikdutta.ion.Ion;
        import com.squareup.picasso.Picasso;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.lang.reflect.Type;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.concurrent.ExecutionException;

public class View2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);

        //recupere les infos de la vue 1
        Intent data = getIntent();
        final String choice = data.getStringExtra("choice");
        final String value = data.getStringExtra("tab");

        //requete pour recuperer toutes les cartes de tel choix
        JsonArray result = new JsonArray();
        try {
            result = Ion.with(View2.this)
                    .load("https://omgvamp-hearthstone-v1.p.mashape.com/cards/"+choice+"/"+value)
                    .setHeader("X-Mashape-Key", "rRP2HHWfXumsh6mBzqUmwogasTEsp1MCwgUjsnHVWqnYGd9InR")
                    .setHeader("Accept", "application/json")
                    .asJsonArray()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            Toast.makeText(View2.this, "RequestError : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //stockage dans un liste de type tableau
        Type arrayType = new TypeToken<ArrayList<JsonElement>>(){}.getType();
        ArrayList<JsonElement> valeurs = new Gson().fromJson(result, arrayType);

        //titre de la recherche
        TextView title = (TextView) findViewById(R.id.value);
        title.setText(value);

        final ListView layout = (ListView) findViewById(R.id.listView); //layout récupéré
        String s = "";
        ArrayList<String> liste = new ArrayList<String>();

        for(JsonElement card: valeurs) {

            s = card.getAsJsonObject().get("name").getAsString();
            liste.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                liste
        );
        layout.setAdapter(adapter);

            layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {

                    String s = (String) layout.getItemAtPosition(i);
                    Toast.makeText(View2.this, s, Toast.LENGTH_SHORT).show(); //affichage

                    //envoi de la valeur a la vue 3 pour la recherche de l'image
                    Intent envoi = new Intent(View2.this, View3.class);
                    envoi.putExtra("name", s);
                    View2.this.startActivity(envoi);
                }
            });


    }
}