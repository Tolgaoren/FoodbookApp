package com.toren.foodbookapp.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.toren.foodbookapp.adapter.MaterialItemAdapter
import com.toren.foodbookapp.databinding.SearchFoodFragmentBinding
import com.toren.foodbookapp.model.Yemek
import com.toren.foodbookapp.ui.viewmodel.SearchFoodViewModel

class SearchFoodFragment : Fragment(), MaterialItemAdapter.OnItemClickListener {

    private val viewModel: SearchFoodViewModel by viewModels()
    private var _binding: SearchFoodFragmentBinding? = null
    private val binding get() = _binding!!
    private val malzemeListesi = ArrayList<String>(arrayListOf())
    private var materialAdapter = MaterialItemAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFoodFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = materialAdapter

            addMalzeme.setOnClickListener {
                val input = malzeme.editText!!.text.toString().trim()
                if (malzemeListesi.contains(input)) {
                    malzeme.editText!!.error = "Lütfen aynı malzemeyi girmeyiniz..."
                } else if (malzeme.editText!!.text != null && input.length > 1) {
                    malzeme.editText!!.error = null
                    malzemeListesi.add(input)
                    materialAdapter.updateList(malzemeListesi)
                } else {
                    malzeme.editText!!.error = "Lütfen geçerli malzeme giriniz..."
                }

                malzeme.editText!!.text = null
            }

            yemekGetir.setOnClickListener {
                viewModel.yemekleriGetir()
                loadFoodData()
            }

        }

    }

    private fun loadFoodData() {
        Log.d("TAG",malzemeListesi.toList().toString())
        viewModel.foodList.observe(viewLifecycleOwner, {
            it?.let {
                val sonuclarYemek: ArrayList<Yemek> = arrayListOf()
                val yemekler = it
                for (i in yemekler) {
                    if (malzemeListesi.containsAll(i.malzemeler)) {
                        sonuclarYemek.add(i)
                    }
                }
                if (sonuclarYemek.isEmpty()) {
                    Toast.makeText(this.context, "Sonuç bulunamadı.", Toast.LENGTH_SHORT).show()
                } else {
                    actionToFoods(sonuclarYemek.toList())
                }
                Log.d("TAG", sonuclarYemek.toList().toString())
            }
        })
        malzemeListesi.clear()
        materialAdapter.temizle()
    }

    override fun onItemClick(position: Int) {
        materialAdapter.removeItem(position)
    }

    private fun actionToFoods(sonuclarYemek: List<Yemek>) {
        val action = SearchFoodFragmentDirections.actionSearchFoodFragmentToFoodsFragment(sonuclarYemek.toTypedArray())
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}