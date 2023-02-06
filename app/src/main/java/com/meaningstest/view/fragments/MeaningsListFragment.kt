package com.meaningstest.view.fragments

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.meaningstest.R
import com.meaningstest.adapter.UsersListAdapter
import com.meaningstest.databinding.FragmentMeaningsListBinding
import com.meaningstest.utils.DataHandler
import com.meaningstest.viewmodel.MeaningsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MeaningsListFragment : Fragment(R.layout.fragment_meanings_list) {
    private val viewModel: MeaningsViewModel by viewModels()

    @Inject
    lateinit var usersListAdapter: UsersListAdapter

    private lateinit var binding: FragmentMeaningsListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMeaningsListBinding.bind(view)
        init()
        initObservers()
    }

    private fun init() {
        binding.btnSearch.setOnClickListener {
            if (!TextUtils.isEmpty(binding.etInput.text.toString())) {
                hideKeyboard(it)
                binding.progressBar.visibility = View.VISIBLE
                viewModel.getMeaningsList(binding.etInput.text.toString())
            } else {
                Toast.makeText(activity, getString(R.string.empty_validation), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.recyclerView.apply {
            adapter = usersListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initObservers() {
        viewModel.meaningsList.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val dataList = dataHandler.data
                    if (dataList?.isNotEmpty()!! && dataList[0].lfs.isNotEmpty()) {
                        dataList[0].lfs.let { data ->
                            usersListAdapter.differ.submitList(data)
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.no_data_found),
                            Toast.LENGTH_SHORT
                        ).show()
                        usersListAdapter.differ.submitList(emptyList())
                        binding.etInput.setText("")
                    }

                }
                is DataHandler.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, dataHandler.message, Toast.LENGTH_SHORT).show()
                    usersListAdapter.differ.submitList(emptyList())
                    binding.etInput.setText("")
                }
                is DataHandler.LOADING -> binding.progressBar.visibility = View.VISIBLE
            }

        }
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}