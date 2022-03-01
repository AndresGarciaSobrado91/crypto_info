package com.garcia.cryptoinfo.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.garcia.cryptoinfo.domain.model.CoinDetail

data class CoinDetailDto(
    @SerializedName("contract")
    val contract: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("development_status")
    val developmentStatus: String,
    @SerializedName("first_data_at")
    val firstDataAt: String,
    @SerializedName("hardware_wallet")
    val hardwareWallet: Boolean,
    @SerializedName("hash_algorithm")
    val hashAlgorithm: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_new")
    val isNew: Boolean,
    @SerializedName("last_data_at")
    val lastDataAt: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("open_source")
    val openSource: Boolean,
    @SerializedName("org_structure")
    val orgStructure: String,
    @SerializedName("platform")
    val platform: String,
    @SerializedName("proof_type")
    val proofType: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("started_at")
    val startedAt: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("tags")
    val tags: List<TagDto>,
    @SerializedName("team")
    val team: List<TeamMemberDto>,
    @SerializedName("type")
    val type: String,
) {
    data class TagDto(
        @SerializedName("coin_counter")
        val coinCounter: Int,
        @SerializedName("ico_counter")
        val icoCounter: Int,
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String
    )

    data class TeamMemberDto(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("position")
        val position: String
    ){
        fun toTeamMember(): CoinDetail.TeamMember{
            return CoinDetail.TeamMember(
                id = id,
                name = name,
                position = position
            )
        }
    }
}

fun CoinDetailDto.toCoinDetail(): CoinDetail {
    return CoinDetail(
        coinId = id,
        name = name,
        description = description,
        symbol = symbol,
        rank = rank,
        isActive = isActive,
        isNew = isNew,
        type = type,
        tags = tags.map { it.name },
        team = team.map { it.toTeamMember() }
    )
}