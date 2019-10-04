package com.cristianmg.knativeplayer

import com.cristianmg.knativeplayer.client.HttpClient


class KNativeDownloader() {

    val httpClient by lazy { HttpClient.client() }



}