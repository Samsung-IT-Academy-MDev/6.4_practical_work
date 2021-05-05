package ru.samsung.itschool.phonebookclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    // здесь нужно указать URL проекта на Heroku
    val HEROKU_URL: String = "https://floating-anchorage-97173.herokuapp.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(HEROKU_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service: PhonebookController = retrofit.create(PhonebookController::class.java);
        val id: EditText = findViewById(R.id.id)
        val name: EditText = findViewById(R.id.name)
        val phone: EditText = findViewById(R.id.phone)
        val result: TextView = findViewById(R.id.result)
        val create: Button = findViewById(R.id.create)
        val update: Button = findViewById(R.id.update)
        val delete: Button = findViewById(R.id.delete)
        val read: Button = findViewById(R.id.read)

        read.setOnClickListener({
            service.read().enqueue(object: Callback<List<PhonebookEntry>>{
                override fun onResponse(call: Call<List<PhonebookEntry>>, response: Response<List<PhonebookEntry>>) {
                    val list=response.body()
                    result.text=""
                    for (entry in list.orEmpty()) {
                        result.text=result.text.toString() + entry.id+"\t"+entry.name+"\t"+entry.phone+"\n";
                    }
                }
                override fun onFailure(call: Call<List<PhonebookEntry>>, t: Throwable) {}
            })
        })

        create.setOnClickListener({
            service.create(PhonebookEntry(0,name.text.toString(),phone.text.toString())).enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    result.text=""+response.body()
                }
                override fun onFailure(call: Call<Boolean>, t: Throwable) { }
            })
        })

        delete.setOnClickListener({
            service.delete(PhonebookEntry(id.text.toString().toLong(),name.text.toString(),phone.text.toString())).enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    result.text=""+response.body()
                }
                override fun onFailure(call: Call<Boolean>, t: Throwable) { }
            })
        })

        update.setOnClickListener({
            service.update(PhonebookEntry(id.text.toString().toLong(),name.text.toString(),phone.text.toString())).enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    result.text=""+response.body()
                }
                override fun onFailure(call: Call<Boolean>, t: Throwable) { }
            })
        })

    }
}