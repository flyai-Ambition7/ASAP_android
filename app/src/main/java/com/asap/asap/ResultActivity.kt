package com.asap.asap

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
/////////
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

import com.bumptech.glide.request.target.Target
//////////
class ResultActivity : AppCompatActivity() {
    var resultImageView: ImageView? = null
    var homeButton: Button? = null
    var saveButton: Button? = null
    var imageUrl: String? = null
    var bitmap: Bitmap? = null
    //0222
    private var resultImageUrlList = java.util.ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        imageUrl = getIntent().getStringExtra("imageUrl");
        Log.d("받은url이다", imageUrl.toString());

        // 실제 구현시 intent로 resultImageUrlList 받아오기
        //resultImageUrlList = intent.getStringArrayListExtra("resultImageUrlList") ?: ArrayList()

        //////
        resultImageView = findViewById(R.id.resultImageView)
        homeButton = findViewById(R.id.homeButton)
        saveButton = findViewById(R.id.saveButton)

        /*
        // test data
        //https://lakue.tistory.com/10
        Log.d("Glide 라이브러리 사용 ", "0000000000000000000000000")
        val testUrl = "https://www.job-post.co.kr/news/photo/202307/81602_85141_1831.jpg"
        Log.d("사용할 url", testUrl)
        //String testUrl = "C:\\Users\\11\\Desktop\\강의자료\\alice_image";
        */
        // 리스트로 받아오는 것은 아래로 실행
        /*
        // 리스트 비어 있는지 확인 후 가장 최근 항목(마지막)을 가져와서 glide로 그림
        if (resultImageUrlList.isNotEmpty()) {
            val lastImageUrl = resultImageUrlList.last()

            resultImageView?.let {
                GlideApp.with(this).load(lastImageUrl)
                    .placeholder(R.drawable.result_page_default_image)
                    .apply(RequestOptions()
                        .signature(ObjectKey("signature string"))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                    )
                    .error(R.drawable.onboarding_image_01)
                    .fallback(R.drawable.onboarding_image_02)
                    .into(it)
            }
        }
        */

