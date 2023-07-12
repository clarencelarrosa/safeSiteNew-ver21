package com.example.safesite.search.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.safesite.R
import com.example.safesite.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentDetailsBinding.bind(view)

        binding.apply {
            val photo = args.photo

            Glide.with(this@DetailsFragment)
                .load(photo.urls.full)
                .error(R.drawable.ic_error)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible=false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        textViewDescription.isVisible = photo.descrip != null
                        return false
                    }
                })
                .into(imageView)
            textViewDescription.text = photo.descrip

            val uri = Uri.parse(photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            textViewCreator.apply {
                text = "Image by ${photo.user.name} on Unsplash" //text printed when opening the image. This will display who created the image
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = false //underline the text for creator when opening the image and setting it in false(will remove the underline)
            }
        }
    }
}