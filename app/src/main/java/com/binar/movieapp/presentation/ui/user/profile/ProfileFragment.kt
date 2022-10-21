package com.binar.movieapp.presentation.ui.user.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.work.WorkInfo
import com.binar.movieapp.R
import com.binar.movieapp.data.local.preference.UserPreferences
import com.binar.movieapp.databinding.FragmentProfileBinding
import com.binar.movieapp.di.UserServiceLocator
import com.binar.movieapp.presentation.ui.user.MainActivity
import com.binar.movieapp.util.viewModelFactory
import com.binar.movieapp.workers.KEY_IMAGE_URI
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModelFactory {
        ProfileViewModel(UserServiceLocator.provideUserRepository(requireContext()), requireActivity().application)
    }
    //test

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnUpdateProfile.setOnClickListener {
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.profileFragment, false)
                .build()
            findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment, null, options)
        }
        binding.btnLogout.setOnClickListener {
            viewModel.setUserLogin(false)
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }
        binding.btnBlurImage.setOnClickListener {
            viewModel.applyBlur(3)
        }
        binding.btnSeeBlur.setOnClickListener {
            viewModel.outputUri?.let { currentUri ->
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                actionView.resolveActivity(requireActivity().packageManager)?.run {
                    startActivity(actionView)
                }
            }
        }
        binding.btnCancelBlur.setOnClickListener {
            viewModel.cancelWork()
        }
        viewModel.outputWorkInfos.observe(viewLifecycleOwner, workInfosObserver())
    }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->

            // Note that these next few lines grab a single WorkInfo if it exists
            // This code could be in a Transformation in the ViewModel; they are included here
            // so that the entire process of displaying a WorkInfo is in one location.

            // If there are no matching work info, do nothing
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }

            // We only care about the one output status.
            // Every continuation has only one worker tagged TAG_OUTPUT
            val workInfo = listOfWorkInfo[0]

            if (workInfo.state.isFinished) {
                showWorkFinished()

                // Normally this processing, which is not directly related to drawing views on
                // screen would be in the ViewModel. For simplicity we are keeping it here.
                val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)

                // If there is an output file show "See File" button
                if (!outputImageUri.isNullOrEmpty()) {
                    viewModel.setOutputUri(outputImageUri)
                    binding.btnSeeBlur.visibility = View.VISIBLE
                }
            } else {
                showWorkInProgress()
            }
        }
    }

    /**
     * Shows and hides views for when the Activity is processing an image
     */
    private fun showWorkInProgress() {
        with(binding) {
            pbLoading.visibility = View.VISIBLE
            btnCancelBlur.visibility = View.VISIBLE
            btnBlurImage.visibility = View.GONE
            btnSeeBlur.visibility = View.GONE
        }
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private fun showWorkFinished() {
        with(binding) {
            pbLoading.visibility = View.GONE
            btnCancelBlur.visibility = View.GONE
            btnBlurImage.visibility = View.VISIBLE
            btnSeeBlur.visibility = View.VISIBLE
        }
    }

    private fun observeData() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            bindDataToView(it)
        }
    }

    private fun bindDataToView(user: UserPreferences?) {
        user?.let {
            binding.apply {
                tvUsername.text = user.username
                tvEmail.text = user.email
                tvFullName.text = user.fullName
                tvDateOfBirth.text = user.dateOfBirth
                tvAddress.text = user.address

                user.profileImage?.let {
                    if (it.isEmpty().not()) {
                        Glide.with(this@ProfileFragment)
                            .load(convertStringToBitmap(it))
                            .into(binding.ivProfileImage)
                    }
                }
            }
        }
    }

    private fun convertStringToBitmap(string: String): Bitmap {
        val imageBytes = Base64.decode(string, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}