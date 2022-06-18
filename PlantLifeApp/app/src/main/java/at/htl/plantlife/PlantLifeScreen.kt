package at.htl.plantlife

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

enum class PlantLifeScreen(
    val icon: ImageVector,
) {
    Overview(
        icon = Icons.Filled.Add,
    ),

    Detail(
        icon = Icons.Filled.List,
    );

    companion object {
        fun fromRoute(route: String?): PlantLifeScreen =
            when (route?.substringBefore("/")) {
                Detail.name -> Detail
                Overview.name -> Overview
                null -> Overview
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
