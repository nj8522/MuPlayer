package com.ncode.muplayer.playeradapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ncode.muplayer.R
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.navigationhost.NavigationHost
import com.ncode.muplayer.ui.MusicPlayerFragment
import kotlinx.android.synthetic.main.song_card_view.view.*

class MusicPlayerAdapter internal constructor(context: Context) : RecyclerView.Adapter<MusicPlayerAdapter.PlayerViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private  var songsList : MutableList<MusicPlayerModel> = mutableListOf()

    fun setSize(songs : List<MusicPlayerModel>) {
        this.songsList = songs as MutableList<MusicPlayerModel>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {

        val view = inflater.inflate(R.layout.song_card_view, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {

        Log.i("adapter", songsList.size.toString())

        val current = songsList[position]

        holder.titleName.text = current.songName
        Log.i("adapter", current.songName)

        holder.artistName.text = current.artistInfo
        Log.i("adapter", current.artistInfo)
    }

    override fun getItemCount() = songsList.size



    class PlayerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val songCard = itemView.song_card
        val titleName = itemView.song_name
        val artistName = itemView.artist_name

        init {
            songCard.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val bundle = Bundle()
            bundle.putInt("position", adapterPosition)
            val musicPlayerFragment = MusicPlayerFragment()
            musicPlayerFragment.arguments = bundle
            (v?.context as NavigationHost).navigateTo(musicPlayerFragment, true)
        }
    }
}