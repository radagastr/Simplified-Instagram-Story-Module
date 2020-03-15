package com.example.simplifiedinstagramstorymodule.ui.story

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.simplifiedinstagramstorymodule.R
import com.example.simplifiedinstagramstorymodule.core.extension.loadFromUrl
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.response.Profile
import jp.shts.android.storiesprogressview.StoriesProgressView


class StoryViewPagerAdapter(private val context: Context, val profileList: List<Profile>): PagerAdapter() , StoriesProgressView.StoriesListener {

    internal var onProfileStoryFinished: (profilePosition: Int) -> Unit =
        { _ -> }

    private var layoutInflater: LayoutInflater? = null
    private lateinit var storiesProgressView: StoriesProgressView
    private lateinit var imageView: ImageView

    private var profileStoryPosition = 0
    private var profilePosition = 0

    private var pressTime = 0L
    private var limit = 500L

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun getCount(): Int {
        return profileList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.image_slider_item, container, false)
        storiesProgressView = v.findViewById(R.id.stories)
        imageView = v.findViewById(R.id.image_view)

        profilePosition = position
        profileStoryPosition = 0


        setPreviousButtonListener(v)
        setForwardButtonListener(v)
        setStoriesProgressView()

        loadImage()
        val vp = container as ViewPager
        if (v.parent != null) {
            (v.parent as ViewGroup).removeView(v)
        }
        vp.addView(v,0)
        return v
    }

    private fun setForwardButtonListener(v: View) {
        val forward = v.findViewById<View>(R.id.forward)
        forward.setOnClickListener {
            storiesProgressView.skip()
        }
        forward.setOnTouchListener(onTouchListener)
    }

    private fun setPreviousButtonListener(v: View) {
        val previous = v.findViewById<View>(R.id.previous)
        previous.setOnClickListener {
            storiesProgressView.reverse()
        }
        previous.setOnTouchListener(onTouchListener)
    }

    private fun loadImage() {
        Log.e("EM", "instantiateItem - profilePosition: $profilePosition")
        Log.e("EM", "instantiateItem - profileStoryPosition: $profileStoryPosition")
        imageView.loadFromUrl(profileList[profilePosition].stories[profileStoryPosition].src)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }

    private fun setStoriesProgressView() {
        storiesProgressView.setStoriesCount(profileList[profilePosition].stories.size)
        storiesProgressView.setStoryDuration(3000L)
        storiesProgressView.setStoriesListener(this)
        storiesProgressView.startStories()
    }

    override fun onComplete() {
        onProfileStoryFinished(profilePosition)
    }

    override fun onPrev() {
        if ((profileStoryPosition - 1) < 0) return
        profileStoryPosition--
        loadImage()
    }

    override fun onNext() {
        profileStoryPosition++
        loadImage()
    }

    private val onTouchListener = OnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                storiesProgressView.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                storiesProgressView.resume()
                return@OnTouchListener limit < now - pressTime
            }
        }
        false
    }
}