package com.example.ui.home

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.userapp.R
import com.smarteist.autoimageslider.SliderView

class GlanceU : AppCompatActivity() {

    private lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    lateinit var imageUrl: ArrayList<String>

    private lateinit var sliderView2: SliderView
    lateinit var sliderAdapter2: SliderAdapter2
    lateinit var imageUrl2: ArrayList<String>

    private lateinit var insta : ImageView
    private lateinit var facebook: ImageView
    private lateinit var twitter : ImageView
    private lateinit var youtube: ImageView

    private lateinit var vcprofile:Button
    private lateinit var vcmsg:Button
    private lateinit var chprofile:Button
    private lateinit var chmsg:Button

    private lateinit var about_text:TextView
    private lateinit var slate:ImageView

    private lateinit var map2: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glance_u)

        map2 = findViewById(R.id.image_map2)
        sliderView = findViewById(R.id.imageSlider)
        sliderView2 = findViewById(R.id.imageSlider2)

        vcprofile = findViewById(R.id.profile_btn_vc)
        vcmsg = findViewById(R.id.msg_btn_vc)

        chprofile = findViewById(R.id.profile_btn_ch)
        chmsg = findViewById(R.id.msg_btn_ch)

        insta = findViewById(R.id.insta)
        facebook = findViewById(R.id.facebook)
        twitter = findViewById(R.id.twitter)
        youtube = findViewById(R.id.youtube)

        about_text = findViewById(R.id.about)
        var isExpanded = false
        about_text.setOnClickListener {
            isExpanded = !isExpanded
            if (isExpanded) {
                about_text.maxLines = Integer.MAX_VALUE // Expand to show all lines
            } else {
                about_text.maxLines = 7 // Collapse to show a maximum of 2 lines
            }
        }

        vcprofile.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/article/en/vice-chancellor"
            openUrlInCustomTab(url)
        }
        vcmsg.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/post/vice-chancellor-message"
            openUrlInCustomTab(url)
        }
        chprofile.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/article/en/chancellor"
            openUrlInCustomTab(url)
        }
        chmsg.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/article/governor-message"
            openUrlInCustomTab(url)
        }
        insta.setOnClickListener {
            goToUrl("https://www.instagram.com/lucknow_university__lu/?hl=en","insta")
        }
        facebook.setOnClickListener {
            goToUrl("https://www.facebook.com/luadmission","fb")
        }
        twitter.setOnClickListener {
            goToUrl("https://twitter.com/lkouniv","x")
        }
        youtube.setOnClickListener {
            goToUrl("https://www.youtube.com/channel/UCtpNUglUAhCIlOc8gww5KuQ","utube")
        }
        setSlider()
        setSlider2()
        setMap()
    }

    private fun openUrlInCustomTab(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(resources.getColor(R.color.black)) // Customize the toolbar color
            .setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .setNavigationBarColor(resources.getColor(R.color.grey)) // Customize the navigation bar color
                    .setToolbarColor(resources.getColor(R.color.black))
                    .build()
            )
            .build()

        customTabsIntent.launchUrl(this, Uri.parse(url))
    }


    private fun goToUrl(url: String, app: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        when(app)
        {
            "insta"->{
                if (!url.isNullOrBlank()) {
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    if (isAppInstalled("com.instagram.android",this)) {
                        webIntent.setPackage("com.instagram.android")
                    }
                    startActivity(webIntent)
                }
            }
            "fb"->{
                val facebookAppUri = Uri.parse("fb://facewebmodal/f?href=$url")
                if (isAppInstalled("com.facebook.katana",this)) {
                    // Open the Facebook app
                    intent.data = facebookAppUri
                } else {
                    // If Facebook app is not installed, open Facebook in a web browser
                    intent.data = Uri.parse(url)
                }
                // Start the intent
                startActivity(intent)
            }
            "x"->{
                val twitterUrl = "https://twitter.com/lkouniv" // Replace with the Twitter URL you want to open
                val twitterAppUri = Uri.parse("twitter://user?screen_name=lkouniv") // Replace "lkouniv" with the Twitter username you want to open

// Check if the Twitter app is installed
                if (isAppInstalled("com.twitter.android",this)) {
                    // Open the Twitter app
                    intent.data = twitterAppUri
                } else {
                    // If Twitter app is not installed, open Twitter in a web browser
                    intent.data = Uri.parse(twitterUrl)
                }
                startActivity(intent)
            }
            "utube"->{
                val youtubeVideoUrl = "https://www.youtube.com/@lkouniv_official"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeVideoUrl))

// Check if the YouTube app is installed
                if (isAppInstalled("com.google.android.youtube", this)) {
                    // Open the YouTube app
                    intent.setPackage("com.google.android.youtube")
                }

// Start the intent
                startActivity(intent)
            }
        }
    }
    private fun isAppInstalled(s: String, context : Context): Boolean {
        return try {
//            val packageManager = requireActivity().packageManager
            val packageManager = context.packageManager
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
    private fun setMap() {
        map2.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setSlider() {
        imageUrl = ArrayList()
        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/gallery%2FIMG-20230917-WA0050.jpg?alt=media&token=4f6b2b9a-24bf-47ac-815a-c5c53f55675f") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/gallery%2FIMG-20230917-WA0045.jpg?alt=media&token=505b909d-c6c8-4579-9ab1-356e04fd6c27") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/gallery%2FIMG-20230917-WA0040.jpg?alt=media&token=c50389e0-f474-4b02-8dd2-47101b5fe3f7") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/gallery%2FIMG-20230917-WA0046.jpg?alt=media&token=0d4b0e10-d616-444c-8e08-85834024d5ec") as ArrayList<String>

        sliderAdapter = SliderAdapter(imageUrl)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }
    private fun setSlider2() {
        imageUrl2 = ArrayList()
        imageUrl2 =
            (imageUrl2 + "https://seeklogo.com/images/U/uttar-pradesh-government-logo-1FA161CB94-seeklogo.com.png") as ArrayList<String>
        imageUrl2 =
            (imageUrl2 + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/image_data%2Fecell_lu.jpeg?alt=media&token=202b0614-ca27-4fc9-94a5-de18d6c3387d") as ArrayList<String>
        imageUrl2 =
            (imageUrl2 +  "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/image_data%2Fslate_lu.png?alt=media&token=092b563f-8329-4ff1-8fb1-b383ff0d3557") as ArrayList<String>
        imageUrl2 =
            (imageUrl2 + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/image_data%2Ftpc.jpg?alt=media&token=0bb23017-a6de-4e89-9a41-e18e99a4b39e") as ArrayList<String>

        sliderAdapter2 = SliderAdapter2(imageUrl2)
        sliderView2.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView2.setSliderAdapter(sliderAdapter2)
        sliderView2.scrollTimeInSec = 3
        sliderView2.isAutoCycle = true
        sliderView2.startAutoCycle()
    }
}