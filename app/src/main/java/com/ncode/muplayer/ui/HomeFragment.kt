package com.ncode.muplayer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.ncode.muplayer.R
import com.ncode.muplayer.ClickedItem.OnItemClicked
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.playeradapter.MusicPlayerAdapter
import com.ncode.muplayer.viewModel.PlayerViewModel
import kotlinx.android.synthetic.main.music_list_recyclerview.view.*



class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    lateinit var playerViewModel: PlayerViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.toolbar))
        view.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "MuPlayer"

        recyclerView = view.music_list_recycler
        val adapter = MusicPlayerAdapter(context!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        playerViewModel = ViewModelProvider(this).get(PlayerViewModel :: class.java)
        playerViewModel.getAllSongsFromProvider()
        playerViewModel.allSongs.observe(viewLifecycleOwner, {songs ->
            songs.let { adapter.setSize(it) }
        })
    }



}