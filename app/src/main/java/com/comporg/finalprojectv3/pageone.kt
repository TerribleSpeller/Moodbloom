package com.comporg.finalprojectv3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import com.comporg.finalprojectv3.data.PlantIDClass
import com.comporg.finalprojectv3.data.examplePlant



class PageOneFragment : Fragment() {


    /**
     * TODO: Figure this out. Help. Because rn it needs for onCreateView to use @Composable functions
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.pageone, container, false)

        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            HomePage()
        }

        return view
    }
}


enum class AppScreen(@StringRes val title: Int) {
    Status(title = R.string.status_page),
    AddPlant(title = R.string.addplant_page),
    PlantInfo(title = R.string.plantinfo_page),
    PlantList (title = R.string.plantlist_info)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible. Taken from Cupcake
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
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

/**
 *
 *Home Page Composable
 */
@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(stringResource(R.string.exampleName))
        myPlantsMenu()
        PlantImage(
            examplePlant,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
        bottomMenu()
    }

}

//Plant Part
@Composable
fun PlantImage(plant: PlantIDClass, modifier: Modifier = Modifier) {
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

            //.fillMaxWidth()
        ) {
            /*TODO*/
            //Greeting("User")
            PlantStateIcon(plant.StateIconImg)

        }
        Row(
            modifier = modifier
            // .fillMaxWidth()
        ) {
            PlantImg(plant.StateImage)
        }

    }
}

/*For the State Image*/
@Composable
fun PlantStateIcon(
    @DrawableRes plantStateImage: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp)
    ) {
        Image(
            modifier = modifier
                .size(100.dp),
            painter = painterResource(plantStateImage),
            contentDescription = null
        )
    }

}

/*For the State Image*/
@Composable
fun PlantImg(
    @DrawableRes plantStateImage: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(350.dp)
            .padding(0.dp, 10.dp),
        painter = painterResource(plantStateImage),
        contentDescription = null
    )
}

@Composable
fun TopBar(
    userName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(32.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                "Hello $userName!",
                fontSize = 30.sp
            )
            Image(
                painter = painterResource(R.drawable.smalleruser),
                contentDescription = null,
                modifier = Modifier.clickable {
                    /*TODO Make User Page    */
                    val intent = Intent(context, PageThree::class.java)
                    context.startActivity(intent) },

            )
        }
        Row(

        ) {
            Text(
                stringResource(R.string.splashName), fontSize = 20.sp
            )
        }
    }
}

@Composable
fun myPlantsMenu(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(32.dp, 0.dp)
            .fillMaxWidth()
            .clickable {
                /*TODO Make MyPlants Page    */
                val intent = Intent(context, PageThree::class.java)
                context.startActivity(intent) },
    ) {
        Text(
            stringResource(R.string.menuName), fontSize = 30.sp
        )
        Text(
            stringResource(R.string.LeFakeButton), fontSize = 30.sp
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
            .background(
                Color.Gray
            )
    ) {
        Column(
            modifier = Modifier

        ) {
            Row(
                //For the top button to add more plants
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .fillMaxWidth()
                    .offset(0.dp, -20.dp)

            ) {
                Image(
                    painter = painterResource(R.drawable.plussymbollate),
                    modifier = modifier
                        .size(50.dp)
                        .clickable {
                            /*TODO Make AddPlants Page    */
                            val intent = Intent(context, PageThree::class.java)
                            context.startActivity(intent) },
                    //.padding(0.dp, 0.dp, 0.dp, 5.dp),
                    contentDescription = null
                )
                /*
                Canvas(modifier = Modifier.fillMaxWidth()) {
                    scale(scaleX = 5f, scaleY = 5f) {
                        drawCircle(Color.LightGray, radius = 5.dp.toPx())
                    }
                }
                */
            }
            Row(
                //For Stats and Comms
                horizontalArrangement = Arrangement.SpaceBetween,

                modifier = Modifier
                    .padding(82.dp, 12.dp)
                    .fillMaxWidth()
                    .offset(0.dp, -30.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .clickable {
                            /*TODO Make Statistics Page a Placeholder    */
                            val intent = Intent(context, PageStat::class.java)
                            context.startActivity(intent) },
                ) {
                    Image(
                        painter = painterResource(R.drawable.staticon),
                        modifier = modifier.size(50.dp),
                        //.padding(0.dp, 0.dp, 0.dp, 5.dp),
                        contentDescription = null
                    )
                    Text(
                        stringResource(R.string.Stats), fontSize = 15.sp
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable {
                            /*TODO Make Community Page a Placeholder    */
                            val intent = Intent(context, PageFour::class.java)
                            context.startActivity(intent) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.commicon),
                        modifier = modifier.size(70.dp),
                        contentDescription = null
                    )
                    Text(
                        stringResource(R.string.Comms), fontSize = 15.sp
                    )
                }


            }
        }
    }
}
