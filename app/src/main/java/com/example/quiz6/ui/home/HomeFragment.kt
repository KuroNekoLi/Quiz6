package com.example.quiz6.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.quiz6.MainActivity
import com.example.quiz6.data.util.Resource
import com.example.quiz6.databinding.FragmentHomeBinding
import com.example.quiz6.ui.UbikeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel:UbikeViewModel

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
        viewUbikeList()
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
                        val data = it.toList().toString()
                        Log.i("LinLi",  "data:" + data);
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
}