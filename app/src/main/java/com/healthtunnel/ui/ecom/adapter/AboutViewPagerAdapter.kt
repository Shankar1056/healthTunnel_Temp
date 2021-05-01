package com.healthtunnel.ui.ecom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import com.healthtunnel.R
import com.healthtunnel.data.model.BusinessAboutDescImages
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_item.view.*

class AboutViewPagerAdapter(
    val activity: FragmentActivity?,
    val media: ArrayList<BusinessAboutDescImages>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(activity)

        val view = inflater.inflate(
            R.layout.about_image_item,
            container, false
        ) as ViewGroup

        if (activity != null) {
            Glide.with(activity).load(media[position].path).into(view.imageView)
        }
        container.addView(view)
        return view
    }

    override fun getCount(): Int = media.size

    override fun getItemPosition(`object`: Any): Int = super.getItemPosition(`object`)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
