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
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import com.example.userapp.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {

    private lateinit var nestedScrollView: NestedScrollView

    private lateinit var insta : ImageView
    private lateinit var facebook: ImageView
    private lateinit var twitter : ImageView
    private lateinit var youtube: ImageView

    private lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    lateinit var imageUrl: ArrayList<String>

    lateinit var map: ImageView

    lateinit var gridView: GridView

    private lateinit var styledPV : StyledPlayerView
    private lateinit var exoPlayer : ExoPlayer

    private lateinit var bottomSheetDialog : BottomSheetDialog
    private lateinit var campuses : LinearLayout

    private var isPlay = true
    private var manuallyPaused = false // Added manuallyPaused flag
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        sliderView = view.findViewById(R.id.imageSlider)
       // gridView = view.findViewById(R.id.grid_view)
        map = view.findViewById(R.id.image_map)
        styledPV = view.findViewById(R.id.styledPV)
        nestedScrollView = view.findViewById(R.id.nestedScrollView)

        insta = view.findViewById(R.id.insta)
        facebook = view.findViewById(R.id.facebook)
        twitter = view.findViewById(R.id.twitter)
        youtube = view.findViewById(R.id.youtube)

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
        campuses = view.findViewById(R.id.campuses)
        campuses.setOnClickListener {
            showBottomSheet()
        }
        setSlider()
        setVideoPlayer()
       // setGridView()
        setMap()

        return view
    }
    private fun showBottomSheet() {
         bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
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

    override fun onResume() {
        super.onResume()
        exoPlayer.playWhenReady = true
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.playWhenReady = false
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