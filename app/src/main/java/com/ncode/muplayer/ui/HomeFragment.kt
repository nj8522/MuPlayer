package com.ncode.muplayer.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.ncode.muplayer.MuPlayerActivity
import com.ncode.muplayer.R
import com.ncode.muplayer.contract.MediaPlayerContract
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.playeradapter.MusicPlayerAdapter
import com.ncode.muplayer.presenter.MediaPlayerPresenter
import kotlinx.android.synthetic.main.music_list_recyclerview.view.*
import kotlinx.coroutines.*
import kotlin.math.log


class HomeFragment : Fragment(), MediaPlayerContract.MediaPlayerView {

    lateinit var recyclerView: RecyclerView

    //Presenter
    private var presenter : MediaPlayerPresenter? = null

    //Adapter
    private var viewAdapter : MusicPlayerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MediaPlayerPresenter(context!!)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.toolbar))
        view.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "MuPlayer"

        initView()
    }


    override fun initView() {
      try {

          if(!presenter!!.getSize()) {
              presenter?.retrieveSongFromProvider()
          }

          updateAdapter()

      } catch (e : Exception) {
          Log.i("error", "initView: ${e.message.toString()}")
      }

    }


    override fun updateAdapter() {
        val songList = presenter?.getAllSongs()
        recyclerAdapter(songList!!)
    }

    private fun recyclerAdapter(songList : MutableList<MusicPlayerModel>) {

        if(viewAdapter == null) {

            viewAdapter = MusicPlayerAdapter(context!!)
            recyclerView = view!!.music_list_recycler
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = viewAdapter
            recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
        viewAdapter!!.setSize(songList)
    }


}