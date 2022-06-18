package at.htl.plantlife

import android.os.Bundle
import android.service.autofill.UserData
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import at.htl.plantlife.ui.theme.PlantLifeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantLifeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    PlantLifeNavHost(navController = navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlantLifeTheme {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    PlantLifeTheme {
        ListProducts(products = SampleData.productSample)
    }
}

@Composable
fun PlantLifeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = PlantLifeScreen.Overview.name,
        modifier = modifier
    ) {

        composable(PlantLifeScreen.Overview.name) {
            HomeScreen()
        }

    }
}


@Composable
fun ListProducts(products: List<Product>) {
    LazyColumn {
        items(products) { product ->
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .clickable(onClick = {})
            ) {
                DisplayProduct(product = product, {})
            }
        }
    }
}

@Composable
fun DisplayProduct(product: Product, click: () -> Unit) {

    Image(
        painter = painterResource(product.imageId),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .border(1.5.dp, MaterialTheme.colors.secondary)
    )

    Column(modifier = Modifier.padding(5.dp)) {
        Text(text = product.name, style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.description,
            style = MaterialTheme.typography.body1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Auf Lager: " + product.stock + " Stück",
            style = MaterialTheme.typography.caption,
        )
    }
}

