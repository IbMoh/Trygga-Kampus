package com.example.tryggakampus.presentation.advicePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
                    .padding(4.dp),
                text = adviceItem.title,
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier =  Modifier.height(4.dp))

            //text of advice content
            Text(
                text = adviceItem.text,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier =  Modifier.height(4.dp))

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