package com.cristianmg.knativeplayer.engine

actual fun downloadItem(downloadState: (DownloadState) -> Unit, url: String) : DownloadResult {
    return DownloadResult(true)
}