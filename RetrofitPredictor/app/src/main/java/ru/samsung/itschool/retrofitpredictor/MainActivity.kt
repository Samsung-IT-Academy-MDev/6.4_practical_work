package ru.samsung.itschool.retrofitpredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val PREDICTOR_URI_JSON: String = "https://predictor.yandex.net/"
    val PREDICTOR_KEY: String =
        "pdct.1.1.20160224T140053Z.cb27d8058bc81d29.10b405aa732895274b7d14c9f7a55a116a832d93"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(PREDICTOR_URI_JSON)
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service: RestApi = retrofit.create(RestApi::class.java);
        val editText: EditText = findViewById(R.id.et_text)
        val textView: TextView = findViewById(R.id.tv_predict)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val call: Call<Model> =
                    service.predict(PREDICTOR_KEY, editText.getText().toString(), "ru");
                call.enqueue(object:Callback<Model> {
                    override fun onResponse(call: Call<Model>, response: Response<Model>) {
                        try {
                            val textWord: String = response.body().text[0].toString();
                            textView.setText("Предиктор : " + textWord);
                        } catch (e: Exception) {
                            e.printStackTrace();
                        }
                    }
                    override fun onFailure(call: Call<Model>, t: Throwable) {
                    }
                });
            }
        })

    }
}