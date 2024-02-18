package com.asap.asap

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

class GlideTestActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_test)
        Log.d("Glide 라이브러리 사용 ", "0000000000000000000000000")
        val testUrl = "https://health.chosun.com/site/data/img_dir/2023/05/31/2023053102582_0.jpg"
        Log.d("사용할 url", testUrl)
        //String testUrl = "C:\\Users\\11\\Desktop\\강의자료\\alice_image";
        var resultImageView : ImageView? = null
        resultImageView = findViewById(R.id.imageView)
        resultImageView?.let {
            // Use it safely inside this block
            GlideApp.with(this).load(testUrl)
                   // .override(300, 300)
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

        Log.d("Glide 완료 ", "1111111111111111111111")

    }
}