package com.offmind.aiflappybird.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.offmind.aiflappybird.designsystem.theme.Brass
import com.offmind.aiflappybird.designsystem.theme.BrassLight
import com.offmind.aiflappybird.designsystem.theme.CogwingTheme
import com.offmind.aiflappybird.designsystem.theme.Soot
import com.offmind.aiflappybird.designsystem.theme.SootLight

// Engraved well displaying a single labelled score or stat value
@Composable
fun CogwingScoreWell(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    val shape = MaterialTheme.shapes.small

    Column(
        modifier = modifier
            .clip(shape)
            .background(Brush.verticalGradient(listOf(SootLight, Soot)))
            .border(1.dp, Brass.copy(alpha = 0.5f), shape)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = Brass.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = BrassLight,
            textAlign = TextAlign.Center,
        )
    }
}

// Row of score wells for multi-stat displays (e.g. score + best)
@Composable
fun CogwingScoreRow(
    stats: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        stats.forEach { (label, value) ->
            CogwingScoreWell(
                label = label,
                value = value,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF14100C)
@Composable
private fun CogwingScoreWellPreview() {
    CogwingTheme(darkTheme = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            CogwingScoreWell(label = "Score", value = "1 024")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF14100C)
@Composable
private fun CogwingScoreRowPreview() {
    CogwingTheme(darkTheme = true) {
        CogwingScoreRow(
            stats = listOf("Score" to "1 024", "Best" to "3 781"),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}
