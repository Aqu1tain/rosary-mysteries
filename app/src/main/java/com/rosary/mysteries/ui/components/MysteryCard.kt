package com.rosary.mysteries.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosary.mysteries.R
import com.rosary.mysteries.domain.Mystery
import com.rosary.mysteries.ui.theme.Lora
import java.util.Locale

private val bibleVersions = mapOf(
    "fr" to 93,
    "es" to 149,
    "it" to 122
)
private const val DEFAULT_BIBLE_VERSION = 59

@Composable
fun MysteryCard(mystery: Mystery, index: Int = 0, modifier: Modifier = Modifier) {
    var pressed by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "scale"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(12.dp))
            .pointerInput(mystery) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                    },
                    onTap = { openBiblePassage(context, mystery.bibleRef) }
                )
            }
            .padding(vertical = 20.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            NumberBadge(mystery.number)
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = stringResource(mystery.titleResId),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = stringResource(mystery.referenceResId),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        Text(
            text = stringResource(mystery.descriptionResId),
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 26.sp,
            modifier = Modifier.padding(start = 54.dp)
        )

        Spacer(Modifier.height(12.dp))

        Row(Modifier.padding(start = 54.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "${stringResource(R.string.fruit_prefix)} ${stringResource(mystery.fruitResId)}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
private fun NumberBadge(number: Int) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            fontFamily = Lora,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

private fun openBiblePassage(context: android.content.Context, bibleRef: String) {
    val versionId = bibleVersions[Locale.getDefault().language] ?: DEFAULT_BIBLE_VERSION
    val url = "https://www.bible.com/bible/$versionId/$bibleRef"
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}
