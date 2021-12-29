package com.toren.foodbookapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.toren.foodbookapp.adapter.MaterialItemAdapter
import com.toren.foodbookapp.databinding.FoodFragmentBinding
import com.toren.foodbookapp.ui.viewmodel.FoodViewModel

class FoodFragment : Fragment() {

    private val viewModel: FoodViewModel by viewModels()
    private var _binding: FoodFragmentBinding? = null
    private val binding get() = _binding!!
    private var materialAdapter = MaterialItemAdapter(arrayListOf())
    private val args: FoodFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoodFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val food = args.gelenYemek
        materialAdapter.updateList(food.malzemeler)

        binding.apply {
            foodName.text = food.yemekIsmi
            foodUser.text = food.user
            foodAciklama.text = food.aciklama
            foodHazirlanis.text = food.hazirlanis

            recyclerViewMalzemeler.layoutManager = LinearLayoutManager(view.context)
            recyclerViewMalzemeler.adapter = materialAdapter

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}