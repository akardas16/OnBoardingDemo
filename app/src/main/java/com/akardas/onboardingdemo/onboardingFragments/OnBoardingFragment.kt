package com.akardas.onboardingdemo.onboardingFragments

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akardas.onboardingdemo.OnBoardingModel
import com.akardas.onboardingdemo.databinding.FragmentOnBoardingBinding


class OnBoardingFragment : Fragment() {

    private  var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    lateinit var titleText:String
    lateinit var descriptionText:String
    lateinit var lottieName:String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOnBoardingBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment

        titleText = arguments?.getString("title").toString()
        descriptionText = arguments?.getString("description").toString()
        lottieName = arguments?.getString("lottie").toString()

        binding.title.text = titleText
        binding.description.text = descriptionText

        val typeface = Typeface.createFromAsset(requireContext().assets,"Roboto-Bold.ttf")
        val typeface2 = Typeface.createFromAsset(requireContext().assets,"Roboto-Regular.ttf")

        binding.title.typeface = typeface
        binding.description.typeface = typeface2
        binding.animationView.setAnimation(lottieName)


        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(data:OnBoardingModel) =
            OnBoardingFragment().apply {
                arguments = Bundle().apply {
                    putString("title", data.title)
                    putString("description",data.description)
                    putString("lottie",data.lottie)
                }
            }
    }
}