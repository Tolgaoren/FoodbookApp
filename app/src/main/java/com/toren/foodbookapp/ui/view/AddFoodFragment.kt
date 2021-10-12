package com.toren.foodbookapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.toren.foodbookapp.ui.viewmodel.AddFoodViewModel
import com.toren.foodbookapp.R
import com.toren.foodbookapp.adapter.MaterialAdapter
import com.toren.foodbookapp.databinding.AddFoodFragmentBinding
import com.toren.foodbookapp.model.Material

class AddFoodFragment : Fragment() {

    private val viewModel: AddFoodViewModel by viewModels()
    private lateinit var binding: AddFoodFragmentBinding
    private val materialList = ArrayList<Material>(arrayListOf())
    private var materialAdapter = MaterialAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddFoodFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val materialTypeItems = listOf("adet", "ml", "gr", "kg", "su bardağı", "yemek kaşığı", "tutam", "çay kaşığı")
        val materialTypeAdapter = ArrayAdapter(requireContext(), R.layout.list_item, materialTypeItems)
        (binding.inputMaterialType.editText as AutoCompleteTextView).setAdapter(materialTypeAdapter)

        val foodCategoryItems = listOf(
            "Kahvaltılık Tarifler",
            "Hamur işi Tarifleri",
            "Diyet Tarifler",
            "Tatlı Tarifleri",
            "Makarna ve Pilav Tarifleri",
            "Vegan Tarifler",
            "Çorba Tarifleri ",
            "Bakliyat Yemekleri",
            "Et Yemekleri",
            "Sebze Yemekleri",
            "Glutensiz Tarifler",
            "Fast-Food Tarifler",
            "Çocuklar için Tarifler",
            "İçecek ve Kokteyl Tarifleri",
            "Reçel, Turşu ve Salça Tarifleri",
            "Salata, Meze ve Sos Tarifleri",
            "Aperatifler"
        )
        val foodCategoryAdapter = ArrayAdapter(requireContext(), R.layout.list_item, foodCategoryItems)
        (binding.inputFoodCategory.editText as AutoCompleteTextView).setAdapter(foodCategoryAdapter)

        binding.apply {

            recylerViewMaterials.layoutManager = LinearLayoutManager(view.context)
            recylerViewMaterials.adapter = materialAdapter

            buttonAddMaterial.setOnClickListener {
                val materialAmount = inputMaterialAmount.editText!!.text.toString()
                val materialType = inputMaterialType.editText!!.text.toString()
                val materialName = inputMaterialName.editText!!.text.toString()

                if(materialControl(materialAmount,materialType,materialName)) {
                    val material = "$materialAmount $materialType $materialName"

                    materialList.add(Material(material))
                    materialAdapter.updateList(materialList)
                    inputMaterialAmount.editText!!.text = null
                    inputMaterialType.editText!!.text = null
                    inputMaterialName.editText!!.text= null
                }

            }
        }

    }

    private fun materialControl(materialAmount: String, materialType: String, materialName: String): Boolean {
        var control = true

        if (materialAmount.isEmpty()) {
            binding.inputMaterialAmount.error = " "
            control = false
        } else {
            binding.inputMaterialAmount.error = null
        }
        if (materialType.isEmpty()) {
            binding.inputMaterialType.error = " "
            control = false
        } else {
            binding.inputMaterialType.error = null
        }
        if (materialName.isEmpty()) {
            binding.inputMaterialName.error = " "
            control = false
        } else {
            binding.inputMaterialName.error = null
        }

        return control
    }

}