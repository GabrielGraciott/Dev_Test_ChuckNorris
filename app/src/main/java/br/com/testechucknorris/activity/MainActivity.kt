package br.com.testechucknorris.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.testechucknorris.R
import br.com.testechucknorris.model.JokeModel
import br.com.testechucknorris.service.ApiService
import br.com.testechucknorris.service.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.getRandomJokes()

        val randomJoke : TextView = findViewById(R.id.randomJokeText)
        val buttonOpen : Button = findViewById(R.id.btn_search_fragment)
        val imageShuffle : ImageView = findViewById(R.id.shuffleImage)

        call.enqueue(object : Callback<JokeModel> {
            override fun onResponse(call: Call<JokeModel>, response: Response<JokeModel>) {
                randomJoke.text = response.body()?.value.toString()
            }

            override fun onFailure(call: Call<JokeModel>, t: Throwable) {
                t.printStackTrace()
                Log.e("Error", t.message.toString())
            }
        })

        buttonOpen.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        imageShuffle.setOnClickListener {
            call.clone().enqueue(object : Callback<JokeModel> {
                override fun onResponse(call: Call<JokeModel>, response: Response<JokeModel>) {
                    randomJoke.text = response.body()?.value.toString()
                }

                override fun onFailure(call: Call<JokeModel>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("Error", t.message.toString())
                }
            })
        }
    }
}