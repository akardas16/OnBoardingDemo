package com.akardas.onboardingdemo

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Interpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieDrawable
import com.akardas.onboardingdemo.databinding.OnboardingActivityBinding
import com.akardas.onboardingdemo.onboardingFragments.OnBoardingFragment
import com.github.hariprasanths.bounceview.BounceView
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlin.math.pow


class OnBoardingActivity : AppCompatActivity() {

    lateinit var binding:OnboardingActivityBinding
    private lateinit var adapter: OnBoardingAdapter
     var animation:ObjectAnimator? = null

    private val onboardList = arrayListOf(
        OnBoardingModel("boarding1.json","Track Your Health","Welcome to health tracker app! Get to a healthier and more active life! It's hard to know how much or what kind of activity you need to stay healthy."),
        OnBoardingModel("boarding2.json","Explore Earth","The Earth, like other planets, is explored by spacecraft. Earth orbiting spacecraft observe phenomena that are large and global in scale."),
        OnBoardingModel("boarding3.json","Fast Optimization","Gradient Descent is the most basic but most used optimization algorithm. It's used heavily in linear regression and classification algorithms."))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hideStatusBar(window, this)
        binding = OnboardingActivityBinding.inflate(layoutInflater)


        adapter = OnBoardingAdapter(supportFragmentManager,lifecycle)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.currentItem = 0
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)
        onboardList.forEach {
            adapter.addFragment(OnBoardingFragment.newInstance(it))
        }


        binding.progressView1.max = 600
        binding.progressView2.max = 600
        binding.progressView3.max = 600

        binding.btnContinue.alpha = 0f
        binding.backBtn.alpha = 0f
        BounceView.addAnimTo(binding.btnContinue)
        //doBounceAnimation(binding.btnContinue,"translationY")
        doBounceAnimation(binding.backBtn, name = "translationX",4f)
        doBounceAnimation(binding.btnForward, name = "translationX",-4f)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.i("wefwefwf", "onPageScrolled: ${position}")
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        binding.btnContinue.alpha = 0f
                        hide(binding.backBtn)
                        show(binding.btnForward)
                        setProgress(0,0,0)
                        progressAnimate(binding.progressView1)
                    }
                    1 -> {
                        binding.btnContinue.alpha = 0f
                        show(binding.backBtn)
                        show(binding.btnForward)
                        setProgress(1000,0,0)
                        progressAnimate(binding.progressView2)
                    }
                    2 -> {
                        show(binding.btnContinue)
                        show(binding.backBtn)
                        hide(binding.btnForward)
                        setProgress(1000,1000,0)
                        progressAnimate(binding.progressView3)
                    }
                }
                Log.i("wefwefw44f", "onPageSelected: ${position}")

            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.i("wefwefwf", "onPageScrollStateChanged: ${state}")
            }
        })





        setContentView(binding.root)

    }
    private fun doBounceAnimation(targetView: View,name:String = "translationX",value:Float = 4f) {
        val interpolator =
            Interpolator { v ->
                getPowOut(v, 2.0) //Add getPowOut(v,3); for more up animation
            }
        val animator = ObjectAnimator.ofFloat(targetView, name, 0f, value, 0f)
        animator.interpolator = interpolator
        animator.startDelay = 200
        animator.duration = 800
        animator.repeatCount = LottieDrawable.INFINITE
        animator.start()
    }

    private fun getPowOut(elapsedTimeRate: Float, pow: Double): Float {
        return (1.toFloat() - (1 - elapsedTimeRate).toDouble().pow(pow)).toFloat()
    }
    private fun show(v:View){
        v.animate().alpha(1f).setDuration(600).start()
    }

    private fun hide(v:View){
        v.animate().alpha(0f).setDuration(600).start()
    }

    private fun setProgress(p1:Int,p2:Int,p3:Int){
        binding.progressView1.progress = p1
        binding.progressView2.progress = p2
        binding.progressView3.progress = p3
    }
    private fun progressAnimate(progressBar: LinearProgressIndicator) {
        if (animation != null){
            animation?.cancel()
            animation = null
        }
        animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, 600)
        animation?.duration = 7000
        //animation.interpolator = DecelerateInterpolator()
        animation?.start()
        animation?.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                if (binding.viewPager.currentItem < 2 && animation?.animatedFraction!! > 0.96){
                    binding.viewPager.currentItem += 1
                }
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}
        })

        binding.backBtn.setOnClickListener {
            if (binding.viewPager.currentItem > 0){
                binding.viewPager.currentItem -= 1
            }
        }

        binding.btnForward.setOnClickListener {
            if (binding.viewPager.currentItem < 2){
                binding.viewPager.currentItem += 1
            }
        }
    }
    @Suppress("DEPRECATION")
    private fun hideStatusBar(window: Window, context: Context){
        window
            .decorView
            .systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = ContextCompat.getColor(context, R.color.transparent_bg_color)
    }
}