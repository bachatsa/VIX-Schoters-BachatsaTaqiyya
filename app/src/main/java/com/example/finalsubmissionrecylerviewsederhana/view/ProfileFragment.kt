package com.example.finalsubmissionrecylerviewsederhana.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.finalsubmissionrecylerviewsederhana.R
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.tvLinkGithub
import kotlinx.android.synthetic.main.fragment_profile.tvNama
import kotlin.system.exitProcess


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       Glide.with(this).load("https://avatars.githubusercontent.com/u/35648165?v=4").into(imgProfile)
        tvNama.text = "Nama: "+"Bachatsa Taqiyya"
        tvLinkGithub.text = "Username Github: "+"Bachatsa"

        tvLinkGithub.setOnClickListener {
            val uri = Uri.parse("https://github.com/bachatsa") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            exitProcess(-1)
        }
    }
}