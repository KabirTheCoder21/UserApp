package com.example.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.example.userapp.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.smarteist.autoimageslider.SliderView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView

class HomeFragment : Fragment() {

    private lateinit var nestedScrollView: NestedScrollView

    private lateinit var insta : ImageView
    private lateinit var facebook: ImageView
    private lateinit var twitter : ImageView
    private lateinit var youtube: ImageView
    private lateinit var Glance: LinearLayout

    private lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    lateinit var imageUrl: ArrayList<String>

    private lateinit var sliderView2: SliderView
    lateinit var sliderAdapter2: SliderAdapter2
    lateinit var imageUrl2: ArrayList<String>
    private lateinit var map: ImageView

    lateinit var gridView: GridView

    private lateinit var styledPV : StyledPlayerView
    private lateinit var exoPlayer : ExoPlayer

    private lateinit var old_camp:LinearLayout
    private lateinit var new_camp:LinearLayout

    private lateinit var bottomSheetDialog : BottomSheetDialog
    private lateinit var campuses : LinearLayout
    private lateinit var contact_dir : CardView
    private lateinit var admission : CardView
    private lateinit var events : CardView
    private lateinit var news : CardView
    private lateinit var academics : CardView
    private lateinit var exam : CardView
    private lateinit var result : CardView
    private lateinit var support : CardView
    private lateinit var activity : CardView
    private lateinit var materials : CardView
    private lateinit var udrc : CardView
    private lateinit var fees : CardView
    private lateinit var schoral : CardView
//    private lateinit var bottomSheetView:BottomSheetDialog
    private var isPlay = true
    private var manuallyPaused = false // Added manuallyPaused flag
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        sliderView = view.findViewById(R.id.imageSlider)
        sliderView2 = view.findViewById(R.id.imageSlider2)
       // gridView = view.findViewById(R.id.grid_view)
        map = view.findViewById(R.id.image_map)
        contact_dir = view.findViewById(R.id.contect_directory)
        admission = view.findViewById(R.id.admission_home)
        events = view.findViewById(R.id.events_home)
        news = view.findViewById(R.id.news_home)
        academics = view.findViewById(R.id.Academics)
        exam = view.findViewById(R.id.exams)
        result = view.findViewById(R.id.results)
        support = view.findViewById(R.id.support)
        activity = view.findViewById(R.id.activity)
        materials = view.findViewById(R.id.material)
        udrc = view.findViewById(R.id.udrc)
        fees = view.findViewById(R.id.fees)
        schoral = view.findViewById(R.id.scholarship)

        styledPV = view.findViewById(R.id.styledPV)
        nestedScrollView = view.findViewById(R.id.nestedScrollView)

        insta = view.findViewById(R.id.insta)
        facebook = view.findViewById(R.id.facebook)
        twitter = view.findViewById(R.id.twitter)
        youtube = view.findViewById(R.id.youtube)

        bottomSheetDialog = BottomSheetDialog(requireContext())


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

        Glance = view.findViewById(R.id.univ_at_glance)
        Glance.setOnClickListener {
            val intent = Intent(requireContext(), GlanceU::class.java)
            startActivity(intent)

        }

        campuses = view.findViewById(R.id.campuses)
        campuses.setOnClickListener {
            showBottomSheet()
        }
        udrc.setOnClickListener {
            val url = "https://udrc.lkouniv.ac.in/student?cd=MQAzADYAOQA2AA%3D%3D"
            openUrlInCustomTab(url)
        }
        fees.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/page/online-fee-submission"
            openUrlInCustomTab(url)
        }
        schoral.setOnClickListener {
            val url = "https://scholarship.up.gov.in/"
            openUrlInCustomTab(url)
        }
        materials.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/page/e-content"
            openUrlInCustomTab(url)
        }
        activity.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/news?Newslistslug=en-events&cd=MQAzADcAMgA5AA%3D%3D"
            openUrlInCustomTab(url)
        }
        support.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/article/en/student-support"
            openUrlInCustomTab(url)
        }
        result.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/page/semester-result"
            openUrlInCustomTab(url)
        }
        exam.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/news?Newslistslug=en-examination-schedule&cd=OAA1AA%3D%3D"
            openUrlInCustomTab(url)
        }
        events.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/news?Newslistslug=en-events&cd=MQAzADcAMgA5AA%3D%3D"
            openUrlInCustomTab(url)
        }
        academics.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data =
//                Uri.parse("https://www.lkouniv.ac.in/en/page/academic-calendar")
//            startActivity(intent)
            val url = "https://www.lkouniv.ac.in/en/page/academic-calendar"
            openUrlInCustomTab(url)
        }
        contact_dir.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data =
