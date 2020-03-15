package com.example.simplifiedinstagramstorymodule.ui.story

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.simplifiedinstagramstorymodule.R
import com.example.simplifiedinstagramstorymodule.core.exception.Failure
import com.example.simplifiedinstagramstorymodule.core.extension.observe
import com.example.simplifiedinstagramstorymodule.core.extension.viewModel
import com.example.simplifiedinstagramstorymodule.core.platform.BaseFragment
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.response.StoryResponse
import com.example.simplifiedinstagramstorymodule.core.utilities.CubeTransformer
import kotlinx.android.synthetic.main.story_fragment.*


class StoryFragment : BaseFragment() {

    private lateinit var storyViewModel: StoryViewModel

    private val firstPosition = 0

    override fun layoutId() = R.layout.story_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyViewModel = viewModel(viewModelFactory) {
            observe(storyResponseLiveData, ::onStoryResponseFetched)
            observe(failure, ::handleFailure)
        }
    }

    private fun onStoryResponseFetched(storyResponse: StoryResponse?) {
        storyResponse?.let {
            val list = (storyResponse.message?.profile?.filter { it.stories.isNotEmpty() } ?: emptyList()) as ArrayList
            val adapter = StoryViewPagerAdapter(context!!, list)
            adapter.onProfileStoryFinished = { profilePosition ->
                if ((profilePosition + 1) < list.size) {
                    adapter.profileStoryPosition = firstPosition
                    storyViewPager.currentItem = profilePosition + 1
                } else {
                    Toast.makeText(context!!, R.string.str_stories_finished, Toast.LENGTH_LONG).show()
                    activity!!.finish()
                }
            }
            adapter.onProfileStoryBack = { profilePosition ->
                if ((profilePosition - 1) >= 0) {
                    adapter.profileStoryPosition = firstPosition
                    storyViewPager.currentItem = profilePosition - 1
                }
            }
            storyViewPager.adapter = adapter
            storyViewPager.setPageTransformer(true, CubeTransformer())
            storyViewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrollStateChanged(page: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    arg1: Float,
                    arg2: Int
                ) {}

                override fun onPageSelected(position: Int) {
                    val imageView = storyViewPager.findViewWithTag<ImageView>("ImageView:$position")
                    val progressBarContainer = storyViewPager.findViewWithTag<LinearLayout>("LinearLayout:$position")
                    adapter.clearAllProgressBars(position, progressBarContainer)
                    adapter.profileStoryPosition = firstPosition
                    adapter.startTimer(position, imageView, progressBarContainer, firstPosition)

                }
            })
            startFirstTimer(storyViewPager)

        }
    }

    private fun startFirstTimer(storyViewPager: ViewPager) {
        val imageView = storyViewPager.findViewWithTag<ImageView>("ImageView:$firstPosition")
        val progressBarContainer = storyViewPager.findViewWithTag<LinearLayout>("LinearLayout:$firstPosition")

        val adapter = storyViewPager.adapter
        adapter?.let {
            (it as StoryViewPagerAdapter).startTimer(firstPosition, imageView, progressBarContainer, firstPosition)
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                Toast.makeText(context!!, R.string.failure_network_connection, Toast.LENGTH_LONG).show()
            }
            is Failure.ServerError -> {
                Toast.makeText(context!!, R.string.failure_server_error, Toast.LENGTH_LONG).show()
            }
        }
    }

}
