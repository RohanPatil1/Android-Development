package com.rohan.news27.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.rohan.news27.domain.models.Article
import com.rohan.news27.presentation.view_model.NewsViewModel

@Composable
fun HomeScreen(newsViewModel: NewsViewModel = hiltViewModel()) {

    val res = newsViewModel.articles.value
    if (res.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }

    if (res.error.isNotBlank()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = res.error, modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }

    res.data?.let {
        LazyColumn {
            items(it) {
                NewsTile(it)
            }
        }
    }


}

@Composable
fun NewsTile(article: Article) {

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFF222831),
    modifier = Modifier.padding(8.dp)
        ){
        Column(modifier = Modifier) {
            Image(
                painter = rememberImagePainter(data = article.urlToImage), contentDescription = "",
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = article.title,
                style = TextStyle(color = Color.Gray),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(12.dp)
            )

        }
    }


}
