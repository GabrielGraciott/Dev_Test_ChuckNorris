package br.com.testechucknorris.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.com.testechucknorris.R
import br.com.testechucknorris.model.SearchModel
import br.com.testechucknorris.service.ApiService
import br.com.testechucknorris.service.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private val listView: ListView
        get() = findViewById(R.id.lv_listView)

    private val inputText: EditText
        get() = findViewById(R.id.textInputEditText)

    private val imageListIcon: ImageView
        get() = findViewById(R.id.imageListIcon)

    private val backButton: ImageView
        get() = findViewById(R.id.backButton)


    private val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                imageListIcon.visibility = View.GONE
                return
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun afterTextChanged(p0: Editable?) {
                searchJoke()
            }
        })
    }


    private fun searchJoke() {
        val call = serviceGenerator.getJokes(inputText.text.toString())

        call.clone().enqueue(object : Callback<SearchModel> {
            override fun onResponse(
                call: Call<SearchModel>,
                response: Response<SearchModel>
            ) {
                imageListIcon.visibility = View.GONE

                var data = mutableListOf<String>()

                response.body()?.result?.map {
                    data.add(it.value)
                }
                val adapter = ArrayAdapter(baseContext, android.R.layout.simple_list_item_1, data)
                listView.adapter = adapter
                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                        val intent = Intent(this@SearchActivity, JokeActivity::class.java).also {
                            it.putExtra("Joke", data[p2])
                        }
                        startActivity(intent)
                    }
            }

            override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                t.printStackTrace()
                Log.e("Error", t.message.toString())
            }
        })
    }
}