//                Uri.parse("https://www.lkouniv.ac.in/article/en/contact-dir")
//            startActivity(intent)
            val url = "https://www.lkouniv.ac.in/article/en/contact-dir "
            openUrlInCustomTab(url)
        }
        admission.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/page/at-a-glance"
            openUrlInCustomTab(url)
        }
        news.setOnClickListener {
            val url = "https://www.lkouniv.ac.in/en/news?Newslistslug=en-student-news&cd=MQAzADcAMgA4AA%3D%3D"
            openUrlInCustomTab(url)
        }
        setSlider()
        setVideoPlayer()
       // setGridView()
        setMap()
        setSlider2()

        return view
    }


    fun openUrlInCustomTab(url: String) {
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

        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun showBottomSheet() {

        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()

        val buttonInsideBottomSheet = dialogView.findViewById<LinearLayout>(R.id.old_campus)
        buttonInsideBottomSheet.setOnClickListener {
            // Your click action code here
            // For example, show a Toast message
//            Toast.makeText(requireContext(), "Clicked On old Camp", Toast.LENGTH_SHORT).show()
            val url = "https://www.lkouniv.ac.in/"
            openUrlInCustomTab(url)
        }

        val buttonInsideBottomSheet2 = dialogView.findViewById<LinearLayout>(R.id.new_campus)
        buttonInsideBottomSheet2.setOnClickListener {
            // Your click action code here
            // For example, show a Toast message
//            Toast.makeText(requireContext(), "Clicked On new Camp", Toast.LENGTH_SHORT).show()
            val url = "https://www.lkouniv.ac.in/en/page/faculty-of-engineering-pages"
            openUrlInCustomTab(url)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun goToUrl(url: String, app: String) {
        val intent = Intent(Intent.ACTION_VIEW)
           when(app)
           {
               "insta"->{
                   if (!url.isNullOrBlank()) {
                       val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                       if (isAppInstalled("com.instagram.android")) {
                           webIntent.setPackage("com.instagram.android")
                       }
                       startActivity(webIntent)
                   }
               }
               "fb"->{
                   val facebookAppUri = Uri.parse("fb://facewebmodal/f?href=$url")
                   if (isAppInstalled("com.facebook.katana")) {
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
                   if (isAppInstalled("com.twitter.android")) {
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
                   if (isAppInstalled("com.google.android.youtube")) {
                       // Open the YouTube app
                       intent.setPackage("com.google.android.youtube")
                   }

// Start the intent
                   startActivity(intent)
               }
           }
    }

    private fun isAppInstalled(s: String): Boolean {
        return try {
            val packageManager = requireActivity().packageManager
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun setVideoPlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        styledPV.player = exoPlayer
        // Set your desired aspect ratio here


        val resourceId = R.raw.intro // Replace with your actual resource ID
        val uri = Uri.parse("rawresource:///${resourceId}") // Construct the URI
        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        // To manually pause the video
        styledPV.setOnClickListener {
            if(isPlay)
            {
                exoPlayer.playWhenReady = false
                manuallyPaused = true
                isPlay = false
            }
           else{
                exoPlayer.playWhenReady = true
                isPlay = true
                manuallyPaused = false
            }
        }
        nestedScrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            // Check the visibility of the StyledPlayerView
            val playerVisible = isViewVisible(styledPV)

            if (playerVisible) {
                if (manuallyPaused) {
                    // If the video was manually paused, don't auto-play it
                    return@setOnScrollChangeListener
                }
                else {
                    // Play the ExoPlayer only if it's not already playing.
                    exoPlayer.playWhenReady = true
                    manuallyPaused = false
                }
            } else {
                exoPlayer.playWhenReady = false
            }
        }

        /* nestedScrollView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
             override fun onGlobalLayout() {
                isViewVisible(styledPV)
             }
         })*/
    }

    private fun isViewVisible(styledPV: StyledPlayerView): Boolean {
        val rect = Rect()
        styledPV.getGlobalVisibleRect(rect)
        val screenHeight = nestedScrollView.height
        return rect.top <= screenHeight && rect.bottom >= 0
    }

    private fun setMap() {
        map.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }
    }

    /*private fun setGridView() {

        val itemName = arrayOf(
            "Admission",
            "Events",
            "News",
            "Academic Calendar",
            "Examination",
            "Result",
            "Student Suport",
            "Student Activities",
            "Online Study",
            "UDRC Login",
            "Fee Submission",
            "Scholarship"
        )
        val image = intArrayOf(
            R.drawable.admission, R.drawable.events, R.drawable.news,
            R.drawable.calendar, R.drawable.examination, R.drawable.result,
            R.drawable.student_support, R.drawable.student_activity, R.drawable.online_education,
            R.drawable.udrc_login, R.drawable.fee_submission, R.drawable.scholarship
        )
        val gridAdapter = GridAdapter(requireContext(), itemName, image)
        gridView.adapter = gridAdapter
    }*/

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

    override fun onResume() {
        super.onResume()
        exoPlayer.playWhenReady = true
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.playWhenReady = false
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
    override fun onStart() {
        super.onStart()

            exoPlayer.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
        exoPlayer.release()
    }
}