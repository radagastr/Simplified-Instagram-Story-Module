package com.example.simplifiedinstagramstorymodule.ui.story


import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.simplifiedinstagramstorymodule.R
import com.example.simplifiedinstagramstorymodule.core.extension.loadFromUrl
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.response.Profile


class StoryViewPagerAdapter(private val context: Context, val profileList: List<Profile>) :
    PagerAdapter() {

    internal var onProfileStoryFinished: (profilePosition: Int) -> Unit =
        { _ -> }
    internal var onProfileStoryBack: (profilePosition: Int) -> Unit =
        { _ -> }

    private var layoutInflater: LayoutInflater? = null

    var profileStoryPosition = 0
    //private var profilePosition = 0

    private var pressTime = 0L
    private var limit = 500L

    private var mCountDownTimer: CountDownTimer? = null

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun getCount(): Int {
        return profileList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.image_slider_item, container, false)

        val imageView = v.findViewById<ImageView>(R.id.image_view)
        val progressBarContainer = v.findViewById<LinearLayout>(R.id.linearLayoutStatusContainer)

        profileStoryPosition = 0

        setPreviousButtonListener(v, position, imageView, progressBarContainer)
        setForwardButtonListener(v, position, imageView, progressBarContainer)
        setProgressBarView(progressBarContainer, position)
        imageView.tag = "ImageView:$position"
        progressBarContainer.tag = "LinearLayout:$position"

        setCurrentProgressBarToMin(progressBarContainer)
        loadImage(position, imageView)
        val vp = container as ViewPager
        vp.addView(v, 0)
        return v
    }

    private fun setProgressBarView(progressBarContainer: LinearLayout, position: Int) {
        profileList[position].stories.forEachIndexed { index, _ ->
            val pb = ProgressBar(
                context,
                null,
                android.R.attr.progressBarStyleHorizontal
            )
            val param = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1.0f
            )
            param.setMargins(8, 0, 8, 0)
            pb.layoutParams = param
            pb.max = 100
            pb.tag = "ProgressBar:$position-$index"
            progressBarContainer.addView(pb)
        }
    }

    private fun setCurrentProgressBarToMax(progressBarContainer: LinearLayout) {
        progressBarContainer.getChildAt(profileStoryPosition)?.let {
            val pb = it as ProgressBar
            pb.progress = 100
        }
        pauseTimer()
    }

    private fun setCurrentProgressBarToMin(progressBarContainer: LinearLayout) {
        progressBarContainer.getChildAt(profileStoryPosition)?.let {
            val pb = it as ProgressBar
            pb.progress = 0
        }
        pauseTimer()
    }

    private fun setForwardButtonListener(
        v: View,
        profilePosition: Int,
        imageView: ImageView,
        progressBarContainer: LinearLayout
    ) {
        val forward = v.findViewById<View>(R.id.forward)
        forward.setOnClickListener {
            setForwardImage(profilePosition, imageView, progressBarContainer)
        }

        forward.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    pressTime = System.currentTimeMillis()
                    Log.e("EM", "pause timer")
                    pauseTimer()
                    return@OnTouchListener false
                }
                MotionEvent.ACTION_UP -> {
                    val now = System.currentTimeMillis()
                    continueTimer()
                    return@OnTouchListener limit < now - pressTime
                }
            }
            false
        })


    }

    private fun setForwardImage(
        profilePosition: Int,
        imageView: ImageView,
        progressBarContainer: LinearLayout
    ) {
        Log.e(
            "EM",
            "setForwardImage: profilePosition: $profilePosition - profileStoryPosition: $profileStoryPosition"
        )
        val hasProfileNextStory =
            profileStoryPosition + 1 < profileList[profilePosition].stories.size
        if (hasProfileNextStory) {
            setCurrentProgressBarToMax(progressBarContainer)
            profileStoryPosition++
            loadImage(profilePosition, imageView)
            startTimer(profilePosition, imageView, progressBarContainer, profileStoryPosition)
        } else {
            setCurrentProgressBarToMax(progressBarContainer)
            onProfileStoryFinished(profilePosition)
        }
    }

    private fun setPreviousButtonListener(
        v: View,
        profilePosition: Int,
        imageView: ImageView,
        progressBarContainer: LinearLayout
    ) {
        val previous = v.findViewById<View>(R.id.previous)
        previous.setOnClickListener {
            setBackImage(profilePosition, imageView, progressBarContainer)
        }

        previous.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    pressTime = System.currentTimeMillis()
                    Log.e("EM", "pause timer")
                    pauseTimer()
                    return@OnTouchListener false
                }
                MotionEvent.ACTION_UP -> {
                    val now = System.currentTimeMillis()
                    continueTimer()
                    return@OnTouchListener limit < now - pressTime
                }
            }
            false
        })

    }

    private fun setBackImage(
        profilePosition: Int,
        imageView: ImageView,
        progressBarContainer: LinearLayout
    ) {
        Log.e(
            "EM",
            "setForwardImage: profilePosition: $profilePosition - profileStoryPosition: $profileStoryPosition"
        )
        val hasProfileBackStory = profileStoryPosition - 1 >= 0
        if (hasProfileBackStory) {
            setCurrentProgressBarToMin(progressBarContainer)
            profileStoryPosition--
            loadImage(profilePosition, imageView)
            startTimer(profilePosition, imageView, progressBarContainer, profileStoryPosition)
        } else {
            setCurrentProgressBarToMin(progressBarContainer)
            onProfileStoryBack(profilePosition)
        }
    }

    private fun loadImage(
        profilePosition: Int,
        imageView: ImageView
    ) {
        Log.e(
            "EM",
            "loadImage: profilePosition: $profilePosition - profileStoryPosition: $profileStoryPosition"
        )
        imageView.loadFromUrl(profileList[profilePosition].stories[profileStoryPosition].src)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }

    fun clearAllProgressBars(profilePosition: Int,
                             progressBarContainer: LinearLayout) {
        for (i in 0 .. profileList[profilePosition].stories.size) {
            progressBarContainer.getChildAt(i)?.let {
                (it as ProgressBar).progress = 0
            }
        }
    }

    fun startTimer(
        profilePosition: Int,
        imageView: ImageView,
        progressBarContainer: LinearLayout,
        profileStoryPosition: Int
    ) {
        progressBarContainer.getChildAt(profileStoryPosition)?.let {
            val progressBar = it as ProgressBar
            var i = 0
            progressBar.max = 100
            progressBar.progress = i
            mCountDownTimer = object : CountDownTimer(5000, 10) {

                override fun onTick(millisUntilFinished: Long) {
                    i++
                    progressBar.progress = i * 100 / (5000 / 10)
                }

                override fun onFinish() {
                    setForwardImage(profilePosition, imageView, progressBarContainer)
                    i++
                    progressBar.progress = 100
                }
            }.start()
        }
    }

    private fun pauseTimer() {
        mCountDownTimer?.cancel()
    }

    private fun continueTimer() {
        mCountDownTimer?.start()

    }


}