package com.medium.audhil.demoapp.ui.scrollingscreen

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.medium.audhil.demoapp.BR
import com.medium.audhil.demoapp.MovieDBAppDelegate
import com.medium.audhil.demoapp.R
import com.medium.audhil.demoapp.data.model.db.MoviesEntity
import com.medium.audhil.demoapp.data.remote.MovieDBAppAPIEndPoints
import com.medium.audhil.demoapp.databinding.ActivityScrollingBinding
import com.medium.audhil.demoapp.ui.base.BaseLifeCycleActivity
import com.medium.audhil.demoapp.util.ConstantsUtil
import com.medium.audhil.demoapp.util.biLets
import com.medium.audhil.demoapp.util.showSnackBar
import kotlinx.android.synthetic.main.activity_scrolling.*

/*
 * Created by mohammed-2284 on 12/04/18.
 */

class ScrollingActivity : BaseLifeCycleActivity<ActivityScrollingBinding, ScrollingScreenViewModel>() {

    override val viewModelClass: Class<ScrollingScreenViewModel>
        get() = ScrollingScreenViewModel::class.java

    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.activity_scrolling

    companion object {
        fun newIntent(movieTitle: String? = null,
                      movieBackDrop: String? = null,
                      parcelable: Parcelable? = null): Intent =
                Intent(MovieDBAppDelegate.INSTANCE, ScrollingActivity::class.java).apply {

                    //  extn function to make life easy
                    Pair(movieTitle, movieBackDrop).biLets { title, backDrop ->
                        putExtra(ConstantsUtil.MOVIE_NAME, title)
                        putExtra(ConstantsUtil.MOVIE_BACK_DROP, backDrop)
                    }

                    //  movie data parcel
                    parcelable?.let {
                        val bundle = Bundle()
                        bundle.putParcelable(ConstantsUtil.PARCELABLE, it)
                        putExtras(bundle)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            //  just showing snackbar
            view.showSnackBar()
        }

        //  data binding in xml
        intent?.extras?.getParcelable<MoviesEntity>(ConstantsUtil.PARCELABLE)?.let {
            viewDataBinding.scrollContentLayout?.setVariable(BR.movie_entity, it)
            viewDataBinding.scrollContentLayout?.executePendingBindings()
        }

        //  actionbar kinda stuff
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = intent?.extras?.getString(ConstantsUtil.MOVIE_NAME)
        }

        //  imageview
        Glide.with(applicationContext)
                .load(MovieDBAppAPIEndPoints.IMAGE_END_POINT_W_500 +
                        intent?.extras?.getString(ConstantsUtil.MOVIE_BACK_DROP))
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round))
                .into(expanded_image)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home ->
                finish()
        }
        return super.onOptionsItemSelected(item)
    }
}