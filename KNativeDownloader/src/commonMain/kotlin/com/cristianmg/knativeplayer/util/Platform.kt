package com.cristianmg.knativeplayer.util


enum class Platform {
    IOS,
    ANDROID;
}


expect fun getExecutingPlatform(): Platform