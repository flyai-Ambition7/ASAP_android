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
