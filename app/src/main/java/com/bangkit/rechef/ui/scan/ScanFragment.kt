package com.bangkit.rechef.ui.scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bangkit.rechef.R
import com.bangkit.rechef.databinding.FragmentScanBinding
import com.bangkit.rechef.ui.main.MainActivity
import com.bangkit.rechef.ui.utils.getImageUri
import com.bangkit.rechef.ui.utils.reduceFileImage
import com.bangkit.rechef.ui.utils.uriToFile

class ScanFragment : Fragment() {

    private lateinit var binding: FragmentScanBinding
    private var currentImageUri: Uri? = null
    private val viewModel: ScanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadButton.visibility = View.GONE

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.cameraButton.setOnClickListener {
            startCamera()
        }

        binding.uploadButton.setOnClickListener {
            uploadPhoto()
        }

        viewModel.predictionResponse.observe(viewLifecycleOwner, Observer { response ->
            val className = response.className

            if(className != "Unknown"){
                Toast.makeText(requireContext(), "${response.className} detected", Toast.LENGTH_SHORT).show()
                navigateToRecipeFragment(className)
            } else {
                Toast.makeText(requireContext(), "No food ingredient detected", Toast.LENGTH_LONG).show()
                hideImage()
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imageView.setImageURI(it)
            binding.scanText.visibility = View.GONE
            binding.uploadButton.visibility = View.VISIBLE
        }
    }

    private fun hideImage() {
        binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_rechef_nofill, null))
        binding.scanText.visibility = View.VISIBLE
        binding.uploadButton.visibility = View.GONE
    }

    private fun uploadPhoto() {
        currentImageUri?.let { uri ->
            val file = uriToFile(uri, requireContext()).reduceFileImage()
            viewModel.uploadImage(file)
        }
    }

    private fun navigateToRecipeFragment(className: String) {
        val bundle = Bundle().apply {
            putString("className", className)
        }
        val fragment = RecipeFragment().apply {
            arguments = bundle
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
