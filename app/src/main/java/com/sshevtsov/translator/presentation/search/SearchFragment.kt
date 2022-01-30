package com.sshevtsov.translator.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.sshevtsov.translator.R
import com.sshevtsov.translator.databinding.FragmentSearchBinding
import com.sshevtsov.translator.presentation.clearFocus
import com.sshevtsov.translator.presentation.hideKeyboard
import com.sshevtsov.translator.presentation.search.adapter.SearchAdapter
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    private val adapter by lazy {
        SearchAdapter(
            listener = { dataModel ->
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                        dataModel = dataModel,
                        label = dataModel.text
                    )
                )
            }
        )
    }

    private val queryStateFlow = MutableStateFlow("")

    private val searchButtonClickListener by lazy {
        View.OnClickListener {
            //So far isOnline is always true
            viewModel.getData(binding.searchEditText.text.toString(), true)
            hideErrorSnackbar()
            cancelInput()
        }
    }

    private val errorSnackbar: Snackbar by lazy {
        Snackbar.make(
            binding.root,
            getString(R.string.search_error_text),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(
                getString(R.string.search_try_again),
                searchButtonClickListener
            )
    }

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
        observeViewStateUpdates()
        initRecycler()
        setupSearchListener()
        setupSearchStateFlow()
    }

    private fun observeViewStateUpdates() =
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { updateUI(it) }
        }

    private fun initRecycler() {
        binding.resultRecycler.adapter = adapter
        binding.resultRecycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setupSearchListener() {
        binding.searchInputLayout.setEndIconOnClickListener(searchButtonClickListener)
        binding.searchEditText.setOnEditorActionListener { view, _, _ ->
            searchButtonClickListener.onClick(view)
            true
        }
    }

    private fun setupSearchStateFlow() {
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            queryStateFlow.value = text.toString()
        }

        lifecycleScope.launch {
            queryStateFlow
                .debounce(500)
                .filter { query ->
                    if (query.isEmpty()) {
                        binding.searchEditText.setText("")
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .collect { query ->
                    viewModel.getData(query, true)
                    hideErrorSnackbar()
                }
        }
    }

    private fun updateUI(viewState: SearchViewState) {
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
                showErrorSnackbar()
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

    private fun showErrorSnackbar() =
        errorSnackbar.show()


    private fun hideErrorSnackbar() =
        errorSnackbar.dismiss()

    private fun cancelInput() {
        hideKeyboard()
        clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}