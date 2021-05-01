package com.healthtunnel.ui.caterorywithtab.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.healthtunnel.data.model.CatResult
import com.healthtunnel.data.model.WellnessResult
import com.healthtunnel.ui.caterorywithtab.fragment.ServiceListFragment
import com.healthtunnel.ui.wellnesscorner.WellnessListFragment
import java.util.*

class ServicesViewPagerAdapter(
    manager: FragmentManager
) : FragmentPagerAdapter(manager) {

    private val fragments: MutableList<Fragment> = ArrayList()
    private val titleLists = ArrayList<String>()
    private val idLists = ArrayList<String>()
    private val header = ArrayList<String>()
    private val explanatoryImage = ArrayList<String>()



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

    fun getHeaders(position: Int): String {
        if (header.size >= position && header[position] != null) {
            return header[position]
        } else {
            return ""
        }
    }

    fun getExplanatoryImage(position: Int): String {
        if (explanatoryImage.size >= position && explanatoryImage[position] != null) {
            return explanatoryImage[position]
        } else {
            return ""
        }
    }

    fun initTabsAndPages(result: ArrayList<CatResult>) {

        if (result != null && result.isNotEmpty()) {
            for (i in result.indices) {
                titleLists.add(result[i].name)
                idLists.add(result[i]._id)
                header.add(result[i].description)
                explanatoryImage.add(result[i].explanatory_image)
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