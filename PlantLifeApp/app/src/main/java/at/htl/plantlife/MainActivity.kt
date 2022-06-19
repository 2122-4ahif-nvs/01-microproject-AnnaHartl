package at.htl.plantlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.htl.plantlife.ui.theme.PlantLifeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantLifeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    PlantLifeApp()
                }
            }
        }
    }
}

@Composable
fun PlantLifeApp() {
    PlantLifeTheme {
        val navController = rememberNavController()
        PlantLifeNavHost(navController = navController)

        Scaffold(
            topBar = { TopAppBar(title = { Text("PlantLife", color = Color.White) }, backgroundColor = Color(0xff0f9d58)) }
        ) { innerPadding ->
            PlantLifeNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }

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
        val plantName = PlantLifeScreen.Detail.name

        composable(PlantLifeScreen.Overview.name) {
            ListProducts(products = SampleData.productSample, onDetailClicked =  { name ->
                navController.navigate("$plantName/$name")
            })
        }

        //andere Routen

        composable(
            "$plantName/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                },
            )
        ) { entry ->
            val productName = entry.arguments?.getString("name")
            val product = getProduct(productName)
            SingleProduct(product = product)
        }

    }
}

@Composable
fun SingleProduct(product: Product) {
    Column(modifier = Modifier
        .padding(15.dp)
        .fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalAlignment =  CenterHorizontally,
        ) {
            Image(
                painter = painterResource(product.imageId),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                    text = product.name,
            style = MaterialTheme.typography.h4)
        }
        DisplayText(product)
        DisplayButtons(product)
    }
}

@Composable
fun DisplayButtons(product: Product) {
    Row {
        
        Button(onClick = { /*TODO*/ }) {
            Text(text = "In den Einkaufswaagen")
        }
    }
}

@Composable
private fun DisplayText(
    product: Product
) {
    val fontSizeValue = 15.sp;
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = "Beschreibung: ", fontWeight = FontWeight(1000))
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = product.description,
    )
    Spacer(modifier = Modifier.height(10.dp))

    Row {
        if (product.stock <= 3) {
            Text(
                fontWeight = FontWeight(1000),
                text = "Es sind nur noch " + product.stock.toString() + " Stück übrig!",
                color = Color.Red,
                fontSize = fontSizeValue
            )
        } else {
            Text(text = "Noch vorhanden: ", fontWeight = FontWeight(1000))
            Text(
                text = product.stock.toString() + " Stück",
            )
        }
    }
}

/*@Composable
fun MyContent(){

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // Create a list of cities
    val mCities = listOf("Delhi", "Mumbai", "Chennai", "Kolkata", "Hyderabad", "Bengaluru", "Pune")

    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {

        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = {Text("Label")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(mTextFieldSize.width.toDp())
        ) {
            mCities.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label
                    mExpanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}*/

fun getProduct(productName: String?): Product {
    return SampleData.productSample.first { it.name == productName }
}


@Composable
fun ListProducts(products: List<Product>, onDetailClicked: (String) -> Unit = {}) {
    LazyColumn {
        items(products) { product ->
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .clickable { onDetailClicked(product.name) }
            ) {
                DisplayProduct(product = product)
            }
        }
    }
}

@Composable
fun DisplayProduct(product: Product) {

    Image(
        painter = painterResource(product.imageId),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
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

