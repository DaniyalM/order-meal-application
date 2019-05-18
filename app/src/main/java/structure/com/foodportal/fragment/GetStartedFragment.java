package structure.com.foodportal.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentGetstartedBinding;
import structure.com.foodportal.fragment.foodportal.FoodLoginFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.DataListner;
import structure.com.foodportal.models.foodModels.User;

public class GetStartedFragment extends BaseFragment implements View.OnClickListener, DataListner {

    FragmentGetstartedBinding binding;
    VideoView videoView;
    SimpleExoPlayer player;
    MediaSource mediaSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_getstarted, container, false);
        videoView = (VideoView) binding.getRoot().findViewById(R.id.videoView);
        registrationActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        getVersionInfo();
        registrationActivity.setcontent(this);
        registrationActivity.setcontentFB(this);
        return binding.getRoot();
    }

    private void getVersionInfo() {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = registrationActivity.getPackageManager().getPackageInfo(registrationActivity.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // TextView textViewVersionInfo = (TextView) findViewById(R.id.textview_version_info);
        binding.versionapp.setText(String.format("v" + versionName));
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    public void init() {


        try {
            Uri video = Uri.parse("android.resource://" + registrationActivity.getPackageName() + "/" + R.raw.gotvideo);
           videoView.setVideoURI(video);

          videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
              @Override
              public void onPrepared(MediaPlayer mediaPlayer) {

                  mediaPlayer.setLooping(true);
              }
          });
            videoView.requestFocus();
            videoView.start();
        } catch (Exception ex) {
            //  jump();
            Log.d("VideoPlayer PSlash", " " + ex);
        }


        // HttpProxyCacheServer proxy = MyApplication.getProxy(mainActivity);
//        MediaController mediaController = new MediaController(registrationActivity);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);

//        player = ExoPlayerFactory.newSimpleInstance(
//                new DefaultRenderersFactory(registrationActivity),
//                new DefaultTrackSelector(), new DefaultLoadControl());
//        player.setVolume(0f);
//        videoView.setPlayer(player);
//        videoView.setFitsSystemWindows(true);
//        player.setPlayWhenReady(true);
//
//
//
//        //  player.seekTo(startTime.get(value), endTime.get(value));
//     //   player.addListener(this);
//        Uri path = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
//        player.setRepeatMode(SimpleExoPlayer.DISCONTINUITY_REASON_SEEK);
//        mediaSource = buildMediaSource(path);
//        player.prepare(mediaSource, true, true);


        // videoView.set(Uri.parse(path));


//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//            }
//        });
////        videoView.setOnPreparedListener(mediaPlayer -> {
////            //mediaPlayer.seekTo(syncStatusMessage.getSyncStatusMessage());
////            mediaPlayer.setLooping(true);
////
////        });
//        videoView.start();


        try {
            PackageInfo info = registrationActivity.getPackageManager().getPackageInfo(
                    "structure.com.foodportal",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));


            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        binding.loginAsGuest.setOnClickListener(this);
        binding.getStarted.setOnClickListener(this);
        binding.proceedAsGuest.setOnClickListener(this);
        binding.signIn.setOnClickListener(this);
        binding.tvWithEmail.setOnClickListener(this);
        binding.tvWithFacebok.setOnClickListener(this);
        binding.tvWithGoogle.setOnClickListener(this);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
//            case R.id.getStarted:
//                //UIHelper.showToast(registrationActivity,getString(R.string.implementdialog));
//                //  registrationActivity.replaceFragment(new SignupFragment(),true,false);
//
//                break;
//            case R.id.proceedAsGuest:
//
//                registrationActivity.showMainActivity();
//
//                break;
//            case R.id.signIn:
//                UIHelper.showToast(registrationActivity, getString(R.string.implementdialog));
//                //  registrationActivity.replaceFragment(new LoginFragment(),true,false);
//
//                break;


            case R.id.tvWithFacebok:
                registrationActivity.loginWithFacebook();

                break;
            case R.id.tvWithGoogle:
                registrationActivity.signIn();
                break;
            case R.id.tvWithEmail:
                registrationActivity.replaceFragment(new FoodLoginFragment(), true, true);

                break;

            case R.id.loginAsGuest:

                String fname = "Anonymous";
                String lname = "user";
                String email = "dummy@express.com.pk";
                long id = 293;
                User user = new User();
                user.setId(String.valueOf(id));
                user.setName_en(fname + " " + lname);
                user.setAcct_type(4);
                user.setEmail(email);
                user.setProfile_picture("http://kbae.com.au/images/no_user_image.png");
                preferenceHelper.putUserFood(user);
                preferenceHelper.setLoginStatus(true);
                registrationActivity.finish();
                registrationActivity.showMainActivity();

                break;


        }

    }

    @Override
    protected void setTitle(Titlebar titlebar) {


        titlebar.resetView();
        titlebar.setVisibility(View.GONE);
    }

    @Override
    public void getdata(JSONObject jsonObject) throws JSONException {


        String fname = jsonObject.getString("first_name");
        String lname = jsonObject.getString("last_name");
        String email = jsonObject.getString("email");
        long id = jsonObject.getLong("id");
        User user = new User();
        user.setId(String.valueOf(id));
        user.setName_en(fname + " " + lname);
        user.setAcct_type(3);
        user.setEmail(email);
        user.setProfile_picture("https://graph.facebook.com/" + id + "/picture?type=large");
        facebooklogin(user);
//        preferenceHelper.putUserFood(user);
//        preferenceHelper.setLoginStatus(true);
//        registrationActivity.showMainActivity();
//        Toast.makeText(registrationActivity, "Login Successfully", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void getdataGOOGLE(User user) {

        googlelogin(user);


    }


    public void facebooklogin(User user) {
        serviceHelper.enqueueCall(webService.LoginFACEBOOK(user.getEmail(), String.valueOf(user.getId()), user.getName_en(), "facebook", "android", preferenceHelper.getDeviceToken()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_FACEBOOK);


    }

    public void googlelogin(User user) {
        serviceHelper.enqueueCall(webService.LoginGOOGLE(user.getEmail(), user.getId(), user.getName_en(), "google", user.getProfile_picture(), "android", preferenceHelper.getDeviceToken()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_GOOGLE);


    }

    User user;

    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_FACEBOOK:

                user = (User) JsonHelpers.convertToModelClass(result, User.class);
                preferenceHelper.putUserFood(user);
                Toast.makeText(registrationActivity, "Login Successfully", Toast.LENGTH_SHORT).show();
                preferenceHelper.setLoginStatus(true);
                registrationActivity.showMainActivity();

                break;
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_GOOGLE:

                user = (User) JsonHelpers.convertToModelClass(result, User.class);
                preferenceHelper.putUserFood(user);
                Toast.makeText(registrationActivity, "Login Successfully", Toast.LENGTH_SHORT).show();
                preferenceHelper.setLoginStatus(true);
                registrationActivity.showMainActivity();

                break;


        }
    }

}
