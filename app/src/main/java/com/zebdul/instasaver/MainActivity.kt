package com.zebdul.instasaver

import android.Manifest

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import papayacoders.instastory.Stories
import papayacoders.instastory.models.TrayModel


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() ,UserInterface {

private val permission=arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val recyclerView = findViewById<RecyclerView>(R.id.userProfileRecyclerView)

        val storyRecyclerView = findViewById<RecyclerView>(R.id.storyRecyclerView)

        val profileAdapter = ProfileAdapter(this)


        recyclerView.adapter = profileAdapter

        val storyAdapter = StoryAdapter()
        storyRecyclerView.adapter = storyAdapter

        checkPermission()

        val button = findViewById<Button>(R.id.login)

        button.setOnClickListener {
            Stories.users(this)
        }

        Stories.users(this)

//        Stories.getStories(this, "26501595281")

        Stories.storyList.observe(this) {
            storyAdapter.submitList(it)

            for (item in it) {
                Log.d("ZebdulStory", "name: ${item.imageversions2.candidates}")
            }
        }


        Stories.list.observe(this) {
            profileAdapter.submitList(it)

            for (item in it) {
                Log.d("Zebdul", "name: ${item.user.fullname}")
            }
        }


    }

    override fun userInterfaceClick(i: Int, traymodel: TrayModel) {
        Stories.getStories(this, traymodel.user.pk.toString())
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Stories.isLogin(this)){
            Stories.users(this)
            }
    }

    private fun checkPermission():Boolean{
        var result :Int
        val listPermissionNeeded : MutableList<String> = ArrayList()

        for (p in permission){
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(p)
            }
        }
        if(listPermissionNeeded.isNotEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toTypedArray(),0)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode ==0 ){
            if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Please accept all permission", Toast.LENGTH_SHORT).show()
            }
            checkPermission()
        }
    }
}