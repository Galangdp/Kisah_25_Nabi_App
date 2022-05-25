package com.galang.kisah25nabi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.galang.kisah25nabi.data.KisahResponse
import com.galang.kisah25nabi.databinding.ActivityMainBinding
import com.galang.kisah25nabi.ui.KisahAdapter
import com.galang.kisah25nabi.ui.MainViewModel
import com.galang.kisah25nabi.ui.detail.DetailActivity
import com.galang.kisah25nabi.utils.OnItemClickCallback

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var _viewModel : MainViewModel? = null
    private val viewModel get() = _viewModel as MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getData()

        //OBSERVE
        viewModel.kisahResponse.observe(this) { showData(it) }
        viewModel.isLoading.observe(this){ showLoading(it) }
        viewModel.isError.observe(this){ showError(it) }

    }

    private fun showData(data: List<KisahResponse>?) {
        binding.recyclerMain.apply {
            val mAdapter = KisahAdapter()
            mAdapter.setData(data)
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = mAdapter
            mAdapter.setOnItemClickCallback(object : OnItemClickCallback{
                override fun onItemClicked(item: KisahResponse) {
                    startActivity(Intent(applicationContext, DetailActivity::class.java)
                        .putExtra(DetailActivity.EXTRA_DATA, item))
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean?) {
        if (isLoading == false){
            binding.progressMain.visibility = View.VISIBLE
            binding.recyclerMain.visibility = View.INVISIBLE
        } else {
            binding.progressMain.visibility = View.INVISIBLE
            binding.recyclerMain.visibility = View.VISIBLE
        }
    }

    private fun showError(error: Throwable?) {

        //KALO BOOLEAN
//        when (error){
//            true -> showLayoutError()
//            false -> showLayoutSucces()
//        }

        Log.e("MainActivity", "showError: $error")
    }
}