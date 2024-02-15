package com.asap.asap;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private FragmentManager fm;
    private ArrayList<Fragment> fList;

    private Button loginButton, signUpButton;


    static public final String BASE_URL = "https://7818-203-236-8-208.ngrok-free.app";
    static public MyAPI myAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSignUpAPI(BASE_URL);
        // 접근 권한 요청, 권한을 부여할 권한 지정하는 부분
        // 이미지들 가져올 것이라 외부 저장소 읽는 것 권한 받기
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                //Manifest.permission.READ_MEDIA_IMAGES,
                //Manifest.permission.INTERNET,
               // Manifest.permission.RECORD_AUDIO,
                //Manifest.permission.CAMERA
        };
        checkPermissions(permissions); // 권한 허용할 것인지 물어보는 것 부분 함수

        //
        mViewPager = (ViewPager) findViewById(R.id.pager);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        // 중간에 온보딩 부분 관련
        fm = getSupportFragmentManager();

        fList = new ArrayList<Fragment>();
        fList.add(OnboardingPage01Fragment.newInstance());
        fList.add(OnboardingPage02Fragment.newInstance());
        fList.add(OnboardingPage03Fragment.newInstance());


        mViewPager.setOnPageChangeListener(viewPagerListener);

        CFPagerAdapter adapter = new CFPagerAdapter(fm, fList);
        mViewPager.setAdapter(adapter);

        // 로그인 버튼
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 이후 백과 연결해서 RestAPI 확인 후 인증 과정
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //Intent intent = new Intent(MainActivity.this, TextUpload2Activity.class);
                // FLAG_ACTIVITY_SINGLE_TOP : 실행하고자 하는 Activity가 존재하면 생성 되신 순서를 가장 위로 올리는 flag
                // 이것을 사용하여 MainActivity를 OnCreate하지 않고 재사용하기 때문에 초기화가 되지 않음
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        // 회원가입 버튼
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 누르면 회원가입 화면으로 이동
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                // 데이터 전달시 아래 이용
                // intent.putExtra("key", "value");
                startActivity(intent);
            }
        });
    }

    ViewPager.SimpleOnPageChangeListener viewPagerListener = new ViewPager.SimpleOnPageChangeListener(){
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            updateIndicators(position);

        }
        @Override
        public void onPageScrollStateChanged(int state) {
            // Do something on page scroll state changed
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Do something on page scrolled
        }


    };

    private void updateIndicators(int position) {
        ImageView indicator0 = findViewById(R.id.indicator01);
        ImageView indicator1 = findViewById(R.id.indicator02);
        ImageView indicator2 = findViewById(R.id.indicator03);

        indicator0.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_gray));
        indicator1.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_gray));
        indicator2.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_gray));

        switch (position) {
            case 0:
                indicator0.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_orange));
                break;
            case 1:
                indicator1.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_orange));
                break;
            case 2:
                indicator2.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_orange));
                break;
        }
    }



    // 앱을 맨 처음 실행했을 때 권한 permission 허용을 요청하는 함수
    public void checkPermissions(String[] permissions) {
        // premission들을 string 배열로 가지고 있는 위험 권한 permissions를 받아옴. 외부 저장장치 읽기
        ArrayList<String> targetList = new ArrayList<String>();
        for (int i = 0; i < permissions.length; i++) {
            String curPermission = permissions[i]; // 현재 요청할 permission을 curPermission에 넣고
            int permissionCheck = ContextCompat.checkSelfPermission(this, curPermission); // 현재 앱에서 권한이 있는지를 permissionCheck에 넣음
            //ContextCompat.checkSelfPermission()를 사용하여 앱에 이미 권한을 부여 받았는지 확인
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                // 호출 결과로 PERMISSION_GRANTED or PERMISSION_DENIED 반환 받은 것을 확인
                // 초기 실행에서 권한 허용을 한 후에 다시 앱을 실행했을 때는 이미 권한이 있어서 아래와 같은 토스트 메세지를 띄워줌
                Toast.makeText(this, curPermission + " 권한 있음", Toast.LENGTH_SHORT).show();
            } else {
                // 만약 권한 설정이 허용되어 있지 않은 경우 권한 없음이 토스트 메세지로 뜨고
                Toast.makeText(this, curPermission + " 권한 없음", Toast.LENGTH_SHORT).show();
                // shouldShowRequestPermissionRationale는 사용자가 이전에 권한 요청을 거절했었을 때 true를 리턴하고
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, curPermission)) {
                    //Toast.makeText(this, curPermission + " 권한 설명 필요함.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "이번 거절시 더이상 물어보지 않습니다 -> 권한 없어 기능을 사용할 수 없음", Toast.LENGTH_SHORT).show();
                    // 거절을 2번 하면 이후는 물어보지 않음으로 안내 문구를 보여줌
                    targetList.add(curPermission);
                    // 권한 부여할 용도인 targetList에 현재 물어본 curPermission 넣음
                } else {
                    targetList.add(curPermission);
                }
            }
        }

        String[] targets = new String[targetList.size()];
        targetList.toArray(targets);

        for (int i=0; i< targets.length; i++){
            int permissionCheck = ContextCompat.checkSelfPermission(this, targets[i]); // 현재 앱에서 권한이 있는지를 permissionCheck에 넣음
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                // 위험 권한이 아직 허용되지 않은 상태인데 위에서 허용해달라고 눌렀으면 이리로 오게 됨.
                // 위험 권한 허용을 요청해서 이제 기능을 쓸 수 있음
                ActivityCompat.requestPermissions(this, targets, 101);
            }
        }
    }

    public void initSignUpAPI(String baseUrl){

        Log.d(TAG,"initSignUpAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myAPI = retrofit.create(MyAPI.class);
    }

}