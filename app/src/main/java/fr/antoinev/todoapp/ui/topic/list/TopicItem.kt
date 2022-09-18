package fr.antoinev.todoapp.ui.topic.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.antoinev.todoapp.models.Topic

@Composable
fun TopicListScreen(item: Topic, navigate: (String) -> Unit) {
    TextButton(
        onClick = {  },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 5.dp)) {
            Canvas(modifier = Modifier.size(12.dp)) {
                drawCircle(
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.width(30.dp))
            Text(text = item.name, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Select")
        }
    }
}
