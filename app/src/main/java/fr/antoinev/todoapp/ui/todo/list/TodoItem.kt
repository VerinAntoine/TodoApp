package fr.antoinev.todoapp.ui.todo.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.antoinev.todoapp.extensions.mediumSpacer
import fr.antoinev.todoapp.models.Todo

@Composable
fun TodoItem(
    item: Todo,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Checkbox(checked = item.done, onCheckedChange = onCheckedChange)
        Spacer(modifier = Modifier.mediumSpacer())
        if(!item.done)
            Text(text = item.title)
        else
            Text(text = item.title, color = MaterialTheme.colorScheme.surfaceVariant)
    }
}
