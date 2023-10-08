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
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/profile%2Flu1.jpg?alt=media&token=7cd5f421-4718-4620-8b6f-bba7383429b6") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/profile%2Flu3.jpg?alt=media&token=22d7ca42-b079-4b5d-af8f-88cb5a6223d7") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/profile%2Flu2.jpg?alt=media&token=b6c22300-611c-45cf-b1c2-276a9517d0a5") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/profile%2Flu4.jpg?alt=media&token=3b1b8d5e-0d9e-4b49-99c0-83b52f97bbcc") as ArrayList<String>

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
            (imageUrl2 + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/profile%2Fecell.jpg?alt=media&token=0b24ea64-86f2-448f-a361-680b5efcfc88") as ArrayList<String>
        imageUrl2 =
            (imageUrl2 +  "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/profile%2Fslate.png?alt=media&token=06d72f99-53fb-4cbe-8bc5-35a12e787851") as ArrayList<String>
        imageUrl2 =
            (imageUrl2 + "https://firebasestorage.googleapis.com/v0/b/my-college-app-1b5cd.appspot.com/o/profile%2Ftpc.png?alt=media&token=5c81c587-8696-4816-b4e3-e9829d0628ab") as ArrayList<String>

        sliderAdapter2 = SliderAdapter2(imageUrl2)
        sliderView2.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView2.setSliderAdapter(sliderAdapter2)
        sliderView2.scrollTimeInSec = 3
        sliderView2.isAutoCycle = true
        sliderView2.startAutoCycle()
    }
}