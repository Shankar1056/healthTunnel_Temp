package com.healthtunnel.ui.donatemoney

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import com.healthtunnel.R
import kotlinx.android.synthetic.main.image_item.view.*

class DonateMoneyPagerAdapter(
    val activity: FragmentActivity?,
    val media: ArrayList<Int>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(activity)

        val view = inflater.inflate(
            R.layout.image_item_donate_money,
            container, false
        ) as ViewGroup

        view.imageView.setBackgroundResource(media[position])

        container.addView(view)
        return view
    }

    override fun getCount(): Int = media.size

    override fun getItemPosition(`object`: Any): Int = super.getItemPosition(`object`)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
