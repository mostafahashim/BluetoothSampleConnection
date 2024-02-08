package bluetooth.sample.connection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import bluetooth.sample.connection.R
import bluetooth.sample.connection.data.model.UserModel
import bluetooth.sample.connection.databinding.RecyclerItemUserBinding
import bluetooth.sample.connection.observer.OnItemClickObserver

class RecyclerUsersAdapter(
    var models: ArrayList<UserModel>,
    var onItemClickObserver: OnItemClickObserver
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = DataBindingUtil.inflate(
            inflater,
            R.layout.recycler_item_user,
            parent,
            false
        ) as RecyclerItemUserBinding
        return ViewHolder(binding)
    }


    override fun getItemCount() = models.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder = holder as ViewHolder

        holder.binding.model = models[holder.layoutPosition]
        holder.itemView.setOnClickListener {
            onItemClickObserver.onItemClick(holder.layoutPosition)
        }

    }

    fun setList(list: ArrayList<UserModel>) {
        this.models = list
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: RecyclerItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)


}