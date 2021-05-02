package com.healthtunnel.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthtunnel.MainActivity
import com.healthtunnel.R
import com.healthtunnel.data.model.CatResult
import com.healthtunnel.databinding.FragmentHomeBinding
import com.healthtunnel.ui.caterorywithtab.ServiceCategoryActivity
import com.healthtunnel.ui.donatemoney.DOnateMoneyActivity
import com.healthtunnel.ui.ecom.BusinessSalesListActivity
import com.healthtunnel.ui.fcm.MyFirebaseMessagingService
import com.healthtunnel.ui.fcm.NotificationModel
import com.healthtunnel.ui.home.adapter.HomeCategoryAdapter
import com.healthtunnel.ui.home.adapter.HomeCategoryAdapterHor
import com.healthtunnel.ui.home.adapter.TOpBrandAdapter
import com.healthtunnel.ui.home.adapter.WellnessCornerAdapter
import com.healthtunnel.ui.utility.Constant
import com.healthtunnel.ui.webview.WebViewActivity
import com.healthtunnel.ui.wellnesscorner.WellnessCornerActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.image_item.*

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var diabeticId: String
    private var diabeticExImage: String = ""
    private lateinit var amblanceId: String
    private var amblanceExImage: String = ""
    private lateinit var medicalLoadId: String
    private var medicalLoadExImage: String = ""
    private lateinit var medicalSecondOpinionId: String
    private var medicalSecondOpinionExImage: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleDeepLinking()
        viewModel.getPopularCat()
        viewModel.getWellnessArticle()
        viewModel.getBanners(1)

        viewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.homeCategoryResponse.observe(viewLifecycleOwner, Observer {
            /*for (data in ArrayList<CatResult>(it)) {
                if (data.name == resources.getString(R.string.rent_medicalequipment)) {
                    it.remove(data)
                }
            }*/

            for (data in ArrayList<CatResult>(it)) {
                /*if (data.name == resources.getString(R.string.title_diabetes_mgmt)) {
                    diabeticId = data._id
                    diabeticExImage = data.explanatory_image
                    Constant.DIABETIC_ID = data._id
                    it.remove(data)
                }*/
                if (data.name == resources.getString(R.string.title_ambulance_nearby)) {
                    amblanceId = data._id
                    amblanceExImage = data.explanatory_image
                    Constant.AMBULANCE_ID = data._id
                    it.remove(data)
                }

                /*if (data.name == resources.getString(R.string.title_medical_loan)) {
                    medicalLoadId = data._id
                    medicalLoadExImage = data.explanatory_image
                    Constant.MEDICLLOAN_ID = data._id
                    it.remove(data)
                }*/

                /* if (data.name == resources.getString(R.string.title_medical_second_opinion)) {
                     medicalSecondOpinionId = data._id
                     medicalSecondOpinionExImage = data.explanatory_image
                     Constant.MEDICLSECONDOPINOIN_ID = data._id
                     it.remove(data)
                 }*/
            }
            val first6 = ArrayList<CatResult>()
            val last6 = ArrayList<CatResult>()
            if (it.size > 5) {
                for (i in 0 until 6) {
                    first6.add(it[i])
                }
            } else {
                first6.addAll(it)
            }

            if (it.size > 6) {
                for (i in 6 until it.size) {
                    last6.add(it[i])
                }
            }


            popularCatRvHor.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            popularCatRvHor2.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            popularCatRv.adapter = HomeCategoryAdapter(first6, activity, object :
                HomeCategoryAdapter.OnItemClickListener {
                override fun onClick(pos: Int) {
                    checkAndSwitchActivity(
                        first6[pos].workFlowType,
                        first6[pos]._id,
                        first6[pos].explanatory_image
                    )
                }
            })

            popularCatRvHor.adapter = HomeCategoryAdapterHor(first6, activity, object :
                HomeCategoryAdapterHor.OnItemClickListener {
                override fun onClick(pos: Int) {
                    checkAndSwitchActivity(
                        first6[pos].workFlowType,
                        first6[pos]._id,
                        first6[pos].explanatory_image
                    )
                }
            })

            popularCatRv2.adapter = HomeCategoryAdapter(last6, activity, object :
                HomeCategoryAdapter.OnItemClickListener {
                override fun onClick(pos: Int) {
                    checkAndSwitchActivity(
                        last6[pos].workFlowType,
                        last6[pos]._id,
                        last6[pos].explanatory_image
                    )
                }
            })

            popularCatRvHor2.adapter = HomeCategoryAdapterHor(last6, activity, object :
                HomeCategoryAdapterHor.OnItemClickListener {
                override fun onClick(pos: Int) {
                    checkAndSwitchActivity(
                        last6[pos].workFlowType,
                        last6[pos]._id,
                        last6[pos].explanatory_image
                    )
                }
            })


        })




        viewModel.wellnessCornerResponse.observe(viewLifecycleOwner, Observer {
            viewModel.wellnessResult = it
            wellnessRv.adapter = WellnessCornerAdapter(it, activity, object :
                WellnessCornerAdapter.OnItemClickListener {
                override fun onClick(pos: Int) {
                    startActivity(
                        Intent(
                            activity,
                            WellnessCornerActivity::class.java
                        ).putExtra("pos", pos).putExtra("data", it)
                    )
                }


            })
        })

        viewModel.featureProductResponse.observe(viewLifecycleOwner, Observer {
            /*featureViewPager.adapter = FeaturedProductAdapter(activity, it, object :
                FeaturedProductAdapter.OnItemClicked {
                override fun onclick(pos: Int) {
                    startActivity(Intent(activity, WebViewActivity::class.java).
                    putExtra("id", "").
                    putExtra("url", it[pos].hyperLink).
                    putExtra("title", resources.getString(R.string.title_feature_products))
                    )
                }

            })
            dotsIndicator.setViewPager(featureViewPager)
            featureViewPager.adapter?.registerDataSetObserver(dotsIndicator.dataSetObserver)*/

            topBrandRv.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            topBrandRv.adapter = TOpBrandAdapter(it, activity, object :
                TOpBrandAdapter.OnItemClickListener {

                override fun onClick(pos: Int) {
                    startActivity(
                        Intent(activity, WebViewActivity::class.java).putExtra("id", "")
                            .putExtra("url", it[pos].hyperLink)
                            .putExtra("title", resources.getString(R.string.title_top_brands))
                            .putExtra("explanatory_image", "")
                    )
                }

                override fun onCallClick(pos: Int) {
                    TODO("Not yet implemented")
                }

            })
        })

        coronaButton.setOnClickListener {
            startActivity(
                Intent(activity, WebViewActivity::class.java).putExtra(
                    "url",
                    "https://www.covid19india.org/"
                )
                    .putExtra("title", resources.getString(R.string.action_corona_text))
                    .putExtra("explanatory_image", "")
            )
        }

        emergencyMedicalLoan.setOnClickListener {
            if (medicalLoadId != null) {
                startActivity(
                    Intent(
                        activity,
                        ServiceCategoryActivity::class.java
                    )
                        .putExtra("parentId", medicalLoadId)
                        .putExtra("explanatory_image", medicalLoadExImage)
                )
            }
        }

        populatText.setOnClickListener {
            //startActivity(Intent(activity, HomeCategoryListActivity::class.java))
            if (medicalSecondOpinionId != null) {
                startActivity(
                    Intent(
                        activity,
                        ServiceCategoryActivity::class.java
                    )
                        .putExtra("parentId", medicalSecondOpinionId)
                        .putExtra("explanatory_image", medicalSecondOpinionExImage)
                )
            }
        }

        donatemoney.setOnClickListener {
            startActivity(Intent(activity, DOnateMoneyActivity::class.java))
        }

        ambulance.setOnClickListener {
            if (amblanceId != null) {
                startActivity(
                    Intent(
                        activity,
                        ServiceCategoryActivity::class.java
                    )
                        .putExtra("parentId", amblanceId)
                        .putExtra("explanatory_image", amblanceExImage)
                )
            }
        }

        diab_mgmt.setOnClickListener {
            if (diabeticId != null) {
                startActivity(
                    Intent(
                        activity,
                        ServiceCategoryActivity::class.java
                    )
                        .putExtra("parentId", diabeticId)
                        .putExtra("explanatory_image", diabeticExImage)
                )
            }
        }

    }

    private fun checkAndSwitchActivity(
        workFlowType: String,
        _id: String,
        explanatoryImage: String
    ) {
        val intent: Intent = if (workFlowType == resources.getString(R.string.sales_work_flow_type)) {
            Intent(activity, BusinessSalesListActivity::class.java)
        } else {
            Intent(activity, ServiceCategoryActivity::class.java)
        }

        val model = (activity as MainActivity).notificationModel
        val isFromNotification = ((model != null) && (model.isNotification!!))
        if (isFromNotification) {
            intent.putExtra("parentId", model?.categoryId)
            intent.putExtra("explanatory_image", model?.explanatoryImageUrl)
            intent.putExtra(MyFirebaseMessagingService.NOTIFICATION_MODEL, model)
            (activity as MainActivity).notificationModel = null
        } else {
            intent.putExtra("parentId", _id)
            intent.putExtra("explanatory_image", explanatoryImage)
        }
        startActivity(intent)
    }
    private fun handleDeepLinking() {
        val model = (activity as MainActivity).notificationModel
        if ((model != null) && (model.isNotification!!)) {
            Log.d("HomeFragment", model.toString())
            checkAndSwitchActivity("TBD", model.categoryId.toString(), model.explanatoryImageUrl.toString())
        }
    }
}