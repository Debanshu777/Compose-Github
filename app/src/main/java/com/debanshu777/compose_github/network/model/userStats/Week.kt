package com.debanshu777.compose_github.network.model.userStats


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Week(
    @SerialName("contributionDays")
    val contributionDays: List<ContributionDay> = listOf()
)