        resultImageView?.let {
            /*
            GlideApp.with(this).load(imageUrl)
                .override(Target.SIZE_ORIGINAL)

                .placeholder(R.drawable.result_page_default_image)
                .apply(RequestOptions()
                    .signature(ObjectKey("signature string"))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .error(R.drawable.main_logo_asap)
                .fallback(R.drawable.onboarding_image_02)
                .into(it)
            */
            GlideApp.with(this)
                .load(imageUrl)
                .override(Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.result_page_default_image)
                .apply(RequestOptions()
                    .signature(ObjectKey("signature string"))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .error(R.drawable.main_logo_asap)
                .fallback(R.drawable.onboarding_image_02)

                .into(it)
        }




        Log.d("Glide 완료 ", "1111111111111111111111")


        /*
        Log.d("HttpURLConnection 사용 ", "0000000000000000000000000");
        imageUrl = "https://i.namu.wiki/i/YhRZX5kAVgZ0cpCjqX4s0AA0dZ-pOMf4Rb5URLmgiaiaP5MxXybfEmVP4eGaX9n0GOfmfusFbvinsRdpJLB8-W6vhi9GlY_FET1hYfmuvWc_FWmVYnP3cc7jGwl50tGRUOH8hWsp23EhwwzdPybubA.webp";
        Thread Thread = new Thread() {

            @Override

            public void run(){

                try{
                    if (!imageUrl.isEmpty()){
                        //URL url = new URL("https://7818-203-236-8-208.ngrok-free.app/media/input/8c42e56d-f75.jpg");
                        URL url = new URL(imageUrl);
                        Log.d("표시 할 url", imageUrl);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                        //     HttpURLConnection의 인스턴스가 될 수 있으므로 캐스팅해서 사용한다
                        //     conn.setDoInput(true); //Server 통신에서 입력 가능한 상태로 만듦
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        //inputStream 값 가져오기

                        bitmap = BitmapFactory.decodeStream(is);
                        // Bitmap으로 반환

                    }




                } catch (IOException e){

                    e.printStackTrace();
                    Log.d("첫번째 thread 오류", "--------------");

                }

            }

        };

        Thread.start();



        try{

            //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다림
            Thread.join();
            if (bitmap!=null){resultImageView.setImageBitmap(bitmap);}else{
                Log.d("비트맵 없다", "--------------");
            }

            Log.d("조인", "--------------");
        }catch (InterruptedException e){

            e.printStackTrace();
            Log.d("조인 실패", "--------------");

        }

        Log.d("HttpURLConnection 사용 끝 ", "111111111111111");

         */

        ///

        // 버튼 동작
        homeButton?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ResultActivity, ImageUploadActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        })
        saveButton?.setOnClickListener(View.OnClickListener {
            saveImageToGallery() // imageView에 있는 이미지를 저장함
            // rest api로 가져온 이미지를 그대로 저장하면 되겠지만 일단 imageview에서 가져와서 저장하는 것으로 짜둠


            //Toast.makeText(ResultActivity.this, "저장 버튼 클릭", Toast.LENGTH_SHORT).show();
            val intent = Intent(this@ResultActivity, ExtraConnectionActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        })
        //setContentView(R.layout.activity_result)
    }

    private fun saveImageToGallery() {
        // ImageView에서 Drawable을 가져와서 Bitmap으로 변환
        val drawable = resultImageView!!.drawable as BitmapDrawable
        val bitmap = drawable.bitmap

        // 내 갤러리에 저장할 파일 생성
        val fileName = "ASAP_Result_" + System.currentTimeMillis() + ".png"
        val galleryFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), fileName)
        try {
            // 파일에 비트맵 저장
            val outputStream: OutputStream = FileOutputStream(galleryFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // 갤러리에 추가하기
            MediaStore.Images.Media.insertImage(contentResolver, galleryFile.absolutePath, galleryFile.name, galleryFile.name)

            // 성공 메시지 토스트로 표시
            Toast.makeText(this@ResultActivity, "이미지가 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            // 실패 메시지 토스트로 표시
            Toast.makeText(this@ResultActivity, "이미지 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun showResultImage() {
        Log.d(ContentValues.TAG, "GET")
        val getCall = MainActivity.myAPI._new_menu_input
        getCall.enqueue(object : Callback<List<NewMenuInputItem>> {
            override fun onResponse(call: Call<List<NewMenuInputItem>>, response: Response<List<NewMenuInputItem>>) {
                if (response.isSuccessful) {
                    val mList = response.body()!!
                    val item = mList[mList.size - 1] // 마지막 항목 가져오기
                    val base64Image = item.getImage()
                    //val base64Image = item.getUser_input_image()
                    // Base64 문자열을 디코딩하여 Bitmap으로 변환
                    val decodedBitmap = decodeBase64ToBitmap(base64Image)
                    // Bitmap을 ImageView에 설정
                    resultImageView!!.setImageBitmap(decodedBitmap)
                } else {
                    Log.d(ContentValues.TAG, "Status Code : " + response.code())
                }
            }

            override fun onFailure(call: Call<List<NewMenuInputItem>>, t: Throwable) {
                Log.d(ContentValues.TAG, "Fail msg : " + t.message)
            }
        })
    }

    private fun decodeBase64ToBitmap(base64Image: String): Bitmap? {
        return try {
            // Base64 문자열을 디코딩하여 byte 배열로 변환
            val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)

            // byte 배열을 Bitmap으로 변환
            val decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            resultImageView!!.setImageBitmap(decodedBitmap)
            decodedBitmap
        } catch (e: IllegalArgumentException) {
            // 잘못된 Base64 문자열이 들어왔을 경우에 대한 예외처리
            Log.d(ContentValues.TAG, "잘못된 Base64 문자열이 들어옴")
            e.printStackTrace()
            null
        }
    }
}