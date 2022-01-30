package com.sshevtsov.translator.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sshevtsov.translator.R
import com.sshevtsov.translator.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()
    private val dataModel by lazy { args.dataModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.headerTextView.text = dataModel.text
        binding.translationTextView.text = dataModel.meanings[0].translation.text

        Glide.with(this)
            .load("https:${dataModel.meanings[0].imageUrl}")
            .error(R.drawable.ic_load_error_vector)
            .placeholder(R.drawable.ic_no_photo_vector)
            .apply(RequestOptions().centerCrop())
            .into(binding.previewImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}