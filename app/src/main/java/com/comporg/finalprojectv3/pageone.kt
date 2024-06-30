package com.comporg.finalprojectv3

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.DisposableEffect
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
            PlantStateIcon()
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
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 0.dp)
    ) {
        PlantStateIconImg()
    }
}

@Composable
fun PlantStateIconImg(
    modifier: Modifier = Modifier
) {
    lateinit var databaseRef2: DatabaseReference
    lateinit var databaseRef3: DatabaseReference
    val database = FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
    databaseRef2 = database.getReference("CurrentData")
    databaseRef3 = database.getReference("ListPlants")

    var isError by remember { mutableStateOf(false) }
    var moistMax by remember { mutableStateOf<Int?>(null) }
    var moistMin by remember { mutableStateOf<Int?>(null) }
    var currentMoist by remember { mutableStateOf<Int?>(null) }
    var painterNeutral = painterResource(R.drawable.neutral)
    var painterPositive = painterResource(R.drawable.happy)
    var painterNegative = painterResource(R.drawable.angery_)
    var painter by remember { mutableStateOf(painterNeutral) }

    fun updatePainter() {
        if (currentMoist != null && moistMin != null && moistMax != null) {
            painter = when {
                currentMoist!! < moistMin!! -> painterNegative
                currentMoist!! > moistMax!! -> painterPositive
                else -> painterNeutral
            }
        }
    }

    fun fetchPlantStats(param: String) {
        databaseRef3.child(param).child("MoistMax").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                moistMax = snapshot.getValue(Int::class.java)
                updatePainter()
            }
            override fun onCancelled(error: DatabaseError) {
                isError = true
            }
        })

        databaseRef3.child(param).child("MoistMin").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                moistMin = snapshot.getValue(Int::class.java)
                updatePainter()
            }
            override fun onCancelled(error: DatabaseError) {
                isError = true
            }
        })

        databaseRef2.child("moisture").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                currentMoist = snapshot.getValue(Int::class.java)
                updatePainter()
            }
            override fun onCancelled(error: DatabaseError) {
                isError = true
            }
        })
    }

    DisposableEffect(Unit) {
        // First data fetch: Find the Plant Name
        val initialParamListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var param = snapshot.getValue(String::class.java)?.trim() //To Remove front spaces IT DOESNT WORK I HAVE BEEN LIED TO BY STACKOVERLFLOW
                if (param != null) {
                    param = param.replace(Regex("^\\s+|\\s+$"), "") //mfw I have to do Regex
                    Log.d("Firebase", "Param being used is ${param}")
                    fetchPlantStats(param)
                } else {
                    isError = true
                }
            }
            override fun onCancelled(error: DatabaseError) {
                isError = true
            }
        }
        databaseRef2.child("currentPlant").addValueEventListener(initialParamListener)

        onDispose {
            databaseRef2.child("currentPlant").removeEventListener(initialParamListener)
        }
    }

    Image(
        modifier = modifier
            .padding(top = 15.dp)
            .size(100.dp),
        painter = painter,
        contentDescription = null
    )
}

@Composable
fun PlantImg(
    modifier: Modifier = Modifier
) {
    lateinit var databaseRef1: DatabaseReference
    val database = FirebaseDatabase.getInstance("https://sem4-appeng-database-default-rtdb.asia-southeast1.firebasedatabase.app")
    databaseRef1 = database.getReference("CurrentData")

    var firebaseImageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = true) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                firebaseImageUrl = snapshot.getValue(String::class.java)
                Log.d("Firebase", "Fetched URL: $firebaseImageUrl")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error fetching image URL", error.toException())
            }
        }
        databaseRef1.child("img").addListenerForSingleValueEvent(valueEventListener)

    }

    val painter = rememberAsyncImagePainter(firebaseImageUrl)
    //Log.d("Firebase", firebaseImageUrl.toString())
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .size(330.dp)
            .padding(top = 15.dp, bottom = 35.dp)
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

    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(bottom = 0.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.bottommenubox),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-30).dp) // Adjust the offset to move the plussymbol up
            ) {
                Image(
                    painter = painterResource(R.drawable.plussymbollate),
                    modifier = modifier
                        .size(55.dp)
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
                    .padding(start = 40.dp, end = 40.dp) // Adjust padding to ensure space between icons and edges
                    .fillMaxWidth()
                    .offset(y = (-25).dp) // Adjust vertical offset if necessary
            ) {
                Image(
                    painter = painterResource(R.drawable.staticon),
                    modifier = Modifier
                        .size(70.dp) // Increase the size of the staticon
                        .clickable {
                            val intent = Intent(context, PageStat::class.java)
                            context.startActivity(intent)
                        },
                    contentDescription = null
                )
                Image(
                    painter = painterResource(R.drawable.commicon),
                    modifier = Modifier
                        .size(70.dp) // Increase the size of the commicon
                        .clickable {
                            val intent = Intent(context, PageCommunity::class.java)
                            context.startActivity(intent)
                        },
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage()
}