package com.ncode.muplayer.ui

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
import com.ncode.muplayer.R
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.playeradapter.MusicPlayerAdapter
import com.ncode.muplayer.presenter.MediaPlayerPresenter
import kotlinx.android.synthetic.main.music_list_recyclerview.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log


class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var songDataList : MutableList<MusicPlayerModel> = mutableListOf()

    //Presenter
    lateinit var presenter: MediaPlayerPresenter

    //Adapter
    private var viewAdapter : MusicPlayerAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.toolbar))
        view.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "MuPlayer"

        
        CoroutineScope(Dispatchers.IO).launch {
            presenter = MediaPlayerPresenter(context!!)
            val isSongPresentInDb = presenter.getSize()
            if(!isSongPresentInDb) {
                presenter.getSongFromProvider()
            }

            withContext(Dispatchers.Main) {
                songDataList = presenter.getAllSongs()
                Log.i("home", songDataList.size.toString())
                recyclerAdapter(songDataList)

            }
        }
             recyclerAdapter(songDataList)
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