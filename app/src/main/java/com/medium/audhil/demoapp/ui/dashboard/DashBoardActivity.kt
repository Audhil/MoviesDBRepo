package com.medium.audhil.demoapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.medium.audhil.demoapp.BR
import com.medium.audhil.demoapp.MovieDBAppDelegate
import com.medium.audhil.demoapp.R
import com.medium.audhil.demoapp.databinding.ActivityDashBoardBinding
import com.medium.audhil.demoapp.ui.base.BaseLifeCycleActivity
import com.medium.audhil.demoapp.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.app_bar_dash_board.*
import kotlinx.android.synthetic.main.nav_header_dash_board.view.*

/*
 * Created by mohammed-2284 on 12/04/18.
 */

class DashBoardActivity :
        BaseLifeCycleActivity<ActivityDashBoardBinding, DashBoardViewModel>(),
        NavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun newIntent(): Intent = Intent(MovieDBAppDelegate.INSTANCE, DashBoardActivity::class.java)
    }

    override val viewModelClass: Class<DashBoardViewModel> = DashBoardViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.one?.setVariable(BR.a_view_model, viewModel)
        viewDataBinding.one?.two?.setVariable(BR.b_view_model, viewModel)
        viewDataBinding.executePendingBindings()
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.getHeaderView(0).nav_base_l_layout.setBackgroundColor(ContextCompat.getColor(applicationContext, android.R.color.black))
        nav_view.getHeaderView(0).user_name_txt_view.text = getString(R.string.s_mohammed_audhil)
        nav_view.getHeaderView(0).user_mail_id.text = getString(R.string.audhil_mail_id)

        //  selecting first nav item
        selectNavHomeItem()
    }

    //  selectNavHomeItem
    private fun selectNavHomeItem() {
        nav_view.setCheckedItem(R.id.nav_home)
        onNavigationItemSelected(nav_view.menu.findItem(R.id.nav_home))
    }

    override fun onBackPressed() = if (drawer_layout.isDrawerOpen(GravityCompat.START))
        drawer_layout.closeDrawer(GravityCompat.START)
    else
        super.onBackPressed()

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                viewModel.toolBarTitle = getString(R.string.nav_home)
                supportFragmentManager
                        .beginTransaction()
                        .disallowAddToBackStack()
                        .replace(R.id.container, HomeFragment.newInstance(viewModel.toolBarTitle!!), HomeFragment::javaClass.name)
                        .commit()
            }

            R.id.nav_linear -> {
                viewModel.toolBarTitle = getString(R.string.nav_linear)
                supportFragmentManager
                        .beginTransaction()
                        .disallowAddToBackStack()
                        .replace(R.id.container, HomeFragment.newInstance(viewModel.toolBarTitle!!), HomeFragment::javaClass.name)
                        .commit()
            }

            R.id.nav_staggered_grid -> {
                viewModel.toolBarTitle = getString(R.string.nav_staggered_grid)
                supportFragmentManager
                        .beginTransaction()
                        .disallowAddToBackStack()
                        .replace(R.id.container, HomeFragment.newInstance(viewModel.toolBarTitle!!), HomeFragment::javaClass.name)
                        .commit()
            }


            R.id.nav_grid -> {
                viewModel.toolBarTitle = getString(R.string.nav_grid)
                supportFragmentManager
                        .beginTransaction()
                        .disallowAddToBackStack()
                        .replace(R.id.container, HomeFragment.newInstance(viewModel.toolBarTitle!!), HomeFragment::javaClass.name)
                        .commit()
            }
        }
        supportActionBar?.title = viewModel.toolBarTitle
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun getBindingVariable(): Int = BR.dash_board_view_model

    override fun getLayoutId(): Int = R.layout.activity_dash_board
}