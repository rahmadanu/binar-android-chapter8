package com.binar.movieapp.presentation.ui.user.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.work.WorkInfo
import com.binar.movieapp.R
import com.binar.movieapp.data.firebase.model.User
import com.binar.movieapp.data.local.preference.UserPreferences
import com.binar.movieapp.databinding.FragmentProfileBinding
import com.binar.movieapp.presentation.ui.user.MainActivity
import com.binar.movieapp.workers.KEY_IMAGE_URI
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("all")
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var userDetails: User

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListener()
        viewModel.outputWorkInfos.observe(viewLifecycleOwner, workInfosObserver())
        binding.tvEmail.text = "Email address"
    }

    private fun setOnClickListener() {
        binding.btnUpdateProfile.setOnClickListener {
            navigateToUpdateProfile()
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.METHOD, "update profile")
            analytics.logEvent("click_update", bundle)
        }
        binding.btnLogout.setOnClickListener {
            viewModel.setUserLogin(false)
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }
        binding.btnBlurVersion.setOnClickListener {
            //viewModel.applyBlur(3)
            throw RuntimeException("Test Crash") // Force a crash
        }
        binding.btnSeeBlur.setOnClickListener {
            viewModel.outputUri?.let { currentUri ->
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                startActivity(actionView)
            } ?: kotlin.run {
                Toast.makeText(requireContext(), "Add your profile image first", Toast.LENGTH_LONG).show()
            }
        }
        binding.ivProfileImage.setOnClickListener {
            Toast.makeText(requireContext(), "Click update profile to update your profile image", Toast.LENGTH_LONG).show()
        }
        binding.btnCancel.setOnClickListener {
            viewModel.cancelWork()
        }
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
            btnCancel.visibility = View.VISIBLE
            btnBlurVersion.visibility = View.GONE
            btnSeeBlur.visibility = View.GONE
        }
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private fun showWorkFinished() {
        with(binding) {
            pbLoading.visibility = View.GONE
            btnCancel.visibility = View.GONE
            btnBlurVersion.visibility = View.VISIBLE
        }
    }

    private fun observeData() {
/*        viewModel.getUser().observe(viewLifecycleOwner) {
            bindDataToView(it)
        }*/
        viewModel.getUserDetail(this@ProfileFragment)
    }

    fun bindDataToView(user: User) {
        userDetails = user
        user.let {
            binding.apply {
                tvUsername.text = getString(R.string.profile_username, user.username)
                tvEmail.text = getString(R.string.profile_email, user.email)
                tvFullName.text = getString(R.string.profile_full_name, user.fullName)
                tvDateOfBirth.text = getString(R.string.profile_date_of_birth, user.dateOfBirth)
                tvAddress.text = getString(R.string.profile_address, user.address)

                user.profileImage.let {
                    if (it.isEmpty().not()) {
                        Glide.with(this@ProfileFragment)
                            .load(convertStringToBitmap(it))
                            .into(binding.ivProfileImage)
                        viewModel.getImageUri(requireActivity(), convertStringToBitmap(it))
                    }
                }
            }
        }
    }

    private fun convertStringToBitmap(string: String): Bitmap {
        val imageBytes = Base64.decode(string, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun navigateToUpdateProfile() {
        val options = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, false)
            .build()
        val bundle = Bundle()
        bundle.putParcelable(USER_DETAILS, userDetails)
        findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment, bundle, options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val USER_DETAILS = "user_details"
    }
}