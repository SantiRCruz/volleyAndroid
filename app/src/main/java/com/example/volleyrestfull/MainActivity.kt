package com.example.volleyrestfull

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.volleyrestfull.typiCodeModel
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private val url = "https://jsonplaceholder.typicode.com/posts"
    private var typiCodeModel: MutableList<typiCodeModel> = mutableListOf()
    private var dataModel: MutableList<Data> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadTaskArray()
        getObject()
        post()
        update()
    }


    private fun update() {
        val jsonPost = NewPost("holaa", "holaaa", 2)

        val queue = Volley.newRequestQueue(applicationContext)
        val request =
            JsonObjectRequest(Request.Method.PUT, "https://jsonplaceholder.typicode.com/posts/1",
                JSONObject().apply {
                    put("id",1)
                    put("title",jsonPost.title)
                    put("body",jsonPost.body)
                    put("userId",jsonPost.userId)
                },
                {
                    Log.e("update: ",it.toString() )
                },
                {
                    Log.e("update: ",it.message.toString() )
                }
            )
        queue.add(request)
    }

    private fun post() {
        val jsonPost = NewPost("holaa", "holaaa", 2)

        val queue = Volley.newRequestQueue(applicationContext)
        val request =
            JsonObjectRequest(Request.Method.POST, "https://jsonplaceholder.typicode.com/posts",
                JSONObject().apply {
                    put("title",jsonPost.title)
                    put("body",jsonPost.body)
                    put("userId",jsonPost.userId)
                },
                {
                    Log.e("post: ",it.toString() )
                },
                {
                    Log.e("post: ",it.message.toString() )
                }
            )
        queue.add(request)
    }

    private fun getObject() {
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest("https://reqres.in/api/users?page=2",
            {
                Log.e("getObject: ", it.toString())
                val jsonArray = it.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    val json = jsonArray.getJSONObject(i)
                    dataModel.add(
                        Data(
                            json.getInt("id"),
                            json.getString("email"),
                            json.getString("first_name"),
                            json.getString("last_name"),
                            json.getString("avatar"),
                        )
                    )

                }
                Log.e("getObject2: ", dataModel[1].email)
            },
            {
                Log.e("getObject: ", it.message.toString())
            }
        )
        queue.add(request)
    }

    private fun downloadTaskArray() {
        val queue = Volley.newRequestQueue(applicationContext)
        val request = JsonArrayRequest(url,
            {
                Log.e("downloadTask: ", it.toString())
                for (i in 0 until it.length()) {
                    val pojo = it.getJSONObject(i)
                    typiCodeModel.add(
                        typiCodeModel(
                            pojo.getInt("userId"),
                            pojo.getInt("id"),
                            pojo.getString("title"),
                            pojo.getString("body")
                        ),
                    )
                }
                Log.e("downloadTask2: ", typiCodeModel[1].body)
            },
            {
                Log.e("downloadTask: ", it.printStackTrace().toString())
            })
        queue.add(request)
    }
}