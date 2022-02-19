package ishant.proximity.proxiityassignment.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<Y:ViewBinding>(val binding: Y) : RecyclerView.ViewHolder(binding.root)
