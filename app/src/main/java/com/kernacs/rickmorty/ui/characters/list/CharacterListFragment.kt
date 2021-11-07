package com.kernacs.rickmorty.ui.characters.list

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kernacs.rickmorty.R
import com.kernacs.rickmorty.base.BaseFragment
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.databinding.FragmentItemListBinding
import com.kernacs.rickmorty.ui.characters.details.CharacterDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CharacterListFragment : BaseFragment() {

    private val viewModel: CharacterListViewModel by viewModels()
    private lateinit var binding: FragmentItemListBinding
    private val items = ArrayList<CharacterEntity>()
    private val adapter: CharacterListRecyclerViewAdapter
        get() = binding.itemList.adapter as CharacterListRecyclerViewAdapter
    override val trackedScreenName: String
        get() = "CharacterList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_item_list,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         * TODO https://proandroiddev.com/master-detail-views-with-navigation-components-a20405f31974
         */
        val onClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag as CharacterEntity
            val bundle = Bundle()
            bundle.putInt(
                CharacterDetailFragment.ARG_ITEM_ID,
                item.id
            )
            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_item_detail, bundle)
            } else {
                itemView.findNavController().navigate(R.id.show_item_detail, bundle)
            }
        }

        setupRecyclerView(binding.itemList, onClickListener)

        viewModel.items.observe(viewLifecycleOwner, {
            adapter.addItems(it)
            checkEmptyView()
        })
        viewModel.newPage.observe(viewLifecycleOwner, {
            // add lazy loaded items
            adapter.addItems(it)
            checkEmptyView()
        })
        viewModel.error.observe(viewLifecycleOwner, { error ->
            error?.let {
                Snackbar.make(view, error, Snackbar.LENGTH_LONG).show()
            }
            checkEmptyView()
        })
    }

    private fun checkEmptyView() {
        binding.apply {
            if (adapter.itemCount == 0) {
                empty?.emptyContainer?.isVisible = true
                empty?.retryButton?.setOnClickListener { viewModel?.loadNewPage() }
                binding.itemList.isVisible = false
            } else {
                binding.itemList.isVisible = true
                binding.empty?.emptyContainer?.isVisible = false
            }
        }
    }


    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onClickListener: View.OnClickListener,
    ) {

        recyclerView.adapter = CharacterListRecyclerViewAdapter(
            items, onClickListener
        )

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.swipeRefresh.isRefreshing) {
                    if ((recyclerView.layoutManager as LinearLayoutManager)
                            .findLastCompletelyVisibleItemPosition() == recyclerView.adapter?.itemCount ?: 0 - 1) {
                        //bottom of list!
                        viewModel.loadNewPage()
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshData()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_characters, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favourites -> {
                findNavController().navigate(R.id.favourite_characters_fragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}