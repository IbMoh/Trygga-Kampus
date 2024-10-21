package com.example.tryggakampus.presentation.advicePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tryggakampus.R

// import packages
import com.example.tryggakampus.domain.model.AdviceItem

// this is just how the layout of every card will be, regardless of what category they are part of
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
                .padding(16.dp)
        ) {
            //display picture on the top
            Image(
                modifier = Modifier
                        .fillMaxWidth()
                    .height(150.dp),
                painter = painterResource(id = adviceItem.image),
                contentDescription = "banner picture",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier =  Modifier.height(4.dp))

            //title of the advice
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                text = adviceItem.title,
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier =  Modifier.height(4.dp))

            //text of advice content
            Text(
                text = adviceItem.text,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines,
            )

            Spacer(modifier =  Modifier.height(4.dp))

            //expand button and expand logic
            if(!expand && adviceItem.text.length > 100){
                Box(
                   modifier = Modifier
                       .fillMaxWidth(),
                   contentAlignment = Alignment.Center,
                ) {
                    TextButton(onClick = {expand = true}) {
                        Text("Read More")
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdviceCardPreview() {
    AdviceCard(
        adviceItem = AdviceItem(
            title = "Just a test title",
            text = "Just writing some text in order to test how it will look in the card" +
                    "and making it a bit longer too so that I can test the hiding the text if its too" +
                    "long and then testing the expand button too so at this point this is just rambling" +
                    "text in order to fill the space size because I dont want more than 3 lines showing.",
            image = R.drawable.mentalsupport
        )
    )
}