package com.project.plantapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.plantapp.adapter.OnSpecieSIndexItemListener
import com.project.plantapp.adapter.SpecieIndexAdapter
import com.project.plantapp.data.DataApp
import com.project.plantapp.databinding.FragmentSpeciesIndexBinding
import com.project.plantapp.viewmodel.SpeciesIndexVM
import java.util.Objects

class SpeciesIndexFragment : Fragment() {
    private lateinit var _binding:FragmentSpeciesIndexBinding
    private lateinit var viewModel: SpeciesIndexVM
    private lateinit var _adapter: SpecieIndexAdapter

    private var _data = DataApp.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpeciesIndexBinding.inflate(inflater, container, false)
        _adapter = SpecieIndexAdapter(ArrayList(),onSpecieItemListener)
        viewModel = ViewModelProvider(this)[SpeciesIndexVM::class.java]
        _binding.speciesBackBnt.setOnClickListener{
            findNavController().popBackStack()
        }
        _binding.rvSpecies.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(context)
            setIndexBarTransparentValue(0.0.toFloat())
            setIndexBarTextColor("#A1A8B9")
            setIndexbarHorizontalMargin(20.toFloat())
            setIndexBarStrokeVisibility(false)

        }
        viewModel.loadData()
        Objects.requireNonNull<RecyclerView.LayoutManager>(_binding.rvSpecies.layoutManager)
            .scrollToPosition(0)

        viewModel.listOfSpeciesIndex.observe(viewLifecycleOwner) {
            Log.v("hmco: ", "reload")
            _binding.rvSpecies.adapter = SpecieIndexAdapter(it as ArrayList<String>?,onSpecieItemListener)
        }

        return _binding.root
    }


    private val onSpecieItemListener  = object : OnSpecieSIndexItemListener {
        override fun onClickItem(item: String) {
            val direction  = SpeciesIndexFragmentDirections.actionSpeciesFragmentToSpeciesCategoryFragment(item)
            findNavController().navigate(direction)
        }
    }


}