package com.hannesdorfmann.navigation.view.newslist

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.navigation.R
import com.hannesdorfmann.navigation.databinding.FragmentNewslistBinding
import com.hannesdorfmann.navigation.domain.news.News
import com.hannesdorfmann.navigation.utils.getViewModel

class NewsListFragment : Fragment() {

    private lateinit var adapter: NewsListAdapter
    private lateinit var vm: NewsListViewModel
    private lateinit var bindingNewList: FragmentNewslistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNewList = FragmentNewslistBinding.inflate(inflater, container, false)
        return bindingNewList.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        vm = getViewModel()
        with(bindingNewList) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = NewsListAdapter(layoutInflater, vm::itemSelected)
            recyclerView.adapter = adapter

            vm.items.observe(viewLifecycleOwner) {
                adapter.items = it
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                vm.logout()
                true
            }
            R.id.toggleABTestGroup -> {
                vm.toggleABTestGroupAssignment()
                true
            }
            else -> false
        }
    }
}


class NewsListAdapter(val inflater: LayoutInflater, val click: (Int) -> Unit) :
    RecyclerView.Adapter<NewsListViewHolder>() {

    var items = emptyList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder =
        NewsListViewHolder(inflater.inflate(R.layout.item_news, null, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title).text = items[position].title
        holder.itemView.setOnClickListener { click(items[position].id) }
    }
}

class NewsListViewHolder(view: View) : RecyclerView.ViewHolder(view)