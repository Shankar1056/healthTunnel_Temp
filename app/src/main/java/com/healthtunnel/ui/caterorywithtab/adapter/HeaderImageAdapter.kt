package com.healthtunnel.ui.caterorywithtab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import com.healthtunnel.R
import com.healthtunnel.data.model.DataResult
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_item.view.*

class HeaderImageAdapter(
    val activity: FragmentActivity?,
    val media: ArrayList<DataResult>,
    val listener: onItemClicked
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(activity)

        val view = inflater.inflate(
            R.layout.image_item_feature,
            container, false
        ) as ViewGroup

        if (activity != null) {
            if (media[position].bannerImage.isNullOrEmpty()) {
                view.imageView.setBackgroundResource(R.drawable.default_banner)
            } else {
                Glide.with(activity).load(media[position].bannerImage).into(view.imageView)
            }
        }

        view.imageView.setOnClickListener {
            listener.onClicked(position)
        }


        container.addView(view)
        return view
    }

    override fun getCount(): Int = media.size

    override fun getItemPosition(`object`: Any): Int = super.getItemPosition(`object`)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    interface onItemClicked {
        fun onClicked(pos: Int)
    }
}
