package com.sshevtsov.translator.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.sshevtsov.translator.R
import com.sshevtsov.translator.data.api.TranslatorApi
import com.sshevtsov.translator.data.mappers.DataModelMapper
import com.sshevtsov.translator.data.repositories.RepositoryImplementation
import com.sshevtsov.translator.databinding.FragmentSearchBinding
import com.sshevtsov.translator.presentation.clearFocus
import com.sshevtsov.translator.presentation.hideKeyboard
import com.sshevtsov.translator.presentation.search.adapter.SearchAdapter
import com.sshevtsov.translator.util.SchedulersProviderImplementation

class SearchFragment : Fragment(R.layout.fragment_search), SearchContract.View {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: SearchPresenter

    private lateinit var adapter: SearchAdapter

    private lateinit var searchButtonClickListener: View.OnClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        initPresenter()
        initRecycler()
        initSearchListener()
    }

    private fun initPresenter() {
        presenter = SearchPresenter(
            RepositoryImplementation(TranslatorApi.create(), DataModelMapper()),
            SchedulersProviderImplementation()
        )
        presenter.attachView(this)
    }

    private fun initRecycler() {
        adapter = SearchAdapter(emptyList())
        binding.resultRecycler.adapter = adapter
        binding.resultRecycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun initSearchListener() {
        searchButtonClickListener = View.OnClickListener {
            //So far isOnline is always true
            presenter.getData(binding.searchEditText.text.toString(), true)
            cancelInput()
        }

        binding.searchInputLayout.setEndIconOnClickListener(searchButtonClickListener)
    }

    override fun renderData(viewState: SearchViewState) {
        hideAllViews()

        when (viewState) {
            is SearchViewState.Loading -> binding.loadingFrame.isVisible = true
            is SearchViewState.CallToAction -> binding.callToActionFrame.isVisible = true
            is SearchViewState.EmptyResult -> binding.emptyResultFrame.isVisible = true
            is SearchViewState.Success -> {
                adapter.setData(viewState.data)
                binding.resultRecycler.isVisible = true
            }
            is SearchViewState.Error -> {
                showErrorInSnackbar(getString(R.string.search_error_text))
                binding.errorFrame.isVisible = true
            }
        }
    }

    private fun hideAllViews() {
        binding.callToActionFrame.isVisible = false
        binding.emptyResultFrame.isVisible = false
        binding.resultRecycler.isVisible = false
        binding.errorFrame.isVisible = false
        binding.loadingFrame.isVisible = false
    }

    private fun showErrorInSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(
                getString(R.string.search_try_again),
                searchButtonClickListener
            )
            .show()
    }

    private fun cancelInput() {
        hideKeyboard()
        clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.detachView()
    }

}