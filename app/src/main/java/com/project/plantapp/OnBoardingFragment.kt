package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.project.plantapp.introduction.FirstScreen
import com.project.plantapp.introduction.SecondScreen
import com.project.plantapp.introduction.ThirdScreen
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class OnBoardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        val fragmentList = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPaperAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPaper = view.findViewById<ViewPager2>(R.id.view_paper)

        viewPaper.adapter = adapter
        val indicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)
        indicator.attachTo(viewPaper)

        return view
    }

}