package com.example.mini_projet.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.activities.QuestionActivity
import com.example.mini_projet.models.Quizz
import com.example.mini_projet.utils.ColorPicker
import com.example.mini_projet.utils.IconPicker

class QuizzAdapter (val context: Context, val quizzes:List<Quizz>):
    RecyclerView.Adapter<QuizzAdapter.QuizzViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizzViewHolder {

        val view=LayoutInflater.from(context).inflate(R.layout.quizz_item,parent,false)
        return QuizzViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizzViewHolder, position: Int) {

        holder.textViewTitle.text=quizzes[position].title
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.iconView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener{
            Toast.makeText(context,quizzes[position].title, Toast.LENGTH_LONG).show()
            val intent = Intent(context,QuestionActivity::class.java)
            intent.putExtra("DATE",quizzes[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return quizzes.size
    }
    inner class QuizzViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textViewTitle:TextView=itemView.findViewById(R.id.quizzTitle)
        var iconView:ImageView=itemView.findViewById(R.id.quizzIcon)
        var cardContainer:CardView=itemView.findViewById(R.id.cardContainer)
    }
}