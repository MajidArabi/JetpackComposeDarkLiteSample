package ir.one_developer.jetpack_compose_dark_light_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ir.one_developer.jetpack_compose_dark_light_sample.ui.theme.SampleAppComposeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Column {
                        Toolbar()
                        Content()
                        Spacer(modifier = Modifier.weight(1f))
                        BottomNavigation()
                        VerticalMargin()
                    }
                }
            }
        }
    }
}

@Composable
fun Toolbar() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        HorizontalMargin(16.dp)
        ToolbarIcon(iconId = R.drawable.ic_menu)
        Spacer(modifier = Modifier.weight(1f))
        ToolbarIcon(iconId = R.drawable.ic_search)
        HorizontalMargin(16.dp)
    }
}

@Composable
fun ToolbarIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier
            .size(52.dp)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = MaterialTheme
                    .colors
                    .onBackground
                    .copy(alpha = 0.2f)
            )
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = null
        )
    }
}

@Composable
fun Content() {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            text = "Explore new\nrelease movies...",
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h4
        )
        VerticalMargin(32.dp)
        Movies()
        VerticalMargin(32.dp)
        PlayedMovies()
    }
}

data class Movie(
    val title: String,
    val subtitle: String,
    val rate: String,
    val imageUrl: String,
)

private val movies = listOf(
    Movie(
        title = "The Matrix Resurrections",
        subtitle = "David Mitchell",
        rate = "3.4",
        imageUrl = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2/8c4a8kE7PizaGQQnditMmI1xbRp.jpg"
    ),
    Movie(
        title = "Moon Knight",
        subtitle = "Marvel Studio",
        rate = "4.5",
        imageUrl = "https://static.wikia.nocookie.net/marvelcinematicuniverse/images/e/ea/Moon_Knight_Poster_Textless.png/revision/latest/top-crop/width/360/height/360?cb=20220313180026"
    ),
    Movie(
        title = "The Northman",
        subtitle = "Robert Eggers",
        rate = "4.1",
        imageUrl = "https://www.themoviedb.org/t/p/w220_and_h330_face/zhLKlUaF1SEpO58ppHIAyENkwgw.jpg"
    ),
    Movie(
        title = "Umma",
        subtitle = "Iris K. Shim",
        rate = "3.0",
        imageUrl = "https://www.themoviedb.org/t/p/w220_and_h330_face/moLnqJmZ00i2opS0bzCVcaGC0iI.jpg"
    ),
)

@Composable
fun Movies() {
    val state = rememberLazyListState()
    val centerOfScreen = LocalConfiguration.current.screenWidthDp / 2
    val isInCenter = state
        .firstVisibleItemScrollOffset / 2 in (centerOfScreen - 70)..(centerOfScreen + 70)
    val centerItemIndex = state.firstVisibleItemIndex + 1
    LazyRow(
        state = state
    ) {
        itemsIndexed(movies) { index, item ->
            val canScale = isInCenter && index == centerItemIndex
            val scale by animateFloatAsState(if (canScale) 1.1f else 0.9f)
            MovieCard(
                modifier = Modifier.scale(scale),
                movie = item
            )
            if (index == movies.lastIndex)
                HorizontalMargin(16.dp)
        }
    }
}

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    HorizontalMargin(16.dp)
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(15))
            .width(210.dp)
            .height(250.dp)
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = movie.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier.padding(vertical = 4.dp),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.blur),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
                Row(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 10.dp,
                            )
                            .weight(1f)
                    ) {
                        Text(
                            text = movie.title,
                            maxLines = 1,
                            color = Color.White,
                            fontSize = 16.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = movie.subtitle,
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                        VerticalMargin(20.dp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Casts()
                        }
                    }
                    Column(
                        modifier = Modifier.padding(
                            end = 12.dp,
                            top = 16.dp,
                        )
                    ) {
                        Rate(rate = movie.rate)
                        VerticalMargin(16.dp)
                        PlayButton()
                    }
                }
            }
        }
    }
}

@Composable
fun Rate(rate: String) {
    Row(
        verticalAlignment =
        Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.Rounded.Star,
            contentDescription = null,
            tint = Color.Yellow
        )
        Text(
            text = rate,
            color = Color.Yellow,
            fontSize = 11.sp
        )
    }
}

