package e.eloifilaudeau.td6;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class View3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view3);

        final TextView title = (TextView) findViewById(R.id.title);

        final ImageView imageView = (ImageView) findViewById(R.id.img);
        final ImageView imageView2 = (ImageView) findViewById(R.id.img2);

        //recupere les infos de la vue 1
        Intent data = getIntent();
        final String name = data.getStringExtra("name");
        title.setText(name);

        //recherche de l'image
        JsonArray image = new JsonArray();

        //encode la string en url
        final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
        String urlEncoded = Uri.encode(name, ALLOWED_URI_CHARS);

        try {
            //remplacer la recherche de carte par la recherche single card
            image = Ion.with(View3.this)
                    .load("https://omgvamp-hearthstone-v1.p.mashape.com/cards/search/" + urlEncoded)
                    .setHeader("X-Mashape-Key", "rRP2HHWfXumsh6mBzqUmwogasTEsp1MCwgUjsnHVWqnYGd9InR")
                    .setHeader("Accept", "application/json")
                    .asJsonArray()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            Toast.makeText(View3.this, "RequestError : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Type arrayType = new TypeToken<ArrayList<JsonElement>>(){}.getType();

        final ArrayList<JsonElement> valeur = new Gson().fromJson(image, arrayType);
        String res = null;
        String resG = null;
        for (JsonElement card : valeur) {
            try {
                res = card.getAsJsonObject().get("img").getAsString();
                resG = card.getAsJsonObject().get("imgGold").getAsString();
            }catch (Exception e) {
                Picasso.with(getApplicationContext()).load("http://herbalife.ida.bg/404_page_not_found.png").into(imageView);
                Picasso.with(getApplicationContext()).load("http://herbalife.ida.bg/404_page_not_found.png").into(imageView2);
            }
        }

        imageView2.setVisibility(View.INVISIBLE);

        if (res == null) {
            Picasso.with(getApplicationContext()).load(R.drawable.blank).into(imageView);
            Picasso.with(getApplicationContext()).load(R.drawable.blank).into(imageView2);
//        } else if (res.contains("original")){
//            Picasso.with(getApplicationContext()).load("http://herbalife.ida.bg/404_page_not_found.png").into(imageView);
//            Picasso.with(getApplicationContext()).load("http://herbalife.ida.bg/404_page_not_found.png").into(imageView2);
        } else {
            Picasso.with(getApplicationContext()).load(res).into(imageView);
            Picasso.with(getApplicationContext()).load(resG).into(imageView2);
        }

        //affichage player class
        TextView playerClass = (TextView) findViewById(R.id.subtitle);
        for (JsonElement card : valeur) {
            try {
                res = card.getAsJsonObject().get("playerClass").getAsString();
            }catch (Exception e) {
                playerClass.setText("undefined");
            }
        }
        playerClass.setText(res);
        //affichage infos carte
        TextView type = (TextView) findViewById(R.id.tv1);
        for (JsonElement card : valeur) {
            try {
                res = card.getAsJsonObject().get("type").getAsString();
                type.setText("Type : "+res);
            }catch (Exception e) {
                type.setText("Type : none");
            }
        }
        TextView cost = (TextView) findViewById(R.id.tv2);
        for (JsonElement card : valeur) {
            try {
                res = card.getAsJsonObject().get("cost").getAsString();
                cost.setText("Cost : "+res);
            }catch (Exception e) {
                cost.setText("Cost : none");
            }
        }
        TextView health = (TextView) findViewById(R.id.tv3);
        for (JsonElement card : valeur) {
            try {
                res = card.getAsJsonObject().get("health").getAsString();
                health.setText("Health : "+res);
            }catch (Exception e) {
                health.setText("Health : none");
            }
        }
        TextView rarity = (TextView) findViewById(R.id.tv4);
        for (JsonElement card : valeur) {
            try {
                res = card.getAsJsonObject().get("rarity").getAsString();
                rarity.setText("Rarity : "+res);
            }catch (Exception e) {
                rarity.setText("Rarity : none");
            }
        }

        //cartes gold et normales
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView2.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView2.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }
        });



        //bouton ajout deck
        final Button button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lire fichier
                FileInputStream input = null;
                InputStreamReader reader = null;
                char[] inputBuffer = new char[255];
                String data = null;
                try{
                    input = openFileInput("test2.txt");
                    reader = new InputStreamReader(input);
                    reader.read(inputBuffer);
                    data = new String(inputBuffer);

                   // Toast.makeText(View3.this, data, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(View3.this, "File is not read", Toast.LENGTH_SHORT).show();
                }

                //ecrire fichier
                FileOutputStream output = null;
                String s = name + "-" + data;
                try {
                    output = openFileOutput("test2.txt", MODE_PRIVATE);
                    output.write(s.getBytes());
                    if(output != null)
                        output.close();
                } catch (FileNotFoundException e) {
                    Toast.makeText(View3.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(View3.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                Intent envoi = new Intent(View3.this, View4.class);
                View3.this.startActivity(envoi);
            }
        });



    }
}
