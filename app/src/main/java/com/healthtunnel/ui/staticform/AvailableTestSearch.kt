package com.healthtunnel.ui.staticform

import android.R.id.message
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.healthtunnel.R
import com.healthtunnel.data.model.AvailableTests
import com.healthtunnel.ui.staticform.fragment.AvailableSearchAdapter
import kotlinx.android.synthetic.main.activity_activity_available_search.*
import java.util.*
import kotlin.collections.ArrayList


class AvailableTestSearch : AppCompatActivity() {
    private var selectedList = ArrayList<AvailableTests>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_available_search)

        val list : ArrayList<AvailableTests> = intent.getParcelableArrayListExtra("list")

        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            var selectedIds = ArrayList<String>()
            var selectedName = ArrayList<String>()


            val intent = Intent()
            intent.putExtra("MESSAGE", selectedIds )
            intent.putExtra("LABNAME", selectedName )
            setResult(2, intent)
            finish()
        }

        search.adapter = AvailableSearchAdapter(list, object :
            AvailableSearchAdapter.OnItemClickListener {
            override fun onClick(pos: Int) {

            }

        })

        tickClicked.setOnClickListener {
            var selectedIds = ArrayList<String>()
            var selectedName = ArrayList<String>()
            for (data in list){
                if (data.isSelected){
                  /* selectedIds.add(data.name.toString())
                    selectedName.add(data.lab_name.toString())*/
                    selectedList.add(data)
                }
            }

            val intent = Intent()
            /*intent.putExtra("MESSAGE", selectedIds )
            intent.putExtra("LABNAME", selectedName )*/
            intent.putExtra("selectedList", selectedList)
            setResult(2, intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var selectedIds = ArrayList<String>()
        var selectedName = ArrayList<String>()


        val intent = Intent()
        intent.putExtra("MESSAGE", selectedIds )
        intent.putExtra("LABNAME", selectedName )
        setResult(2, intent)
        finish()
    }
}