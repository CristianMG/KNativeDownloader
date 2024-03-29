package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.util.existFile
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpResponseData
import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import kotlinx.coroutines.io.ByteReadChannel

class MockClient {

    companion object {

        private const val GENERIC_PATH: String = "src/commonTest/resources/assets/"

        fun getMockClient(): io.ktor.client.HttpClient {
            return io.ktor.client.HttpClient(MockEngine.create {
                addHandler { request ->
                    val name = request.url.fullPath.replace("/", "")
                    val filePath = GENERIC_PATH + name

                    if(existFile(filePath)){
                         getOkRespond(filePath)
                    }else{
                        respond(status = HttpStatusCode.NotFound, content = "", headers = headersOf())
                    }
                }
            })
        }

        private fun getOkRespond(filePath: String): HttpResponseData {
            val contentLenght = Pair("Content-Lenght", listOf(getContentLenghtFromFile(filePath).toString()))
            val responseHeaders: Headers = headersOf(contentLenght)
            return respond(getByteReadChannelFromFile(filePath), HttpStatusCode.OK, responseHeaders)
        }
    }
}


expect fun getByteReadChannelFromFile(path: String): ByteReadChannel
expect fun getContentLenghtFromFile(path: String): Long