private val avatars = listOf(
    "https://unsplash.com/photos/IF9TK5Uy-KI/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjB8fHBlcnNvbnxlbnwwfHx8fDE2NTA1MjYwMzQ&force=true&w=640",
    "https://unsplash.com/photos/jmURdhtm7Ng/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjUwNTczNTkx&force=true&w=640",
    "https://unsplash.com/photos/mEZ3PoFGs_k/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MzN8fHBlcnNvbnxlbnwwfHx8fDE2NTA1NTUxMzI&force=true&w=640",
)

@Composable
fun Casts() {
    avatars.forEachIndexed { index, avatar ->
        Box {
            AsyncImage(
                model = avatar,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)
                    .size(20.dp)
                    .clip(CircleShape)
            )
        }
        if (index == avatars.lastIndex) {
            HorizontalMargin(4.dp)
            Text(
                text = "+${Random.nextInt(100)} Casts",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 9.sp
            )
        }
    }
}

data class PlayedMovie(
    val title: String,
    val time: String,
    val imageUrl: String
)

private val playedMovies = listOf(
    PlayedMovie(
        title = "Morbius",
        time = "30min",
        imageUrl = "https://m.media-amazon.com/images/M/MV5BYjlhNTA3Y2ItYjhiYi00NTBiLTg5MDMtZDJjMDZjNzVjNjJmXkEyXkFqcGdeQXVyMTEyMjM2NDc2._V1_QL75_UX140_CR0,0,140,140_.jpg"
    ),
    PlayedMovie(
        time = "48min",
        title = "Shang Chi",
        imageUrl = "https://static1.colliderimages.com/wordpress/wp-content/uploads/2021/04/shang-chi-and-the-legend-of-the-ten-rings-poster-social.jpg?q=50&fit=contain&w=943&h=472&dpr=1.5"
    ),
)

@Composable
fun PlayedMovies() {
    Text(
        text = "Continue Watching",
        modifier = Modifier
            .padding(start = 32.dp)
    )
    VerticalMargin()
    LazyRow(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 32.dp
        )
    ) {
        items(playedMovies) { item ->
            PlayedMovieCard(item)
        }
    }
}

@Composable
fun PlayedMovieCard(
    movie: PlayedMovie
) {
    HorizontalMargin(16.dp)
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(15))
            .background(color = MaterialTheme.colors.surface)
            .width(260.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(90.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(15)),
            model = movie.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(
                start = 4.dp,
                end = 16.dp,
            )
        ) {
            Text(
                text = movie.title,
                maxLines = 1,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis
            )
            VerticalMargin(8.dp)
            Text(
                text = movie.time,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        PlayButton()
        HorizontalMargin(8.dp)
    }
}

@Composable
fun PlayButton() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colors.secondary)
            .padding(8.dp)
    ) {
        Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = null)
    }
}

data class BottomNav(
    val title: String,
    val iconId: Int
)

private val bottomNavigationItems = listOf(
    BottomNav(title = "Explore", iconId = R.drawable.ic_explore),
    BottomNav(title = "Play", iconId = R.drawable.ic_play),
    BottomNav(title = "Favorite", iconId = R.drawable.ic_bookmark),
    BottomNav(title = "Account", iconId = R.drawable.ic_person),
)

@Composable
fun BottomNavigation() {
    var selectedIndex by remember { mutableStateOf(0) }
    val state = rememberLazyListState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(50))
                .background(color = MaterialTheme.colors.surface)
                .padding(horizontal = 16.dp, vertical = 26.dp)
        ) {
            LazyRow(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                itemsIndexed(bottomNavigationItems) { index, item ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            selectedIndex = index
                        }
                    ) {
                        AnimatedVisibility(visible = selectedIndex != index) {
                            Icon(
                                painter = painterResource(id = item.iconId),
                                contentDescription = item.title
                            )
                        }
                        AnimatedVisibility(visible = selectedIndex == index) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    selectedIndex = index
                                }
                            ) {
                                Text(text = item.title)
                                VerticalMargin(6.dp)
                                Divider(
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height(2.dp)
                                        .background(
                                            color = MaterialTheme.colors.onBackground,
                                            shape = CircleShape
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalMargin(margin: Dp = 24.dp) {
    Spacer(modifier = Modifier.width(margin))
}

@Composable
fun VerticalMargin(margin: Dp = 16.dp) {
    Spacer(modifier = Modifier.height(margin))
}
