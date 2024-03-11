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

import com.bumptech.glide.request.target.Target

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

        // 다중 생성시 intent로 resultImageUrlList 받아오기
        //resultImageUrlList = intent.getStringArrayListExtra("resultImageUrlList") ?: ArrayList()

        //////
        resultImageView = findViewById(R.id.resultImageView)
        homeButton = findViewById(R.id.homeButton)
        saveButton = findViewById(R.id.saveButton)


        resultImageView?.let {

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

        //Log.d("Glide 완료 ", "1111111111111111111111")

        // 버튼 동작
        homeButton?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ResultActivity, ImageUploadActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        })
        saveButton?.setOnClickListener(View.OnClickListener {
            saveImageToGallery() // imageView에 있는 이미지를 저장함
            //Toast.makeText(ResultActivity.this, "저장 버튼 클릭", Toast.LENGTH_SHORT).show();
            val intent = Intent(this@ResultActivity, ExtraConnectionActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        })
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