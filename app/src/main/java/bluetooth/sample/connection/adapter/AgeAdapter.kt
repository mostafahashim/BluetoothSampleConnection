package bluetooth.sample.connection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import bluetooth.sample.connection.R
import bluetooth.sample.connection.data.model.UserModel
import bluetooth.sample.connection.databinding.RecyclerItemUserBinding
import bluetooth.sample.connection.databinding.SpinnerItemAgeBinding
import bluetooth.sample.connection.observer.OnItemClickObserver

class AgeAdapter(
    var models: ArrayList<String>,
    var onItemClickObserver: OnItemClickObserver
) : BaseAdapter() {

    fun setList(list: ArrayList<String>) {
        this.models = list
        notifyDataSetChanged()
    }


    override fun getCount(): Int {
        return models.size
    }

    override fun getItem(position: Int): String {
        return models[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view = convertView
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent!!.context)
            val binding = DataBindingUtil.inflate(
                inflater,
                R.layout.spinner_item_age,
                parent,
                false
            ) as SpinnerItemAgeBinding
            view = binding.root
            viewHolder = ViewHolder(binding)
            view.tag = viewHolder
        } else {
            viewHolder = view!!.tag as ViewHolder
        }
        viewHolder.binding.model = models[position]
        return viewHolder.binding.root
    }


    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view = convertView
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent!!.context)
            val binding = DataBindingUtil.inflate(
                inflater,
                R.layout.spinner_item_age,
                parent,
                false
            ) as SpinnerItemAgeBinding
            view = binding.root
            viewHolder = ViewHolder(binding)
            view.tag = viewHolder
        } else {
            viewHolder = view!!.tag as ViewHolder
        }

        viewHolder.binding.model = models[position]
        viewHolder.binding.layoutContainer.setOnClickListener {
            onItemClickObserver.onItemClick(position)
        }
        return viewHolder.binding.root
    }

    class ViewHolder(var binding: SpinnerItemAgeBinding) {}
}