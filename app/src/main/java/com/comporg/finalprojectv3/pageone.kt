package com.comporg.finalprojectv3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.comporg.finalprojectv3.data.PlantIDClass
import com.comporg.finalprojectv3.data.examplePlant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PageOneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePage()
        }
    }
}

enum class AppScreen(@StringRes val title: Int) {
    Status(title = R.string.status_page),
    AddPlant(title = R.string.addplant_page),
    PlantInfo(title = R.string.plantinfo_page),
    PlantList (title = R.string.plantlist_info)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val customFontFamily = FontFamily(Font(R.font.font_family))

    TopAppBar(
        title = { Text(stringResource(currentScreen.title),
            style = TextStyle(
                fontFamily = customFontFamily,
            ) ) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar(stringResource(R.string.exampleName))
        myPlantsMenu()
        PlantImage(
            examplePlant,
            "meha",
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
        bottomMenu()
    }
}

@Composable
fun PlantImage(plant: PlantIDClass, img: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            PlantStateIcon(plant.StateIconImg)
        }
        Row(
            modifier = modifier
        ) {
            PlantImg()
        }
    }
}

@Composable
fun PlantStateIcon(
    plantStateImage: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 0.dp)
    ) {
        Image(
            modifier = modifier
                .padding(top = 15.dp)
                .size(100.dp),
            painter = painterResource(plantStateImage),
            contentDescription = null
        )
    }
}

@Composable
fun PlantImg(
    modifier: Modifier = Modifier
) {
    lateinit var databaseRef1: DatabaseReference
    val database = FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
    databaseRef1 = database.getReference("CurrentData")

    // Create a variable to hold the Firebase Storage URL
    // Create a variable to hold the Firebase Storage URL
    var firebaseImageUrl by remember { mutableStateOf<String?>(null) }

    // Fetch Firebase Storage URL asynchronously
    LaunchedEffect(key1 = true) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Assuming snapshot.getValue() returns the URL as a String
                firebaseImageUrl = snapshot.getValue(String::class.java)
                Log.d("Firebase", "Fetched URL: $firebaseImageUrl")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e("Firebase", "Error fetching image URL", error.toException())
            }
        }
        databaseRef1.child("img").addListenerForSingleValueEvent(valueEventListener)

    }

    val painter = // Optionally, you can customize the image loading
        // e.g., placeholder, error, transformations
        rememberAsyncImagePainter(firebaseImageUrl)

    Log.d("Firebase", firebaseImageUrl.toString())

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .size(330.dp)
            .padding(top = 15.dp)
    )
}

//@Composable
//fun PlantImg(
//    @DrawableRes plantStateImage: Int,
//    modifier: Modifier = Modifier
//) {
//    Image(
//        modifier = modifier
//            .size(350.dp)
//            .padding(bottom = 30.dp),
//        painter = painterResource(plantStateImage),
//        contentDescription = null
//    )
//}

@Composable
fun TopBar(
    userName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val customFontFamily = FontFamily(Font(R.font.font_family))

    Column(
        modifier = Modifier
            .padding(start = 32.dp, top = 50.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                "Hello $userName!",
                fontSize = 30.sp,
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.heading)
                )
            )
            Image(
                painter = painterResource(R.drawable.smalleruser),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(context, PageThree::class.java)
                        context.startActivity(intent)
                    }
                    .padding(end = 35.dp),

            )
        }
        Row {
            Text(
                stringResource(R.string.splashName),
                Modifier.padding(top = 10.dp),
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontStyle = FontStyle.Italic,
                    color = colorResource(id = R.color.subheading))
            )
        }
    }
}

@Composable
fun myPlantsMenu(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val customFontFamily = FontFamily(Font(R.font.font_family))

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(32.dp, 0.dp)
            .fillMaxWidth()
            .clickable {
                val intent = Intent(context, PageUserPlants::class.java)
                context.startActivity(intent)
            },
    ) {
        Text(
            stringResource(R.string.menuName),
            Modifier.padding(top = 40.dp),
            fontSize = 25.sp,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.heading))
        )
        Text(
            stringResource(R.string.LeFakeButton),
            Modifier.padding(top = 35.dp),
            fontSize = 30.sp,
        )
    }
}

@Composable
fun bottomMenu(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(bottom = 0.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.bottommenubox),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ){
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(0.dp, -10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.plussymbollate),
                    modifier = modifier
                        .size(50.dp)
                        .clickable {
                            val intent = Intent(context, PageThree::class.java)
                            context.startActivity(intent)
                        },
                    contentDescription = null
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(60.dp, 6.dp)
                    .fillMaxWidth()
                    .offset(0.dp, -25.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(context, PageStat::class.java)
                            context.startActivity(intent)
                        },
                ) {
                    Image(
                        painter = painterResource(R.drawable.staticon),
                        modifier = modifier.size(70.dp),
                        contentDescription = null
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(context, PageFour::class.java)
                            context.startActivity(intent)
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.commicon),
                        modifier = modifier.size(70.dp),
                        contentDescription = null
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)  // Adjust height as necessary
                .align(Alignment.BottomCenter)
                .background(colorResource(id = R.color.bottom_nav))  // Set to the desired background color
        )
    }
}
@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage()
}