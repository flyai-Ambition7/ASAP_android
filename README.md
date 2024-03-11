# ASAP Front-end
## 기술 스택

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white">
<img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
<img src="https://img.shields.io/badge/android-34A853?style=for-the-badge&logo=android&logoColor=white">
<img src="https://img.shields.io/badge/androidstudio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">
<img src="https://img.shields.io/badge/adobephotoshop-31A8FF?style=for-the-badge&logo=adobephotoshop&logoColor=white">


Client는 Android로 지정하여 App으로 서비스를 제공합니다. 

- Andorid Studio에서 java와 kotlin을 사용하여 개발하였습니다. 
- UI 및 로고 제작 등 디자인 작업에서 Figma와 photoshop이 사용되었습니다. 
- Github를 통해 버전 관리를 진행하였습니다.

## dependencies

retrofit2

```sh
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

glide
```sh
implementation 'com.github.bumptech.glide:glide:4.16.0'
implementation "com.github.bumptech.glide:okhttp3-integration:4.16.0"
kapt 'com.github.bumptech.glide:compiler:4.16.0'
```

- 서버와 RestAPI 작업 처리를 위해 Retrofit2를 사용합니다.
- 서버 측 URL으로 이미지를 불러와 imageView에 표시하기 위해 Glide를 사용합니다. 


## Front-end 구조

```sh
│  .gitignore
│  build.gradle
│  gradle.properties
│  gradlew
│  gradlew.bat
│  local.properties
│  README.md
│  settings.gradle
│
├─.gradle
│  │  config.properties
│  │  file-system.probe
│  │
│  ├─8.2
│  │  │  gc.properties
│  │  │
.
.
.
│  │
│  └─src
│     .
│     .
│     .
│      │
│      ├─main
│      │  │  AndroidManifest.xml
│      │  │  asap_icon-playstore.png
│      │  │
│      │  ├─java
│      │  │  └─com
│      │  │      └─asap
│      │  │          └─asap
│      │  │                  CFPagerAdapter.java
│      │  │                  DialogPurpose.java
│      │  │                  DialogResultForm.java
│      │  │                  DialogTheme.java
│      │  │                  ExtraConnectionActivity.java
│      │  │                  .
│      │  │                  .
│      │  │                  .
│      │  │                  OnboardingPage03Fragment.java
│      │  │                  RecyclerViewItem.java
│      │  │                  ResultActivity.kt
│      │  │                  SignUpActivity.java
│      │  │                  SignUpItem.java
│      │  │                  SplashActivity.java
│      │  │                  TextUpload1Activity.java
│      │  │                  TextUpload2Activity.java
│      │  │
│      │  └─res
│      │      ├─drawable
│      │      │      asap_icon.png
│      │      │      ic_launcher_background.xml
│      │      │      ic_launcher_foreground.xml
│      │      │      input_white_round_rect_background.xml
│      │      │      login_signup_icon_password.png
│      │                 .
│      │                 .
│      │                 .
│      │      │      upload_page_default_image.png
│      │      │      upload_page_input_background.xml
│      │      │      upload_page_left_button.png
│      │      │      upload_page_right_button.png
│      │      │
│      │      ├─layout
│      │      │      activity_extra_connection.xml
│      │      │      activity_image_upload.xml
│      │      │      activity_loading.xml
│      │      │      activity_login.xml
│      │      │      activity_main.xml
│      │      │      activity_multi_result_image.xml
│      │                 .
│      │                 .
│      │                 .
│      │      │      fragment_onboarding_page02.xml
│      │      │      fragment_onboarding_page03.xml
│      │      │      item_view.xml
│      │      │      result_image_dialog.xml
│      │      │
│      │      ├─mipmap-anydpi
│      │      │  .
│      │      │  .
│      │      │  .
│      │      │
│      │      └─xml
│      │              backup_rules.xml
│      │              data_extraction_rules.xml
│      │              file_paths.xml
│      │              network_security_config.xml
│      │
│      └─test
│          └─java
│              └─com
│                  └─asap
│                      └─asap
│                              ExampleUnitTest.java
│
└─gradle
    └─wrapper
            gradle-wrapper.jar
            gradle-wrapper.properties

```


## 페이지 구성 : UI/UX 흐름
![슬라이드5](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/06449904-5e1e-4535-b409-8ab1446eee2c)
![슬라이드6](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/e1d8dff1-f4bc-43ed-be3e-0252663f7847)
![슬라이드7](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/ec9266c9-6603-407d-8bdf-71c1d3e7af91)
![슬라이드8](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/99c87130-c023-40c6-a709-9fc321e1d50f)
![슬라이드9](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/f64b4a7a-7ac9-493e-8812-b0edd8d13c1f)
![슬라이드10](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/163fbe0f-c5c1-46c0-8408-3e31cd983633)
![슬라이드11](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/dab49a06-96f1-4b78-bcd1-6ff52d940df4)
![슬라이드12](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/5465b7ba-5ed8-4b6d-9c03-0e54818d9d6c)
![슬라이드13](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/021caf02-54a8-4e05-801e-448eff8ac8b7)
![슬라이드14](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/0b7886f1-028a-48b7-8ecc-f0c1831a9c0f)
![슬라이드15](https://github.com/flyai-Ambition7/ASAP_android/assets/113690378/5c48587b-8d89-414f-bf6c-323d05a1af18)


