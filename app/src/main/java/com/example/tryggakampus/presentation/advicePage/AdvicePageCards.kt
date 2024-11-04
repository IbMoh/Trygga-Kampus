package com.example.tryggakampus.presentation.advicePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// import packages
import com.example.tryggakampus.data.model.AdviceItem

// layout for card with the advice on it
@Composable
fun AdviceCard(adviceItem: AdviceItem) {
    var expand by remember { mutableStateOf(false)}
    val maxLines = if (expand) Int.MAX_VALUE else 3

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            //display picture on the top
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .height(150.dp),
                painter = painterResource(id = adviceItem.image),
                contentDescription = "banner picture",
                contentScale = ContentScale.Crop
            )

            //title of the advice
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = adviceItem.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            //text of advice content
            Text(
                text = adviceItem.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines,
            )

            if(!expand && adviceItem.text.length > 100){
                Box(
                   modifier = Modifier.fillMaxWidth(),
                   contentAlignment = Alignment.Center,
                ) {
                    TextButton(onClick = {expand = true}) {
                        Text("Read More", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                    }
                }
            }

        }
    }
}

// card for the category picking
@Composable
fun CategoryCard(title: String, image: Int, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Support Picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AdviceCardPreview() {
//    AdviceCard(
//        adviceItem = AdviceItem(
//            title = "Just a test title",
//            text = "Just writing some text in order to test how it will look in the card" +
//                    "and making it a bit longer too so that I can test the hiding the text if its too" +
//                    "long and then testing the expand button too so at this point this is just rambling" +
//                    "text in order to fill the space size because I dont want more than 3 lines showing.",
//            image = R.drawable.mentalsupport
//        )
//    )
//}