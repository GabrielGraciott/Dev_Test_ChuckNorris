package br.com.testechucknorris.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.testechucknorris.R

class JokeActivity : AppCompatActivity() {

    private val jokeTextView: TextView
        get() = findViewById(R.id.jokeTextView)

    private val backButton: ImageView
        get() = findViewById(R.id.backButton)

    private val closeButton: ImageView
        get() = findViewById(R.id.closeButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joke)

        closeButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            finish()
        }

        backButton.setOnClickListener { onBackPressed() }

        val joke = intent.getStringExtra("Joke")
        jokeTextView.setText(joke)
    }
}