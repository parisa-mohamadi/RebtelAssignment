package com.example.rebtelassignment.view

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
import androidx.compose.material3.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.rebtelassignment.R
import com.example.rebtelassignment.model.Country
import com.example.rebtelassignment.utils.Utils.Companion.formatPopulation
import com.example.rebtelassignment.utils.Utils.Companion.getFlagUrl
import com.example.rebtelassignment.utils.Utils.Companion.searchCountryList
import com.example.rebtelassignment.viewmodel.CountryInfoViewModel
import org.json.JSONObject
import java.io.InputStream

/**
 * Composable function to display the main screen of the country information app.
 *
 * @param viewModel The ViewModel responsible for managing the data.
 */
@Composable
fun CountryInfoScreen(viewModel: CountryInfoViewModel) {
    val countries by viewModel.countries.observeAsState(null)
    val jsonContent = readRawResourceText(R.raw.flags)
    val jsonObject = JSONObject(jsonContent)

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
            text = (stringResource(id = R.string.ttile)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black)

        if (countries?.isEmpty() == true) {
            Log.e("tag", "there is no country")
        } else {
            countries?.let { it ->
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
                val flagUrl = getFlagUrl(countries!![0].cca2.uppercase(), jsonObject)
                if (flagUrl != null) {
                    FlagWithDescription(flagUrl, countries!![0])
                } else {
                    FlagWithDescription("https://upload.wikimedia.org/wikipedia/commons/d/d1/Image_not_available.png"
                        ,countries!![0])
                }

            }
        }
    }
}

@Composable
fun FlagWithDescription(flagResId: String?, country: Country) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .decoderFactory(SvgDecoder.Factory())
                .data(flagResId)
                .size(400)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Name: ${country.name.official}",
            fontSize = 18.sp,
        )
        Text(
            text = "Capital: ${country.capital[0]}",
            fontSize = 18.sp,
        )
        Text(
            text = "Population: ${formatPopulation(country.population.toLong())}",
            fontSize = 18.sp,
        )
        Text(
            text = "Currencies: ${country.currencies.keys}",
            fontSize = 18.sp,
        )
        Text(
            text = "Languages: ${country.languages.values}",
            fontSize = 18.sp,
        )
    }
}

@Composable
fun CountryDropDown(
    viewModel: List<Country>,
    modifier: Modifier = Modifier,
    defaultSelectedCountry: Country = viewModel[0],
    pickedCountry: (Country) -> Unit,
    dialogSearch: Boolean = true,
    dialogRounded: Int = 12,
) {
    val countryList: List<Country> = viewModel
    var selectedCountry by remember { mutableStateOf(defaultSelectedCountry) }
    var isOpenDropDown by remember { mutableStateOf(false) }
    var searchValue by remember { mutableStateOf("") }
    var updateCountry by remember { mutableStateOf(false) }
    val jsonContent = readRawResourceText(R.raw.flags)
    val jsonObject = JSONObject(jsonContent)

    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { isOpenDropDown = true }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                        Text(
                            selectedCountry.name.common,
                            Modifier.padding(horizontal = 18.dp)
                        )
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
                            searchValue = dropDownSearchView()
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
                                            selectedCountry = countryItem
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
        val flagUrl = getFlagUrl(selectedCountry.cca2.uppercase(), jsonObject)
        if (flagUrl != null) {
            FlagWithDescription(flagUrl,selectedCountry)
        } else {
            FlagWithDescription("https://upload.wikimedia.org/wikipedia/commons/d/d1/Image_not_available.png", selectedCountry)
        }
    }
}

@Composable
private fun dropDownSearchView(): String {
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

@Composable
fun readRawResourceText(resourceId: Int): String {
    val context = LocalContext.current
    val inputStream: InputStream = context.resources.openRawResource(resourceId)
    return inputStream.bufferedReader().use { it.readText() }
}
