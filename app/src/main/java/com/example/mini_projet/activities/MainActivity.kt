package com.example.mini_projet.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.adapters.QuizzAdapter
import com.example.mini_projet.models.Quizz
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle:ActionBarDrawerToggle
    lateinit var adapter: QuizzAdapter
    private var quizzList= mutableListOf<Quizz>()
    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //populateDummyData()
        setUpViews()
        firebaseAuth=FirebaseAuth.getInstance()
    }
    private fun populateDummyData() {
        quizzList.add(Quizz("31-12-2022","31-12-2022"))

    }
     fun setUpViews() {
         setUpFireStore()
         setUpDrawerLayout()
         setUpRecyclerView()
    }

    private fun setUpFireStore() {
        firestore=FirebaseFirestore.getInstance()
        val collectionReference=firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if(value==null||error!=null){
                Toast.makeText(this,"Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quizz::class.java).toString())
            quizzList.clear()
            quizzList.addAll(value.toObjects(Quizz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        val quizzRecyclerView = findViewById<RecyclerView>(R.id.quizzRecyclerView)
        adapter= QuizzAdapter(this,quizzList)
        quizzRecyclerView.layoutManager=GridLayoutManager(this,2)
        quizzRecyclerView.adapter=adapter
    }
    fun setUpDrawerLayout() {
         val appBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
         val mainDrawer = findViewById<DrawerLayout>(R.id.mainDrawer)
         setSupportActionBar(appBar)
         actionBarDrawerToggle=ActionBarDrawerToggle(this,mainDrawer,
             R.string.app_name,
             R.string.app_name
         )
         actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            mainDrawer.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}