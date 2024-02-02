package com.example.bathingsitev2.downloaders

interface Downloader {
    fun downloadFile(url: String,
                     userAgent: String,
                     contentDisposition: String,
                     mimeType: String,
                     contentLength: Long):Long
}