package com.healthtunnel.ui.ecom.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.healthtunnel.data.model.CatResult
import com.healthtunnel.data.model.WellnessResult
import com.healthtunnel.ui.caterorywithtab.fragment.ServiceListFragment
import com.healthtunnel.ui.wellnesscorner.WellnessListFragment
import java.util.ArrayList

class EcomViewPagerAdapter (
    manager: FragmentManager
) : FragmentPagerAdapter(manager) {

    private val fragments: MutableList<Fragment> = ArrayList()
    private val titleLists = ArrayList<String>()
    private val idLists = ArrayList<String>()


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleLists[position]
    }

    fun getId(position: Int): String {
        return idLists[position]
    }

    fun initTabsAndPages(result: ArrayList<CatResult>) {

        if (result != null && result.isNotEmpty()) {
            for (i in result.indices) {
                titleLists.add(result[i].name)
                idLists.add(result[i]._id)
                fragments.add(ServiceListFragment())
            }
        }
    }

    fun initWellnessTabsAndPages(result: ArrayList<WellnessResult>) {

        if (result != null && result.isNotEmpty()) {
            for (i in result.indices) {
                titleLists.add(result[i].name)
                idLists.add(result[i]._id)
                fragments.add(WellnessListFragment())
            }
        }
    }
}
