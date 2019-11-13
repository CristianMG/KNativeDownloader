package com.cristianmg.knativedownloader

import kotlinx.coroutines.CoroutineScope

expect fun getCurrentThread(): String
expect fun getCoroutineScope(): CoroutineScope
