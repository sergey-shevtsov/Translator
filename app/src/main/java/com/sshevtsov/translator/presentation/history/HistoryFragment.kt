package com.sshevtsov.translator.presentation.history

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sshevtsov.translator.R
import com.sshevtsov.translator.databinding.FragmentHistoryBinding
import com.sshevtsov.translator.presentation.history.adapter.HistoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HistoryAdapter

    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.history_action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                viewModel.clearHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewStateChanges()
        viewModel.fetchHistory()
    }

    private fun observeViewStateChanges() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { updateUI(it) }
        }
    }

    private fun updateUI(state: HistoryViewState) {
        hideViews()
        when (state) {
            is HistoryViewState.EmptyHistory -> {
                binding.emptyHistoryTextView.isVisible = true
            }
            is HistoryViewState.Error -> {
                binding.errorImage.isVisible = true
            }
            is HistoryViewState.Loading -> {
                binding.progressIndicator.isVisible = true
            }
            is HistoryViewState.Success -> {
                adapter = HistoryAdapter(state.data)
                binding.historyRecycler.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
                binding.historyRecycler.adapter = adapter
                binding.historyRecycler.isVisible = true
            }
        }
    }

    private fun hideViews() {
        binding.historyRecycler.isVisible = false
        binding.progressIndicator.isVisible = false
        binding.emptyHistoryTextView.isVisible = false
        binding.errorImage.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}