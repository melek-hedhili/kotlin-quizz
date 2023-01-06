package com.example.mini_projet.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.models.Question

class OptionAdapter (val context: Context, val questions:Question):
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>(){
    private var options:List<String> =listOf(questions.option1,questions.option2,questions.option3,questions.option4)
    inner class OptionViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var oprionView=itemView.findViewById<TextView>(R.id.quizz_option)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_item,parent,false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.oprionView.text=options[position]
        holder.itemView.setOnClickListener {
            questions.userAnswer=options[position]
            notifyDataSetChanged()
        }
        if(questions.userAnswer==options[position]){
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg)
        }else{
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }

}