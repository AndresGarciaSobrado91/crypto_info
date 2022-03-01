package com.garcia.cryptoinfo.domain.model

data class CoinDetail(
    val coinId: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val isNew: Boolean,
    val type: String,
    val tags: List<String>,
    val team: List<TeamMember>
){
    data class TeamMember(
        val id: String,
        val name: String,
        val position: String
    )
}
