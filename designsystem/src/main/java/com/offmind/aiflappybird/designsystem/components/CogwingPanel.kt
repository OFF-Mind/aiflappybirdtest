package com.offmind.aiflappybird.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.offmind.aiflappybird.designsystem.theme.Brass
import com.offmind.aiflappybird.designsystem.theme.BrassDark
import com.offmind.aiflappybird.designsystem.theme.CogwingSpacing
import com.offmind.aiflappybird.designsystem.theme.CogwingTheme
import com.offmind.aiflappybird.designsystem.theme.ParchmentLight
import com.offmind.aiflappybird.designsystem.theme.Soot
import com.offmind.aiflappybird.designsystem.theme.SootLight

enum class PanelStyle { BrassPlate, Parchment, EngravedWell }

// Brass plate surface — the default elevated surface in Cogwing
@Composable
fun CogwingPanel(
    modifier: Modifier = Modifier,
    style: PanelStyle = PanelStyle.BrassPlate,
    content: @Composable BoxScope.() -> Unit,
) {
    val shape = MaterialTheme.shapes.medium

    val background = when (style) {
        PanelStyle.BrassPlate -> Modifier.background(
            Brush.verticalGradient(listOf(SootLight, Soot, SootLight.copy(alpha = 0.6f))),
        )
        PanelStyle.Parchment -> Modifier.background(
            Brush.verticalGradient(listOf(ParchmentLight, ParchmentLight.copy(alpha = 0.85f))),
        )
        PanelStyle.EngravedWell -> Modifier.background(Soot.copy(alpha = 0.8f))
    }

    val borderColor = when (style) {
        PanelStyle.BrassPlate -> Brass.copy(alpha = 0.6f)
        PanelStyle.Parchment -> Brass.copy(alpha = 0.3f)
        PanelStyle.EngravedWell -> BrassDark.copy(alpha = 0.4f)
    }

    Box(
        modifier = modifier
            .clip(shape)
            .then(background)
            .border(1.dp, borderColor, shape)
            .padding(CogwingSpacing.sp4),
        content = content,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF14100C)
@Composable
private fun CogwingPanelPreview() {
    CogwingTheme(darkTheme = true) {
        CogwingPanel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = PanelStyle.BrassPlate,
        ) {
            Text(
                text = "A noble crash. The cogs will turn again.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
