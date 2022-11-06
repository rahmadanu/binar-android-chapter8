package com.binar.movieapp.presentation.ui.user.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.binar.movieapp.data.local.preference.UserPreferences
import com.binar.movieapp.data.repository.UserRepository
import com.binar.movieapp.workers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: UserRepository, @ApplicationContext application: Context): ViewModel() {

    fun getUserDetail(fragment: Fragment) {
        repository.getUserDetail(fragment)
    }

    fun updateUserProfile(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        repository.updateUserProfile(fragment, userHashMap)
    }

    fun getUser(): LiveData<UserPreferences> {
        return repository.getUser().asLiveData()
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            repository.setUserLogin(isLogin)
        }
    }

    fun updateUser(user: UserPreferences) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun setProfileImage(image: String) {
        viewModelScope.launch {
            repository.setProfileImage(image)
        }
    }

    private var imageUri: Uri? = null
    internal var outputUri: Uri? = null
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     * @return Data which contains the Image Uri as a String
     */
    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal fun applyBlur(blurLevel: Int) {
        // Add WorkRequest to Cleanup temporary images
        var continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )

        // Add WorkRequests to blur the image the number of times requested
        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }

            continuation = continuation.then(blurBuilder.build())
        }

        // Create charging constraint
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        // Add WorkRequest to save the image to the filesystem
        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .setConstraints(constraints)
            .addTag(TAG_OUTPUT)
            .build()
        continuation = continuation.then(save)

        // Actually start the work
        continuation.enqueue()
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

    fun getImageUri(context: Context, image: Bitmap) {
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            context.getContentResolver(),
            image,
            "BlurredImage_" + Calendar.getInstance().time,
            null
        )
        imageUri = Uri.parse(path)
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }
}