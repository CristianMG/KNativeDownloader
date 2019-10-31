package com.cristianmg.knativedownloader.util

import java.io.File

actual fun existFile(url: String): Boolean {
 return File(url).exists()
}