package com.example.libimgur.apis

import com.example.libimgur.ImgurClient
import com.example.libimgur.params.Section
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ImgurAPIv3Tests {
    val api = ImgurClient.api
    // we can name our test as sentence, In kotlin we can name variables with spaces using backtick
    // for tests we can always use runBlocking
    @Test
    fun `get tags working`() = runBlocking{
        // we are using runBlocking here because its fine to run tests in main thread
        val response = api.getTags()
        assertNotNull(response.body())
    }

    @Test
    fun `get tag - tree working`() = runBlocking{
        // we are using runBlocking here because its fine to run tests in main thread
        val response = api.getTagGallery("vintage")
        assertNotNull(response.body())
    }

    @Test
    fun `get galleries working - hot working`() = runBlocking{
        val response = api.getGallery(Section.HOT)
        assertNotNull(response.body())
    }

    @Test
    fun `get galleries - top working`()= runBlocking{
        val response = api.getGallery(Section.TOP)
        assertNotNull(response.body())
    }
}