package com.healthtunnel.ui.ecom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.ui.ecom.adapter.FilterAdapter
import com.healthtunnel.ui.ecom.adapter.FilterProdAdapter
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {
    var prodId: String? = null
    private var list: ArrayList<String>? = null

    val viewModel: EcomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        viewModel.getFilterCat(intent.getStringExtra("id"))
        cat.setOnClickListener {
            cat.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);
            prod.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_grey, 0);
            viewModel.getFilterCat(intent.getStringExtra("id"))
        }

        apply.setOnClickListener {
            val intent = Intent()
            intent.putExtra("prodId", prodId)
            intent.putExtra("list", list)
            setResult(2, intent)
            finish()
        }

        prod.setOnClickListener {
            if (prodId.isNullOrEmpty()) {
                Toast.makeText(this@FilterActivity,
                    resources.getString(R.string.title_select_prod_first), Toast.LENGTH_SHORT)
                    .show()
            } else {
                cat.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_grey, 0);
                prod.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);
                viewModel.getFilterProd(prodId)
            }
        }

        viewModel.showProgress.observe(this, Observer {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        })

        viewModel.filterCatResult.observe(this, Observer {
            catProdRV.adapter = FilterAdapter(it, object : FilterAdapter.OnItemClickListener {
                override fun onClick(pos: Int) {
                    list = ArrayList()
                    prodId = it[pos]._id
                    cat.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_grey, 0);
                    prod.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);
                    viewModel.getFilterProd(it[pos]._id)
                }

            })
        })

        viewModel.filterProdResult.observe(this, Observer {
            catProdRV.adapter =
                FilterProdAdapter(it, object : FilterProdAdapter.OnItemClickListener {
                    override fun onClick(pos: Int) {
                        for (item in it) {
                            if (item.isChecked) {
                                list?.add(item._id.toString())
                            }
                        }
                    }

                })
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

}