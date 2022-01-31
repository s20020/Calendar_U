package jp.ac.it_college.std.s20020.calendar_u

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val dataSet: List<Map<String, String>>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    init {
        setHasStableIds(true)
    }



    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(android.R.id.text1)
        val data: TextView = view.findViewById(android.R.id.text2)
    }

    //一つ一つのセルをつくる
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_expandable_list_item_2, parent, false)
        return ViewHolder(textView)
    }

    //指定された位置にデータを表示するため呼び出される。
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {

        holder.title.text = dataSet[position]["title"]
        holder.data.text = dataSet[position]["date"]



        holder.itemView.setOnClickListener {
            println(position)

            
        }
    }

    override fun getItemCount() : Int = dataSet.size

    fun itemClick(position: Int) {

    }


}