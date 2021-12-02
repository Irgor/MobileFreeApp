package com.example.lolteambuilder

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.net.URI
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

data class data(
    val name: String,
    val info: info,
    val image: image,
    val tags: JSONArray,
)

data class  image (
    val full: String,
)

data class info (
    val difficulty: Number
)

class MainActivity : AppCompatActivity() {

    val API_URL = "https://ddragon.leagueoflegends.com/cdn/6.24.1/data/pt_BR/champion.json";
    val IMG_URL = "https://ddragon.leagueoflegends.com/cdn/11.23.1/img/champion/"

    var champsData = ArrayList<data>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData();

        val btnGerar = findViewById<Button>(R.id.btnGerar);

        val champ1 = findViewById<TextView>(R.id.champ1);
        val img1 = findViewById<ImageView>(R.id.imgChamp1);

        val champ2 = findViewById<TextView>(R.id.champ2);
        val img2 = findViewById<ImageView>(R.id.imgChamp2);

        val champ3 = findViewById<TextView>(R.id.champ3);
        val img3 = findViewById<ImageView>(R.id.imgChamp3);

        val champ4 = findViewById<TextView>(R.id.champ4);
        val img4 = findViewById<ImageView>(R.id.imgChamp4);

        val champ5 = findViewById<TextView>(R.id.champ5);
        val img5 = findViewById<ImageView>(R.id.imgChamp5);


        btnGerar.onClick {
            var generatedValues = ArrayList<Number>();
            var generatedChamps = ArrayList<data>();

            repeat(5) {
                var value = Math.floor(Math.random() * (champsData.size - 1)).toInt();
                while(generatedValues.contains(value)) {
                    value = Math.floor(Math.random() * (champsData.size - 1)).toInt();
                }

                generatedValues.add(value);
                generatedChamps.add(champsData.get(value));
            }

            champ1.setText("Campeão 1: " + generatedChamps.get(0).name);
            Picasso.get().load(IMG_URL + generatedChamps.get(0).image.full).into(img1);

            champ2.setText("Campeão 2: " + generatedChamps.get(1).name);
            Picasso.get().load(IMG_URL + generatedChamps.get(1).image.full).into(img2);

            champ3.setText("Campeão 3: " + generatedChamps.get(2).name);
            Picasso.get().load(IMG_URL + generatedChamps.get(2).image.full).into(img3);

            champ4.setText("Campeão 4: " + generatedChamps.get(3).name);
            Picasso.get().load(IMG_URL + generatedChamps.get(3).image.full).into(img4);

            champ5.setText("Campeão 5: " + generatedChamps.get(4).name);
            Picasso.get().load(IMG_URL + generatedChamps.get(4).image.full).into(img5);
        }
    }

    fun getData() {

        doAsync {
            val r = URL(API_URL).readText();
            val json = JSONObject(r).getJSONObject("data")

            val keys = json.keys();

            keys.forEach {
                var jsonChamp = json.getJSONObject(it);
                val dataChamp = data(
                    name = jsonChamp.getString("name"),
                    info = info(jsonChamp.getJSONObject("info").getInt("difficulty")),
                    image = image(jsonChamp.getJSONObject("image").getString("full")),
                    tags = jsonChamp.getJSONArray("tags")
                )
                champsData.add(dataChamp);
            }
        }
    }
}
