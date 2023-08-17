import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.rebtelassignment.model.Country
import com.example.rebtelassignment.ui.theme.RebtelAssignmentTheme
import com.example.rebtelassignment.viewmodel.CountryInfoViewModel

@Composable
fun CountryInfoScreen(viewModel: CountryInfoViewModel) {
    val countries by viewModel.countries.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchCountries()
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(12.dp),
            text = "Choose a country and see the details",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black)

        if (countries?.isEmpty() == true) {
            Log.e("tag", "there is no country")
        } else {
            countries?.let {
                CountryDropDown(
                    viewModel = it,
                    pickedCountry = {
                        Log.v("TAG", "country name is : ${it.name.common}")
                    },
                    defaultSelectedCountry = countries!![0],
                    dialogSearch = true,
                    dialogRounded = 22,
                )
            }
            countries?.let {
                FlagWithDescription(com.example.rebtelassignment.R.drawable.flag_south_africa,countries!![0])
            }
        }
    }
}

@Composable
fun FlagWithDescription(flagResId: Int, country: Country) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = flagResId),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "name: ${country.name.official}",
            fontSize = 18.sp,
        )
        Text(
            text = "capital: ${country.capital[0]}",
            fontSize = 18.sp,
        )
        Text(
            text = "population: ${country.population}",
            fontSize = 18.sp,
        )
        Text(
            text = "currencies: ${country.currencies.keys}",
            fontSize = 18.sp,
        )
        Text(
            text = "languages: ${country.languages.values}",
            fontSize = 18.sp,
        )
    }
}

@Composable
fun CountryDropDown(
    viewModel: List<Country>,
    modifier: Modifier = Modifier,
    isOnlyFlagShow: Boolean = false,
    defaultSelectedCountry: Country = viewModel[0],
    pickedCountry: (Country) -> Unit,
    dialogSearch: Boolean = true,
    dialogRounded: Int = 12,
) {
    val countryList: List<Country> = viewModel
    var isPickCountry by remember { mutableStateOf(defaultSelectedCountry) }
    var isOpenDropDown by remember { mutableStateOf(false) }
    var searchValue by remember { mutableStateOf("") }
    var updateCountry by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(3.dp)
            .clickable { isOpenDropDown = true }
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isOnlyFlagShow) {
                        Text(
                            isPickCountry.name.common,
                            Modifier.padding(horizontal = 18.dp)
                        )
                }
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }

        if (isOpenDropDown) {
            Dialog(
                onDismissRequest = { isOpenDropDown = false },
            ) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f),
                    shape = RoundedCornerShape(dialogRounded.dp)
                ) {
                    Column {
                        if (dialogSearch) {
                            searchValue = DropDownSearchView()
                        }
                        LazyColumn {
                            items(
                                if (searchValue.isEmpty()) {
                                    countryList
                                } else {
                                    countryList.searchCountryList(searchValue)
                                }
                            ) { countryItem ->
                                Row(
                                    Modifier
                                        .padding(
                                            horizontal = 18.dp,
                                            vertical = 18.dp
                                        )
                                        .clickable {
                                            pickedCountry(countryItem)
                                            isPickCountry = countryItem
                                            isOpenDropDown = false
                                            updateCountry = true
                                        }) {
                                    Text(
                                        countryItem.name.common,
                                        Modifier.padding(horizontal = 18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (updateCountry){
        FlagWithDescription(com.example.rebtelassignment.R.drawable.flag_south_africa,isPickCountry)
    }
}

@Composable
private fun DropDownSearchView(): String {
    var searchValue by remember { mutableStateOf("") }
    Row {
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            value = searchValue,
            onValueChange = {
                searchValue = it
            },
            fontSize = 14.sp,
            hint = "Search ...",
            textAlign = TextAlign.Start,
        )
    }
    return searchValue
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    fontSize: TextUnit = 16.sp,
    textAlign: TextAlign = TextAlign.Center
) {
    Box(
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = 0.1f)
            )
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            textStyle = LocalTextStyle.current.copy(
                textAlign = textAlign,
                fontSize = fontSize
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Black.copy(0.2f)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        if (value.isEmpty()) {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.then(
                    Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 52.dp)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryInfoPreview() {
    RebtelAssignmentTheme {
        CountryInfoScreen(viewModel = CountryInfoViewModel())
    }
}


fun List<Country>.searchCountryList(key: String): MutableList<Country> {
    val tempList = mutableListOf<Country>()
    this.forEach {
        if (it.name.common.lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}
