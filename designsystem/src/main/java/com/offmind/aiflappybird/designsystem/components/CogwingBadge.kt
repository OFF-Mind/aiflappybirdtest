package com.offmind.aiflappybird.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.offmind.aiflappybird.designsystem.theme.Brass
import com.offmind.aiflappybird.designsystem.theme.CogwingTheme
import com.offmind.aiflappybird.designsystem.theme.Ember
import com.offmind.aiflappybird.designsystem.theme.ParchmentLight
import com.offmind.aiflappybird.designsystem.theme.Verdigris

enum class BadgeVariant { Brass, Success, Danger, Neutral }

@Composable
fun CogwingBadge(
    text: String,
    modifier: Modifier = Modifier,
    variant: BadgeVariant = BadgeVariant.Brass,
) {
    val (bgColor, borderColor, textColor) = when (variant) {
        BadgeVariant.Brass -> Triple(Brass.copy(alpha = 0.2f), Brass, Brass)
        BadgeVariant.Success -> Triple(Verdigris.copy(alpha = 0.2f), Verdigris, Verdigris)
        BadgeVariant.Danger -> Triple(Ember.copy(alpha = 0.2f), Ember, Ember)
        BadgeVariant.Neutral -> Triple(
            ParchmentLight.copy(alpha = 0.1f),
            ParchmentLight.copy(alpha = 0.4f),
            ParchmentLight.copy(alpha = 0.8f),
        )
    }

    val shape = MaterialTheme.shapes.extraSmall

    Box(
        modifier = modifier
            .clip(shape)
            .background(bgColor)
            .border(1.dp, borderColor, shape)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF14100C)
@Composable
private fun CogwingBadgePreview() {
    CogwingTheme(darkTheme = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            CogwingBadge(text = "Rare", variant = BadgeVariant.Brass)
        }
    }
}
