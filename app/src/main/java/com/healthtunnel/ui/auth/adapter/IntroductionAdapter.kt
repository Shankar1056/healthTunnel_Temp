package com.healthtunnel.ui.auth.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import com.healthtunnel.data.model.InteroductionModel
import com.healthtunnel.R
import kotlinx.android.synthetic.main.image_item.view.*

class IntroductionAdapter (
    val activity: FragmentActivity?,
    val media: ArrayList<InteroductionModel>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(activity)

        val view = inflater.inflate(
            R.layout.image_item,
            container, false
        ) as ViewGroup

        view.imageView.setBackgroundResource(media[position].image)
        view.title.setText(media[position].header)
        view.desc.setText(media[position].desc)
        container.addView(view)
        return view
    }

    override fun getCount(): Int = media.size

    override fun getItemPosition(`object`: Any): Int = super.getItemPosition(`object`)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
