package com.ncode.muplayer

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ncode.muplayer.navigationhost.NavigationHost
import com.ncode.muplayer.ui.HomeFragment

class MuPlayerActivity : AppCompatActivity(), NavigationHost {

   lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muplayer)


        homeFragment = HomeFragment()
        navigateTo(homeFragment, false)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun navigateTo(fragement: Fragment, addToBackStack: Boolean) {

       val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragement)

      if(addToBackStack) {
          transaction.addToBackStack(null)
      }

        transaction.commit()
    }
}