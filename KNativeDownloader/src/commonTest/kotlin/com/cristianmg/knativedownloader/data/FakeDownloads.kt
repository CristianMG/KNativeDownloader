package com.cristianmg.knativedownloader.data

import com.cristianmg.knativedownloader.model.FileDownload

class FakeDownloads{


    companion object{

        const val URL_WASNT_FOUND: String = "http://www.example.com/not_found.zip"


        fun getListDownload(quantity:Int): List<FileDownload> {
            val list = mutableListOf<FileDownload>()
            for (i in 1..quantity) {
                list.add(getFileDownload(i))
            }
            return list
        }


        fun getFileDownload(document: Int = 1): FileDownload {
            return FileDownload("http://www.example.com/example_document_$document.pdf")
        }

        fun getFileDownloadNotFound(document: Int = 1): FileDownload {
            return FileDownload("http://www.example.com/example_document_$document.pdf")
        }
    }
}