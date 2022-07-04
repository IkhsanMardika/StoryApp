package com.karyadika.storyapp.ui.main.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.karyadika.storyapp.R
import com.karyadika.storyapp.data.local.room.StoryModel

import com.karyadika.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetail()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setDetail() {
        val user = intent.getParcelableExtra<StoryModel>(EXTRA_USER)
        if (user != null) {
            binding.apply {
                Glide.with(this@DetailActivity).load(user.photoUrl).into(imgStories)
                tvName.text = user.name
                tvDesc.text = user.description
                tvCreateDate.text = binding.root.resources.getString(R.string.created_add, user.createdAt)

                supportActionBar!!.title = user.name
            }
        }
    }


    companion object {
        const val EXTRA_USER = "extra_user"
    }


}