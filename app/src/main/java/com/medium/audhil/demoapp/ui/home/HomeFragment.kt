package com.medium.audhil.demoapp.ui.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.medium.audhil.demoapp.BR
import com.medium.audhil.demoapp.R
import com.medium.audhil.demoapp.data.model.api.generic.NetworkError
import com.medium.audhil.demoapp.data.model.db.MoviesEntity
import com.medium.audhil.demoapp.databinding.HomeFragBinding
import com.medium.audhil.demoapp.ui.base.BaseLifeCycleFragment
import com.medium.audhil.demoapp.ui.other.pagination.EndlessRecyclerViewScrollListener
import com.medium.audhil.demoapp.ui.scrollingscreen.ScrollingActivity
import com.medium.audhil.demoapp.util.ConstantsUtil
import com.medium.audhil.demoapp.util.showToast
import com.medium.audhil.demoapp.util.showVLog
import kotlinx.android.synthetic.main.home_frag.*
import android.view.Menu
import android.view.MenuInflater
import android.widget.AbsListView
import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.*
import android.view.MenuItem

/*
 * Created by mohammed-2284 on 11/04/18.
 */

class HomeFragment : BaseLifeCycleFragment<HomeFragBinding, HomeViewModel>() {

    override fun internetAvailable() {
        getString(R.string.internet_connected).showToast()
        refreshListener.onRefresh()
    }

    override fun internetUnAvailable() =
            getString(R.string.internet_not_connected).showToast()

    override fun initAPIErrorLiveData() =
            viewModel.errorLiveData.observe(this, Observer {
                when (it) {
                    NetworkError.DISCONNECTED -> {
                        "HomeFragment DISCONNECTED".showToast()
                    }
                    NetworkError.BAD_URL -> {
                        "HomeFragment BAD_URL".showToast()
                    }
                    NetworkError.UNKNOWN -> {
                        "HomeFragment UNKNOWN".showToast()
                    }
                    NetworkError.SOCKET_TIMEOUT -> {
                        "HomeFragment SOCKET_TIMEOUT".showToast()
                    }
                }
            })

    override val viewModelClass: Class<HomeViewModel> = HomeViewModel::class.java

    override fun getBindingVariable(): Int = BR.view_model

    override fun getLayoutId(): Int = R.layout.home_frag

    private var searchView: SearchView? = null

    //  new instance
    companion object {
        fun newInstance(toolBarTitle: String): HomeFragment =
                HomeFragment().apply {
                    val bundle = Bundle()
                    bundle.putString(ConstantsUtil.TOOLBAR_TITLE, toolBarTitle)
                    arguments = bundle
                }
    }

    private var isToolbarSearchViewOpen = false
    private var moviesListBackup: List<MoviesEntity>? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter(arguments?.getString(ConstantsUtil.TOOLBAR_TITLE, ConstantsUtil.EMPTY)!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayoutManager()
        initFab()
        initNoMoreDataListener()
        initAdapter()
        initSwipeToRefresh()
        initPagination()
        initMovies()
        refreshListener.onRefresh()
    }

    private fun initFab() {
        fab.tag = ConstantsUtil.NOT_CLICKED

        //  just animating
        fab.setOnClickListener { view ->
            (fab.tag as? String)?.let {
                if (it.equals(ConstantsUtil.NOT_CLICKED, true)) {
                    fab.animate().setDuration(50).rotationBy(45f)
                    fab.tag = ConstantsUtil.CLICKED
                } else {
                    fab.animate().setDuration(50).rotationBy(-45f)
                    fab.tag = ConstantsUtil.NOT_CLICKED
                }
            }
            "Implement anything you want!".showToast()
        }
    }

    //  init layout manager
    private fun initLayoutManager() =
            arguments?.getString(ConstantsUtil.TOOLBAR_TITLE)?.let {
                when (it) {
                    getString(R.string.nav_home) -> recycler_view.layoutManager = LinearLayoutManager(context)

                    getString(R.string.nav_linear) -> recycler_view.layoutManager = LinearLayoutManager(context)

                    getString(R.string.nav_grid) -> recycler_view.layoutManager = GridLayoutManager(context, 2)

                    getString(R.string.nav_staggered_grid) -> recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                }
            }

    //  init swipe to refresh
    private fun initSwipeToRefresh() = swipe_refresh_layout.setOnRefreshListener(refreshListener)

    private val refreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        searchView?.let {
            if (it.isShown) {
                it.onActionViewCollapsed()
                isToolbarSearchViewOpen = false
            }
        }
        viewModel.refresh()
        viewModel.fetchMovies()
    }

    //  no more data listener
    private fun initNoMoreDataListener() =
            viewModel.noMoreDataLiveDataBool.observe(this, Observer {
                if (it!!)
                    adapter.removeLoadingView()
            })

    //  init adapter
    private fun initAdapter() {
        recycler_view.adapter = adapter
        adapter.clickListener = {
            movieItemClick(it, adapter.getItemAtPosition(it))
        }
    }

    //  item click
    private fun movieItemClick(pos: Int, movieEntity: MoviesEntity?) {
        //  just showing a toast
        movieEntity?.title?.showToast()

        //  launching second activity
        startActivity(ScrollingActivity.newIntent(movieEntity?.title, movieEntity?.backdropPath, movieEntity))
    }

    //  init pagination
    private fun initPagination() {
        scrollListener = object : EndlessRecyclerViewScrollListener(recycler_view.layoutManager) {

            override fun onScrollStateChanged(state: Int) =
                    when (state) {
                        AbsListView.OnScrollListener.SCROLL_STATE_FLING ->
                            showVLog("SCROLL_STATE_FLING")

                        AbsListView.OnScrollListener.SCROLL_STATE_IDLE ->
                            fab.animate().scaleX(1f).scaleY(1f).start()

                        AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL ->
                            fab.animate().scaleX(0f).scaleY(0f).start()

                        else ->
                            showVLog("NO_SCROLL_AT_ALL")
                    }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (isToolbarSearchViewOpen)   //  to avoid unnecessary api calls - while toolbar searching
                    return

                viewModel.fetchMovies()
            }
        }
        recycler_view.addOnScrollListener(scrollListener)
    }

    //  init movies
    private fun initMovies() =
            viewModel.moviesListLiveData.observe(this, Observer {
                moviesListBackup = it
                adapter.addMovies(it)
                viewModel.fullPageVisibility.set(false)
                swipe_refresh_layout.isRefreshing = false
            })

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId?.let {
            when (it) {
                R.id.action_settings -> "Settings".showToast()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_home_frag, menu)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView?.queryHint = getString(R.string.search_by_name_or_year)
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView?.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchRecyclerView(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                searchRecyclerView(query)
                return false
            }
        })

        searchView?.setOnCloseListener {
            isToolbarSearchViewOpen = false
            false
        }
    }

    //  searching - recycler view
    private fun searchRecyclerView(query: String) {
        isToolbarSearchViewOpen = true
        if (query.isEmpty())
            adapter.addMovies(
                    moviesListBackup,
                    isToolbarSearchViewOpen
            )
        else
            adapter.addMovies(
                    moviesListBackup
                            ?.asSequence()
                            ?.filter {
                                //  filtering with movie title or with release year (1995-10-20)
                                it.title?.contains(query, true)!! || it.releaseDate?.substring(0, 4)?.contains(query, true)!!
                            }
                            ?.toMutableList(),
                    isToolbarSearchViewOpen
            )
    }
}