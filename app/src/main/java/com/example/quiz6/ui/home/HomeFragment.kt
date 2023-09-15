package com.example.quiz6.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz6.MainActivity
import com.example.quiz6.R
import com.example.quiz6.data.model.UbikeInfoItem
import com.example.quiz6.data.util.Resource
import com.example.quiz6.databinding.FragmentHomeBinding
import com.example.quiz6.presentation.adapter.SearchHistoryAdapter
import com.example.quiz6.presentation.adapter.UbikeInfoAdapter
import com.example.quiz6.ui.UbikeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel:UbikeViewModel
    private lateinit var ubikeInfoAdapter:UbikeInfoAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewUbikeList()
        binding.ivSearch.setOnClickListener { onSearchClicked() }
        binding.editSearch.setOnClickListener { showHistorySearchResult() }
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.rvSearch.visibility = View.GONE
                    binding.editSearch.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.black))
                } else {
                    binding.rvSearch.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showHistorySearchResult() {
        binding.rvSearch.visibility = View.VISIBLE
        viewModel.searchHistoryLiveData.observe(viewLifecycleOwner){
            searchHistoryAdapter.submitList(it)
            searchHistoryAdapter.notifyDataSetChanged()
        }
    }

    private fun initAdapter() {
        ubikeInfoAdapter = UbikeInfoAdapter()
        binding.rvUBike.apply {
            adapter = ubikeInfoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        searchHistoryAdapter = SearchHistoryAdapter(){
            //when item clicked
            searchResult ->
            binding.editSearch.setText(searchResult)
            binding.editSearch.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_green))
            binding.rvSearch.visibility = View.GONE

        }
        binding.rvSearch.apply {
            adapter = searchHistoryAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun viewUbikeList() {
        viewModel= (activity as MainActivity).viewModel
        viewModel.getUbikeInfo()
        viewModel.uBikeInfoLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
//                    hideProgressBar()
                    response.data?.let {
                        val ubikeInfoItemList : List<UbikeInfoItem> = it.toList()
                        Log.i("LinLi", ubikeInfoItemList.toString());
                        ubikeInfoAdapter.submitList(ubikeInfoItemList)
                    }
                }

                is Resource.Error -> {
//                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
//                    showProgressBar()
                }
            }
        }
    }

    private fun onSearchClicked(){
        val query = binding.editSearch.text.toString()
        if (query.isEmpty())
            return
        //add result to history
        viewModel.addToSearchHistory(query)
        //filter data and renew adapter
        viewModel.filterUbikeInfo(query)
        viewModel.filteredUbikeInfo.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val filteredList = resource.data
                    ubikeInfoAdapter.submitList(filteredList)
                }
                is Resource.Error -> {
                    // Handle error
                }
                is Resource.Loading -> {
                    // Handle loading
                }
            }
        }
    }
}