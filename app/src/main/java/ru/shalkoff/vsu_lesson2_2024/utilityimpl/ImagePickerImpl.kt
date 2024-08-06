package ru.shalkoff.vsu_lesson2_2024.utilityimpl

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ru.shalkoff.vsu_lesson2_2024.utility.IImagePicker

class ImagePickerImpl(
    private val activity: AppCompatActivity,
    private val onImageSelected: (Uri?) -> Unit
): IImagePicker {

    private var getContent: ActivityResultLauncher<Intent>

    init {
        getContent = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                onImageSelected(selectedImageUri)
            } else {
                onImageSelected(null)
            }
        }
    }

    override fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getContent.launch(intent)
    }

}