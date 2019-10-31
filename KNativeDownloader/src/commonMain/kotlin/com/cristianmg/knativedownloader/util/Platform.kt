package com.cristianmg.knativedownloader.util


enum class Platform {
    IOS,
    ANDROID;
}


expect fun getExecutingPlatform(): Platform