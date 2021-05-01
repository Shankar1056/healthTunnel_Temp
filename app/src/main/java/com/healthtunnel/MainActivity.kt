package com.healthtunnel

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.androidisland.ezpermission.EzPermission
import com.google.android.material.navigation.NavigationView
import com.healthtunnel.ui.HomeCategoryListActivity
import com.healthtunnel.ui.SplashScreen
import com.healthtunnel.ui.blood.BloodDonationActivity
import com.healthtunnel.ui.blood.BloodRequestAcrivity
import com.healthtunnel.ui.caterorywithtab.ServiceCategoryActivity
import com.healthtunnel.ui.community_center.CommunityCenteractivity
import com.healthtunnel.ui.home.HomeViewModel
import com.healthtunnel.ui.location.LocationActivityNew
import com.healthtunnel.ui.profile.ProfileActivity
import com.healthtunnel.ui.static_content.PrivacyPolicyActivity
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.healthtunnel.ui.utility.LocationUtil
import com.healthtunnel.ui.wellnesscorner.WellnessCornerActivity
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var isGPSEnabled = false
    private var whatClicked: Int? =
        null //1. BloodDonate, 2. BloodRequest, 3. PillRemainder, 4. CommunityCenter
    val viewModel: HomeViewModel by viewModels()

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkGPSOnOff()
        getUpdatedLocation()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_popular_cat,
                R.id.nav_diab_mgnt,
                R.id.nav_order_medicine,
                R.id.nav_book_lab,
                R.id.nav_doctor_consultation,
                R.id.nav_fertility_clinic,
                R.id.nav_weight_loss,
                R.id.nav_rent_medical,
                R.id.nav_health_insurance,
                R.id.nav_yoga_wellness,
                R.id.nav_organic_food,
                R.id.nav_welnesscorner,
                R.id.nav_welnesscorner,
                R.id.nav_comunity_center,
                R.id.nav_privacy_policy,
                R.id.nav_terms_condition,
                R.id.nav_logout
            ), drawerLayout
        )


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewModel.performedClickOperation.observe(this, Observer {
            when (it) {
                HomeViewModel.HomeClickListener.DONATE_BLOOD -> {
                    whatClicked = 1
                    getStandAloneImage()

                }
                HomeViewModel.HomeClickListener.REQUEST_BLOOD -> {
                    whatClicked = 2
                    getStandAloneImage()
                }

                HomeViewModel.HomeClickListener.PILL_REMAINDER -> {
                    whatClicked = 3
                    getStandAloneImage()
                }

                HomeViewModel.HomeClickListener.COMMUNI_CENTER -> {
                    whatClicked = 4
                    getStandAloneImage()
                }
            }
        })

        viewModel.standAloneImageResult.observe(this, Observer {
            when (whatClicked) {
                1 ->
                    for (data in it) {
                        if (data.serviceCategoryNumber == 1) {
                            switchActivity(BloodDonationActivity::class.java, data.path.toString())
                        }
                    }
                2 ->
                    for (data in it) {
                        if (data.serviceCategoryNumber == 2) {
                            switchActivity(BloodRequestAcrivity::class.java, data.path.toString())
                        }
                    }

                3 -> {
                    /*for (data in it) {
                        if (data.serviceCategoryNumber == 3) {
                            startActivity(
                                Intent(this@MainActivity, RemainderListActivity::class.java)
                                    .putExtra("user_id", ClsGeneral.getPreferences(Constant.USERID))
                                    .putExtra("explanatory_image", data.path.toString())
                            )
                        }
                    }*/
                }

                4 -> {
                    for (data in it) {
                        if (data.serviceCategoryNumber == 4) {
                            startActivity(
                                Intent(this@MainActivity, CommunityCenteractivity::class.java)
                                    .putExtra("explanatory_image", data.path.toString())
                            )
                        }
                    }

                }
            }
        })

        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_menu)

        val headerView: View = navView!!.getHeaderView(0)
        val name: TextView = headerView.findViewById(R.id.name)
        val profile: TextView = headerView.findViewById(R.id.profile)
        name.setText(ClsGeneral.getPreferences(Constant.NAME))

        profile.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
        }

        navView!!.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_popular_cat -> {
                    startActivity(Intent(this, HomeCategoryListActivity::class.java))
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_diab_mgnt -> {
                    goToServiceCat(Constant.DIABETIC_ID)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_order_medicine -> {
                    goToServiceCat(Constant.ORDER_MEDICINE)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_book_lab -> {
                    goToServiceCat(Constant.BOOK_LAB_TESTES)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_doctor_consultation -> {
                    goToServiceCat(Constant.DOCTOR_CONSULTATION)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_fertility_clinic -> {
                    goToServiceCat(Constant.FERTILITY_CLINIC)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_weight_loss -> {
                    goToServiceCat(Constant.WEIGHTLOSS_FITNESS)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_rent_medical -> {
                    goToServiceCat(Constant.RENT_MEDICAL_EQUIPMENT)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_health_insurance -> {
                    goToServiceCat(Constant.BUY_HEALTH_INSURANCE)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_yoga_wellness -> {
                    goToServiceCat(Constant.YOGA_WELLNESS)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_organic_food -> {
                    goToServiceCat(Constant.ORGANIC_FOOD)
                    return@setNavigationItemSelectedListener true
                }

                R.id.nav_welnesscorner -> {
                    startActivity(
                        Intent(
                            this,
                            WellnessCornerActivity::class.java
                        ).putExtra("pos", 0).putExtra("data", viewModel.wellnessResult)
                    )
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_comunity_center -> {
                    startActivity(Intent(this, CommunityCenteractivity::class.java))
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_privacy_policy -> {
                    startActivity(
                        Intent(this, PrivacyPolicyActivity::class.java).putExtra(
                            "title",
                            resources.getString(R.string.menu_privacy_policy)
                        )
                    )
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_terms_condition -> {
                    startActivity(
                        Intent(this, PrivacyPolicyActivity::class.java).putExtra(
                            "title",
                            resources.getString(R.string.menu_terms_condition)
                        )
                    )
                    return@setNavigationItemSelectedListener true
                }

                R.id.nav_share -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Hi, Checkout All-In-One Mobile Health App called HealthTunnel. Download From Play Store \n https://healthtunnel.com/"
                    )
                    startActivity(Intent.createChooser(shareIntent, "\n Health Tunnel \n "))
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_logout -> {
                    ClsGeneral.setPreferences(Constant.TOKEN, "")
                    ClsGeneral.setPreferences(Constant.NAME, "")
                    ClsGeneral.setPreferences(Constant.EMAIL, "")
                    ClsGeneral.setPreferences(Constant.GENDER, "")
                    ClsGeneral.setPreferences(Constant.USERID, "")
                    startActivity(Intent(this@MainActivity, SplashScreen::class.java))
                    finishAffinity()
                    return@setNavigationItemSelectedListener true
                }

                else -> {
                    return@setNavigationItemSelectedListener false
                }

            }
        }

        locationCL.setOnClickListener {
            startActivity(Intent(this@MainActivity, LocationActivityNew::class.java))
        }

        //Get User TueCaller Profile
//        TruecallerSDK.getInstance().getUserProfile(this@MainActivity)
    }

    private fun getStandAloneImage() {
        viewModel.getStandAloneImage()
    }

    private fun goToServiceCat(diabeticId: String) {
        if (diabeticId != null) {
            startActivity(
                Intent(
                    this@MainActivity,
                    ServiceCategoryActivity::class.java
                ).putExtra("parentId", diabeticId)
            )
        }
    }


    private fun switchActivity(activity: Class<*>, standAloneImage: String) {
        startActivity(
            Intent(this@MainActivity, activity)
                .putExtra("from", "home")
                .putExtra("explanatory_image", standAloneImage)
        )
    }

    private fun getUpdatedLocation() {
        /**
         * Observe LocationViewModel LiveData to get updated location
         */
        viewModel.getLocationData.observe(this, Observer {
            if (!isNewLocationClickedd) {
                ClsGeneral.setPreferences(Constant.LATITUTE, it.latitude.toString())
                ClsGeneral.setPreferences(Constant.LONGITUTE, it.longitude.toString())
                viewModel.getAddressFromLatLon(
                    ClsGeneral.getPreferences(Constant.LATITUTE)
                        .toDouble(),
                    ClsGeneral.getPreferences(Constant.LONGITUTE).toDouble()
                )
            }
//                location.text = it.longitude.toString() +", "+it.latitude.toString()
        })

        viewModel.address.observe(this, Observer {
            location.text = it
            ClsGeneral.setPreferences(Constant.LOCATION, it)
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun checkGPSOnOff() {
        //Check weather Location/GPS is ON or OFF
        LocationUtil(this).turnGPSOn(
            object :
                LocationUtil.OnLocationOnListener {

                override fun locationStatus(isLocationOn: Boolean) {
                    this@MainActivity.isGPSEnabled = isLocationOn
                }
            })
    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        when {
            !isGPSEnabled -> {
//                info.text = getString(R.string.enable_gps)
            }

            isLocationPermissionsGranted() -> {
                getUpdatedLocation()
            }
            else -> {
                askLocationPermission()
            }
        }
    }

    private fun isLocationPermissionsGranted(): Boolean {
        return (EzPermission.isGranted(this, locationPermissions[0])
                && EzPermission.isGranted(this, locationPermissions[1]))
    }

    private fun askLocationPermission() {
        EzPermission
            .with(this)
            .permissions(locationPermissions[0], locationPermissions[1])
            .request { granted, denied, permanentlyDenied ->
                if (granted.contains(locationPermissions[0]) &&
                    granted.contains(locationPermissions[1])
                ) { // Granted
                    startLocationUpdates()

                } else if (denied.contains(locationPermissions[0]) ||
                    denied.contains(locationPermissions[1])
                ) { // Denied

                    showDeniedDialog()

                } else if (permanentlyDenied.contains(locationPermissions[0]) ||
                    permanentlyDenied.contains(locationPermissions[1])
                ) { // Permanently denied
                    showPermanentlyDeniedDialog()
                }

            }
    }

    /**
     *
     */
    private fun showPermanentlyDeniedDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.title_permission_permanently_denied))
        dialog.setMessage(getString(R.string.message_permission_permanently_denied))
        dialog.setNegativeButton(getString(R.string.not_now)) { _, _ -> }
        dialog.setPositiveButton(getString(R.string.settings)) { _, _ ->
            startActivity(
                EzPermission.appDetailSettingsIntent(
                    this
                )
            )
        }
        dialog.setOnCancelListener { } //important
        dialog.show()
    }


    /**
     *
     */
    private fun showDeniedDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.title_permission_denied))
        dialog.setMessage(getString(R.string.message_permission_denied))
        dialog.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        dialog.setPositiveButton(getString(R.string.allow)) { _, _ ->
            askLocationPermission()
        }
        dialog.setOnCancelListener { } //important
        dialog.show()
    }

    /**
     * On Activity Result for locations permissions updates
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.LOCATION_PERMISSION_REQUEST) {
                isGPSEnabled = true
                startLocationUpdates()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            viewModel.getAddressFromLatLon(
                ClsGeneral.getPreferences(Constant.LATITUTE).toDouble(),
                ClsGeneral.getPreferences(Constant.LONGITUTE).toDouble()
            )
        } catch (e: Exception) {

        }
    }

    companion object {
        var isNewLocationClickedd: Boolean = false
    }
}