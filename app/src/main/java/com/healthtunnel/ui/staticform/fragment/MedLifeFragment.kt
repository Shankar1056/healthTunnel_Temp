package com.healthtunnel.ui.staticform.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.healthtunnel.R
import com.healthtunnel.data.model.AvailableTests
import com.healthtunnel.databinding.FragmentMedlifeBinding
import com.healthtunnel.ui.staticform.AvailableTestSearch
import com.healthtunnel.ui.staticform.StaticFormViewModel
import com.healthtunnel.ui.staticform.adapter.ServiceCategoryAdapter
import kotlinx.android.synthetic.main.fragment_medlife.*

class MedLifeFragment : Fragment() {

    val viewModel: StaticFormViewModel by activityViewModels()
    private var testAdapter : ServiceCategoryAdapter ?= null
    private var selectedList = ArrayList<AvailableTests>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMedlifeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_medlife, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { viewModel.setContext(it) }
        termsConditionCB.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.termsCondition(
                isChecked
            )
        }

        viewModel.availableTestResponse.observe(viewLifecycleOwner, Observer {
            startActivityForResult(Intent(activity,
                AvailableTestSearch::class.java).putParcelableArrayListExtra(
                "list",
                it), 2)
        })

        viewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })

        serviceRV.layoutManager = GridLayoutManager(activity, 2)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            /*try {
                val message = data?.getStringArrayListExtra("MESSAGE") as ArrayList<String>
                if (message != null) {
                    viewModel.setlabcode.value = Array(message.size) { i -> message[i].toString() }
                    serviceList.addAll(message)
                    serviceRV.adapter = adapter
                }
            } catch (e : Exception){

            }
            val labname: ArrayList<String>? = data?.getStringArrayListExtra("LABNAME")*/
            selectedList = data?.getParcelableArrayListExtra("selectedList")!!

            setDatainAdapterAndViewodel()
            testAdapter = ServiceCategoryAdapter(selectedList, object :
                ServiceCategoryAdapter.OnItemClickListener {
                override fun onClick(pos: Int) {
                    selectedList.removeAt(pos)
                    setDatainAdapterAndViewodel()
                    testAdapter?.notifyDataSetChanged()
                }

            })

            serviceRV.adapter = testAdapter

        }
    }

    private fun setDatainAdapterAndViewodel() {
        viewModel.setlabcode.value =
            Array(selectedList.size) { i -> selectedList[i].code.toString() }
    }
}