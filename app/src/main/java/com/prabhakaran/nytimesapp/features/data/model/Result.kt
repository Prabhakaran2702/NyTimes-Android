package com.prabhakaran.nytimesapp.features.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    val uri: String,
    val url: String,
    val id: Long,
    val asset_id: Long,
    val source: String,
    val published_date: String,
    val updated: String,
    val section: String,
    val subsection: String,
    val nytdsection: String,
    val adx_keywords: String,
    val column: String?,
    val byline: String,
    val type: String,
    val title: String,
    val abstract: String,
    val des_facet: List<String>,
    val org_facet: List<String>,
    val per_facet: List<String>,
    val geo_facet: List<String>,
    val media: List<Media>,
    val eta_id: Int
) : Serializable

data class Media(
    val type: String,
    val subtype: String,
    val caption: String,
    val copyright: String,
    val approved_for_syndication: Int,
    @SerializedName("media-metadata") val media_metadata: List<MediaMetadata>
) : Serializable

data class MediaMetadata(
    val url: String,
    val format: String,
    val height: Int,
    val width: Int
) : Serializable